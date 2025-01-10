<template>
    <el-row>
        <el-col :span="12" :offset="6">
            <div class="change-form">
                <div class="text item">
                    <p class="change-title">输入修改房间 ID</p>
                    <el-form :model="change" status-icon ref="change" label-width="80px">
                        <el-form-item label="ID" prop="id">
                            <el-input type="text" v-model="change.id"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button type="primary" @click="queryBtn" class="mb-1 query-btn">
                        查询
                    </el-button>
                    <p class="change-title">输入修改信息</p>
                    <el-form :model="change" status-icon ref="change" label-width="120px">
                        <el-form-item label="房间号" prop="number">
                            <el-input type="text" v-model="change.number"></el-input>
                        </el-form-item>

                        <el-form-item label="房间类型" prop="type">
                            <el-select v-model="change.type" placeholder="请选择">
                                <el-option :label="'单人房'" :value="1"></el-option>
                                <el-option :label="'双人房'" :value="2"></el-option>
                                <el-option :label="'三人房'" :value="3"></el-option>
                                <el-option :label="'大床房'" :value="4"></el-option>
                                <el-option :label="'豪华套房'" :value="5"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="房间状态" prop="state">
                            <el-select v-model="change.state" placeholder="请选择">
                                <el-option :label="'空闲'" :value="0"></el-option>
                                <el-option :label="'已入住'" :value="1"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="最大容纳人数" prop="maxPeople">
                            <el-input type="text" v-model="change.maxPeople"></el-input>
                        </el-form-item>

                        <el-form-item label="客房详情" prop="introduce">
                            <div class="details-label">
                                面积（m²）:
                                <el-input type="text" v-model="change.size"></el-input>
                            </div>
                            <div class="details-label">
                                空调:
                                <el-radio v-model="change.airCondition" label="有">有</el-radio>
                                <el-radio v-model="change.airCondition" label="无">无</el-radio>
                            </div>
                            <div class="details-label">
                                电脑:
                                <el-radio v-model="change.computer" label="有">有</el-radio>
                                <el-radio v-model="change.computer" label="无">无</el-radio>
                            </div>
                            <div class="details-label">
                                热水:
                                <el-radio v-model="change.hotWater" label="有">有</el-radio>
                                <el-radio v-model="change.hotWater" label="无">无</el-radio>
                            </div>
                            <div class="details-label">
                                WIFI:
                                <el-radio v-model="change.wifi" label="有">有</el-radio>
                                <el-radio v-model="change.wifi" label="无">无</el-radio>
                            </div>
                            <div class="details-label">
                                电视:
                                <el-radio v-model="change.tv" label="有">有</el-radio>
                                <el-radio v-model="change.tv" label="无">无</el-radio>
                            </div>
                            <div class="details-label">
                                早餐:
                                <el-radio v-model="change.breakfast" label="有">有</el-radio>
                                <el-radio v-model="change.breakfast" label="无">无</el-radio>
                            </div>
                        </el-form-item>
                    </el-form>
                    <el-button type="success" @click="changeBtn" class="changebtn" :disabled="disabled">
                        确认修改
                    </el-button>
                </div>
            </div>
        </el-col>
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <span>{{ msgText }}</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">确定</el-button>
            </span>
        </el-dialog>
    </el-row>
</template>

<script>
export default {
    data() {
        return {
            change: {
                id: "",
                number: "",
                type: "",
                state: "",
                maxPeople: "",
                introduce: "",
                size: "",
                airCondition: "有",
                computer: "有",
                hotWater: "有",
                wifi: "有",
                tv: "有",
                breakfast: "有",
            },
            disabled: false,
            dialogVisible: false,
            msgText: "",
        };
    },
    methods: {
       
    },
};
</script>

<style scoped>
body {
    background: linear-gradient(135deg, #ff9a9e, #fad0c4);
    font-family: 'Roboto', sans-serif;
    color: #333;
}

.change-form {
    background: #fff;
    border-radius: 20px;
    padding: 3rem; /* 增加内边距 */
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
    max-width: 800px; /* 增加盒子宽度 */
    margin: auto; /* 居中 */
}

.change-title {
    font-size: 2rem; /* 增加标题字体大小 */
    font-weight: bold;
    margin-bottom: 1.5rem;
    text-align: center;
    color: #4a4a4a;
}

.el-input {
    border-radius: 8px;
}

.el-select {
    border-radius: 8px;
}

.query-btn {
    width: 100%;
    background: linear-gradient(to right, #6a11cb, #2575fc);
    color: #fff;
    font-size: 18px;
    border-radius: 8px;
    transition: all 0.3s ease;
    margin-bottom: 1.5rem; /* 增加按钮间距 */
}

.query-btn:hover {
    transform: scale(1.05);
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.changebtn {
    width: 100%;
    background: linear-gradient(to right, #00b09b, #96c93d);
    color: #fff;
    font-size: 18px;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.changebtn:hover {
    transform: scale(1.05);
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.el-form-item {
    margin-bottom: 1.5rem;
}

/* 确保客房详情的标签颜色为黑色 */
.details-label {
    font-weight: bold;
    color: #000;
    margin-bottom: 15px;
    font-size: 18px; /* 增加字体大小 */
}
</style>
