<template>
    <div class="user-table">
        <el-table :data="tableData" stripe style="width: 100%; border-radius: 10px; overflow: hidden;">
            <el-table-column prop="id" label="ID" class="text-center"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="userName" label="用户名"></el-table-column>
            <el-table-column prop="sex" label="性别"></el-table-column>
            <el-table-column prop="phone" label="手机号码"></el-table-column>
            <el-table-column prop="jifen" label="账户积分"></el-table-column>
            <el-table-column prop="state" label="状态"></el-table-column>
            <el-table-column label="操作" width="140" fixed="right">
                <template slot-scope="scope">
                    <el-button
                        type="danger"
                        @click="deleteUser(scope.row.id)"
                        style="font-size: 18px; background-color: #f56c6c; border: none; border-radius: 5px; transition: all 0.3s ease;"
                        @mouseover="handleMouseOver"
                        @mouseleave="handleMouseLeave"
                    >
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
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
        deleteUser(id) {
            // 删除用户
            this.$confirm("确定删除该用户？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.axios
                        .post(
                            "http://localhost:9151/admin/deleteUser?userId=" +
                                id
                        )
                        .then((res) => {
                            this.$message({
                                type: "success",
                                message: "删除成功",
                            });
                            this.listUser();
                        })
                        .catch((res) => {
                            this.$message({
                                type: "error",
                                message: "删除失败",
                            });
                        });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
        listUser() {
            this.axios
                .get("http://localhost:9151/admin/listUsers")
                .then((res) => {
                    this.tableData = res.data.data;
                    for (var i = 0; i < this.tableData.length; i++) {
                        // 性别
                        if (this.tableData[i].sex == 0) {
                            this.tableData[i].sex = "女";
                        } else if (this.tableData[i].sex == 1) {
                            this.tableData[i].sex = "男";
                        }

                        // 状态
                        if (this.tableData[i].state == 0) {
                            this.tableData[i].state = "游客";
                        } else if (this.tableData[i].state == 1) {
                            this.tableData[i].state = "会员";
                        }
                    }
                })
                .catch((res) => {
                    console.log("err:" + res);
                });
        },
        handleMouseOver(event) {
            event.target.style.transform = "scale(1.1)";
        },
        handleMouseLeave(event) {
            event.target.style.transform = "scale(1)";
        }
    },
    mounted() {
        this.listUser();
    },
};
</script>

<style>
.user-table {
    margin: 2rem;
}

.el-table {
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.el-table th {
    background-color: #f5f5f5;
    font-weight: bold;
    color: #333;
}

.el-table td {
    color: #555;
}

.el-button {
    font-size: 18px;
    padding: 8px 16px;
    border-radius: 5px;
    transition: all 0.3s ease;
}

.el-button:hover {
    background-color: #ff7070;
    transform: scale(1.05);
}
</style>
