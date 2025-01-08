<template>
    <div class="user-table">
        <div v-for="(item, index) in tableData" :key="index" class="order-card">
            <div class="card-content">
                <div class="order-details">
                    <h3>订单编号: {{ item.id }}</h3>
                    <p>用户ID: {{ item.userId }}</p>
                    <p>房间ID: {{ item.roomId }}</p>
                    <p>下单时间: {{ item.createTime }}</p>
                    <p>入住时间: {{ item.inTime }}</p>
                    <p>离店时间: {{ item.leaveTime }}</p>
                    <p>总计价格: ¥{{ item.realPrice }}</p>
                    <p>入住人数: {{ item.realPeople }} 人</p>
                    <p>发票: {{ item.fapiao === 1 ? "需要" : "不需要" }}</p>
                    <p>受理状态: {{ item.flag }}</p>
                </div>
            </div>
        </div>
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
        listOrders() {
            this.axios
                .get("http://localhost:9151/admin/listOrders?orderFlags=1,2,3")
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
    background: linear-gradient(135deg, #e3f2fd, #bbdefb);
    min-height: 100vh;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
}
.order-card {
    background: #ffffff;
    border-radius: 15px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    padding: 20px;
    width: 300px;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.order-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}
.card-content {
    display: flex;
    flex-direction: column;
    gap: 10px;
}
.order-details h3 {
    font-size: 20px;
    color: #1565c0;
    margin-bottom: 10px;
}
.order-details p {
    font-size: 14px;
    color: #455a64;
    margin: 0;
    line-height: 1.6;
}
</style>
