<template>
    <el-row>
        <el-col :span="16" :offset="4">
            <div class="addnew-form">
                <div class="text item">
                    <p class="addnew-title" style="color: black;">输入房间信息</p>
                    <el-form
                        :model="addnew"
                        status-icon
                        ref="addnew"
                        label-width="100px"
                    >
                        <el-form-item label="房间号" prop="number">
                            <el-input
                                type="text"
                                v-model="addnew.number"
                                class="custom-input"
                            ></el-input>
                        </el-form-item>
                        <el-form-item label="房间类型" prop="type">
                            <el-select
                                v-model="addnew.type"
                                placeholder="请选择"
                                class="custom-select"
                            >
                                <el-option :label="'单人房'" :value="1"></el-option>
                                <el-option :label="'双人房'" :value="2"></el-option>
                                <el-option :label="'三人房'" :value="3"></el-option>
                                <el-option :label="'大床房'" :value="4"></el-option>
                                <el-option :label="'豪华套房'" :value="5"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="房间状态" prop="state">
                            <el-select
                                v-model="addnew.state"
                                placeholder="请选择"
                                class="custom-select"
                            >
                                <el-option :label="'空闲'" :value="0"></el-option>
                                <el-option :label="'已入住'" :value="1"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="最大容纳人数" prop="maxPeople">
                            <el-input
                                type="text"
                                v-model="addnew.maxPeople"
                                class="custom-input"
                            ></el-input>
                        </el-form-item>
                        <el-form-item label="客房详情" prop="introduce">
                            面积（m²）:
                            <el-input
                                type="text"
                                v-model="addnew.size"
                                class="custom-input"
                            ></el-input><br />
                            电脑:
                            <el-radio v-model="addnew.computer" label="有">有</el-radio>
                            <el-radio v-model="addnew.computer" label="无">无</el-radio><br />
                            热水:
                            <el-radio v-model="addnew.hotWater" label="有">有</el-radio>
                            <el-radio v-model="addnew.hotWater" label="无">无</el-radio><br />
                            WIFI:
                            <el-radio v-model="addnew.wifi" label="有">有</el-radio>
                            <el-radio v-model="addnew.wifi" label="无">无</el-radio><br />
                            电视:
                            <el-radio v-model="addnew.tv" label="有">有</el-radio>
                            <el-radio v-model="addnew.tv" label="无">无</el-radio><br />
                            早餐:
                            <el-radio v-model="addnew.breakfast" label="有">有</el-radio>
                            <el-radio v-model="addnew.breakfast" label="无">无</el-radio>
                        </el-form-item>
                    </el-form>
                    <el-button
                        type="success"
                        @click="addnewBtn"
                        class="addnewbtn custom-button"
                        :disabled="disabled"
                    >
                        确认添加
                    </el-button>
                </div>
            </div>
        </el-col>
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <span>新房间信息添加成功。</span>
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
            addnew: {
                number: "",
                type: "",
                state: "",
                maxPeople: "",
                introduce: "",
                size: "",
                computer: "有",
                hotWater: "有",
                wifi: "有",
                tv: "有",
                breakfast: "有",
            },
            dialogVisible: false,
        };
    },
    methods: {
        addnewBtn() {
            if (this.addnew.number == "") {
                this.$message.error("请提供房间号");
                return;
            }
            if (this.addnew.type == "") {
                this.$message.error("请选择房间类型");
                return;
            }
            if (this.addnew.state === "") {
                this.$message.error("请选择房间状态");
                return;
            }
            if (this.addnew.maxPeople == "") {
                this.$message.error("请填写最大容纳人数");
                return;
            }
            if (this.addnew.size == "") {
                this.$message.error("请填写房间面积");
                return;
            }
            this.addnew.introduce =
                `面积: ${this.addnew.size}m², 容纳:1-${this.addnew.maxPeople}人, 电脑: ${this.addnew.computer}, 热水: ${this.addnew.hotWater}, WIFI: ${this.addnew.wifi}, 电视: ${this.addnew.tv}, 早餐: ${this.addnew.breakfast}`;
            this.axios
                .post("http://localhost:9151/admin/addRoom", this.addnew)
                .then((res) => {
                    if (res.data.code == "200") {
                        this.dialogVisible = true;
                        this.addnew = {
                            number: "",
                            type: "",
                            state: "",
                            maxPeople: "",
                            introduce: "",
                            size: "",
                            computer: "有",
                            hotWater: "有",
                            wifi: "有",
                            tv: "有",
                            breakfast: "有",
                        };
                    } else {
                        this.$message.error(res.data.data);
                    }
                })
                .catch((res) => {
                    console.log("err: " + res);
                });
        },
    },
};
</script>

<style>
.addnew-form {
    margin: 2rem;
    width: 90%;
    max-width: 1200px;
    background-color: #fff;
    padding: 3rem;
    border-radius: 15px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.addnew-title {
    font-size: 1.8rem;
    font-weight: bold;
    text-align: center;
    margin-bottom: 1.5rem;
    color: #333;
}

.custom-input,
.custom-select {
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.addnewbtn {
    display: block;
    margin: 1rem auto;
    width: 100%;
    height: 50px;
    font-size: 18px;
    background: linear-gradient(90deg, #4facfe 0%, #00f2fe 100%);
    color: #fff;
    border: none;
    border-radius: 25px;
    transition: all 0.3s;
}

.addnewbtn:hover {
    transform: scale(1.05);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.dialog-footer {
    text-align: center;
}
</style>
