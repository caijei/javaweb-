
<template>
    <div class="user-table">
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="id" label="房间id"> </el-table-column>
            <el-table-column prop="number" label="房间号"> </el-table-column>
            <el-table-column prop="type" label="房间类型"> </el-table-column>
            <el-table-column prop="state" label="房间状态"> </el-table-column>
            <el-table-column prop="maxPeople" label="最大容纳人数">
            </el-table-column>
            <el-table-column prop="introduce" label="客房简介">
            </el-table-column>
            <el-table-column label="操作" width="140" fixed="right" >
                <template slot-scope="scope">
                    <el-button
                        type="danger"
                        @click="deleteRoom(scope.row.id)"
                        
                        style="font-size: 18px;"
                        >
                         删除</el-button
                    >
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
        deleteRoom(roomId) {
            // 删除房间
            this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.axios
                        .post(
                            "http://localhost:9151/admin/deleteRoom?roomId=" +
                                roomId
                        )
                        .then((res) => {
                            this.$message({
                                type: "success",
                                message: "删除成功",
                            });
                            this.listRooms();
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
        listRooms() {
            this.axios
                .get("http://localhost:9151/admin/listRooms")
                .then((res) => {
                    // console.log(res.data.data);
                    this.tableData = res.data.data;
                    for (var i = 0; i < this.tableData.length; ++i) {
                        // 房间状态
                        if (this.tableData[i].state == 0) {
                            this.tableData[i].state = "空闲";
                        } else if (this.tableData[i].state == 1) {
                            this.tableData[i].state = "已入住";
                        }

                        // 房间类型
                        switch (this.tableData[i].type) {
                            case 1:
                                this.tableData[i].type = "单人房";
                                break;
                            case 2:
                                this.tableData[i].type = "双人房";
                                break;
                            case 3:
                                this.tableData[i].type = "三人房";
                                break;
                            case 4:
                                this.tableData[i].type = "大床房";
                                break;
                            case 5:
                                this.tableData[i].type = "豪华套房";
                                break;
                        }
                    }
                })
                .catch((res) => {
                    console.log("err: " + res);
                });
        },
    },
    mounted() {
        this.listRooms();
    },
};
</script>

<style scoped>
.user-table {
    margin: 2rem;
    padding: 2rem;
    background-color: #fff; /* 深色渐变背景 */
    border-radius: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.7); /* 强烈阴影效果 */
}

/* 表格样式 */
.el-table {
    background: transparent; /* 表格透明背景 */
    color: black; /* 表格文字浅色 */
    font-size: 16px;
    border-spacing: 0 10px; /* 表格行间距 */
}

/* 表头样式 */
.el-table th {
    background: linear-gradient(90deg, #16a085, #1abc9c); /* 翠绿色渐变背景 */
    color: #ffffff;
    text-align: center;
    font-weight: bold;
    font-size: 18px;
    padding: 12px;
    border-radius: 12px;
}

/* 表格行样式 */
.el-table .el-table__row {
    transition: all 0.4s ease;
    background: rgba(236, 240, 241, 0.1); /* 半透明背景 */
    border-radius: 10px;
}

.el-table .el-table__row:hover {
    background: linear-gradient(90deg, rgba(22, 160, 133, 0.5), rgba(26, 188, 156, 0.5)); /* 行悬停渐变 */
    transform: scale(1.02); /* 缩放动画 */
    box-shadow: 0 5px 15px rgba(22, 160, 133, 0.5); /* 行阴影效果 */
}

/* 按钮样式 */
.el-button {
    background: linear-gradient(90deg, #e74c3c, #c0392b); /* 按钮红色渐变 */
    color: white;
    font-size: 16px;
    font-weight: bold;
    border-radius: 10px;
    padding: 8px 20px;
    transition: all 0.3s ease;
    box-shadow: 0 5px 15px rgba(231, 76, 60, 0.5); /* 按钮阴影 */
}

.el-button:hover {
    background: linear-gradient(90deg, #c0392b, #a93226); /* 按钮悬停更深红 */
    transform: scale(1.1); /* 放大动画 */
    box-shadow: 0 8px 20px rgba(192, 57, 43, 0.7); /* 悬停阴影增强 */
}

/* 表格内文字居中对齐 */
.el-table-column {
    text-align: center;
}

/* 滚动条样式 */
.el-table__body-wrapper {
    border-radius: 10px;
    border: 1px solid rgba(255, 255, 255, 0.2); /* 半透明边框 */
    scrollbar-color: #1abc9c #2c3e50; /* 滚动条渐变颜色 */
    scrollbar-width: thin;
}

.el-scrollbar__wrap::-webkit-scrollbar {
    width: 10px;
}

.el-scrollbar__wrap::-webkit-scrollbar-thumb {
    background: linear-gradient(90deg, #16a085, #1abc9c); /* 滚动条渐变色 */
    border-radius: 5px;
}

.el-scrollbar__wrap::-webkit-scrollbar-track {
    background: rgba(44, 62, 80, 0.5); /* 滚动条背景 */
}

/* 提示框样式 */
.el-message {
    font-size: 16px;
    font-weight: bold;
    background: linear-gradient(90deg, #3498db, #2980b9); /* 信息框渐变背景 */
    color: black;
    padding: 10px 20px;
    border-radius: 8px;
    box-shadow: 0 5px 15px rgba(52, 152, 219, 0.5); /* 信息框阴影 */
}
</style>

