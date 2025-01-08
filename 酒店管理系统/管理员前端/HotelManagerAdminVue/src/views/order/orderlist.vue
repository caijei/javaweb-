<template>
    <div class="user-table">
        <el-row :gutter="20" v-for="(item, index) in tableData" :key="index" class="order-card">
            <el-col :span="24">
                <div class="order-card-container">
                    <div class="order-info">
                        <h3>订单编号: #{{ item.id }}</h3>
                        <p>用户ID: {{ item.userId }}</p>
                        <p>房间ID: {{ item.roomId }}</p>
                        <p>下单时间: {{ item.createTime }}</p>
                        <p>入住时间: {{ item.inTime }}</p>
                        <p>离店时间: {{ item.leaveTime }}</p>
                        <p>总计价格: ¥{{ item.realPrice }}</p>
                        <p>入住人数: {{ item.realPeople }} 人</p>
                        <p>发票: {{ item.fapiao === 1 ? '需要' : '不需要' }}</p>
                        <p>受理状态: {{ item.flag }}</p>
                    </div>
                    <div class="order-actions">
                        <el-button
                            type="success"
                            @click="accept(item.id)"
                            size="large"
                            class="action-btn"
                        >
                            接受
                        </el-button>
                        <el-button
                            type="danger"
                            @click="cancel(item.id)"
                            size="large"
                            class="action-btn"
                        >
                            取消
                        </el-button>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tableData: [],
        };
    },
    methods: {
        accept(orderId) {
            this.$confirm("确定接受该订单？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "success",
            })
                .then(() => {
                    this.axios
                        .post(
                            "http://localhost:9151/admin/handle?orderId=" +
                                orderId
                        )
                        .then(() => {
                            this.$message({
                                type: "success",
                                message: "接受订单成功",
                            });
                            this.listOrders();
                        })
                        .catch(() => {
                            this.$message({
                                type: "error",
                                message: "接受订单失败",
                            });
                        });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消操作",
                    });
                });
        },
        cancel(orderId) {
            this.$confirm("确定取消该订单？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "error",
            })
                .then(() => {
                    this.axios
                        .post(
                            "http://localhost:9151/admin/unsubscribe?orderId=" +
                                orderId
                        )
                        .then(() => {
                            this.$message({
                                type: "info",
                                message: "取消订单成功",
                            });
                            this.listOrders();
                        })
                        .catch(() => {
                            this.$message({
                                type: "info",
                                message: "取消订单失败",
                            });
                        });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消操作",
                    });
                });
        },
        listOrders() {
            this.axios
                .get("http://localhost:9151/admin/listOrders?orderFlags=0")
                .then((res) => {
                    this.tableData = res.data.data.map((order) => ({
                        ...order,
                        flag: this.formatFlag(order.flag),
                    }));
                })
                .catch((err) => {
                    console.log("err: " + err);
                });
        },
        formatFlag(flag) {
            switch (flag) {
                case 0:
                    return "未处理";
                case 1:
                    return "办理入住";
                case 2:
                    return "退订";
                case 3:
                    return "订单完成";
                default:
                    return "未知状态";
            }
        },
    },
    mounted() {
        this.listOrders();
    },
};
</script>

<style scoped>
.user-table {
    padding: 20px;
    background: linear-gradient(135deg, #e0f7fa, #80deea);
    min-height: 100vh;
}
.order-card {
    margin-bottom: 20px;
}
.order-card-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #ffffff;
    border-radius: 15px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    padding: 20px;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.order-card-container:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}
.order-info h3 {
    font-size: 20px;
    color: #00796b;
}
.order-info p {
    margin: 5px 0;
    font-size: 14px;
    color: #455a64;
}
.order-actions {
    display: flex;
    flex-direction: column;
    gap: 10px;
}
.action-btn {
    width: 120px;
    font-size: 16px;
    font-weight: bold;
}
.action-btn:hover {
    transform: scale(1.05);
}
</style>
