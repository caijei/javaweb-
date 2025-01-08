<template>
    <div>
        <el-container class="wrapper">
            <el-main class="section text-left">
                <div class="res-bg"></div>
                <el-card class="box-card res-form">
                    <div slot="header" class="clearfix">
                        <span class="card-header-title">预订客房</span>
                    </div>
                    <div class="text item">
                        <el-form
                            :model="resinfo"
                            :rules="rules"
                            ref="ruleForm"
                            label-width="80px"
                            class="demo-ruleForm"
                        >
                            <el-form-item
                                label="客房类型"
                                prop="type"
                                required
                                @click="resolveData"
                            >
                                <el-select
                                    v-model="resinfo.type"
                                    placeholder="请选择"
                                    :disabled="lockCondition"
                                    class="el-select-custom"
                                >
                                    <el-option
                                        v-for="item in roomtype"
                                        :key="item.id"
                                        :label="item.typeName"
                                        :value="item.id"
                                    ></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="入住时间" required>
                                <el-col :span="24" class="mb-1">
                                    <el-form-item prop="indate">
                                        <el-date-picker
                                            :disabled="lockCondition"
                                            type="datetime"
                                            placeholder="入住日期"
                                            v-model="resinfo.indate"
                                            style="width: 100%"
                                            class="el-date-picker-custom"
                                        />
                                    </el-form-item>
                                </el-col>
                                <el-col :span="24">
                                    <el-form-item prop="leavedate">
                                        <el-date-picker
                                            :disabled="lockCondition"
                                            type="datetime"
                                            placeholder="离店日期"
                                            v-model="resinfo.leavedate"
                                            style="width: 100%"
                                            class="el-date-picker-custom"
                                        />
                                    </el-form-item>
                                </el-col>
                            </el-form-item>

                            <el-form-item>
                                <el-button
                                    :type="searchBtnType"
                                    @click="checkroom"
                                    :disabled="checkBtnDis"
                                    class="search-btn"
                                >
                                    <i
                                        :class="searchicon"
                                        style="font-size: 18px"
                                    ></i>
                                    查询可用房间
                                </el-button>
                            </el-form-item>

                            <el-form-item label="房间号码" prop="id" required>
                                <el-select
                                    v-model="resinfo.id"
                                    placeholder="请先选择房间号"
                                    :disabled="isNotSelect"
                                    class="el-select-custom"
                                >
                                    <el-option
                                        v-for="item in roomnumber"
                                        :key="item.id"
                                        :label="item.number"
                                        :value="item.id"
                                    ></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="住店人数" prop="num" required>
                                <el-input-number
                                    v-model="resinfo.num"
                                    :min="1"
                                    :max="maxp"
                                    label="请输入"
                                    :disabled="isNotSelect"
                                    class="el-input-number-custom"
                                />
                                <span class="ml-1">人</span>
                            </el-form-item>
                            <el-form-item label="提供发票" prop="invoice">
                                <el-switch
                                    v-model="resinfo.isNeedInvoice"
                                    :disabled="isNotSelect"
                                    class="el-switch-custom"
                                />
                            </el-form-item>
                            <el-form-item label="价格">
                                <span class="room-price">{{ sumprice }} 元</span>
                            </el-form-item>
                            <el-form-item>
                                <el-button
                                    type="primary"
                                    @click="submitForm('ruleForm')"
                                    class="submit-btn"
                                    :disabled="isNotSelect"
                                >
                                    立即预订
                                </el-button>
                                <br />
                                <el-button
                                    @click="resetForm('ruleForm')"
                                    type="warning"
                                    class="reset-btn"
                                >
                                    重置选项
                                </el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-card>
            </el-main>
            <el-footer class="footer">
                <footbar></footbar>
            </el-footer>

            <el-dialog title="提示" :visible.sync="dialogVisible" width="95%">
                <span>你确定要提交预订单吗？</span>
                <span slot="footer" class="dialog-footer">
                    <el-button
                        @click="dialogVisible = false"
                        style="font-size: 18px;"
                    >
                        取消
                    </el-button>
                    <el-button type="primary" @click="confirmSubmitForm">
                        确定
                    </el-button>
                </span>
            </el-dialog>
        </el-container>
    </div>
</template>

<script>
import footbar from "@/components/footbar.vue";
export default {
    data() {
        return {
            resinfo: {
                id: "",
                type: "",
                indate: "",
                leavedate: "",
                num: "",
                isNeedInvoice: false,
            },
            price: 0,
            sumprice: 0,
            maxp: 1,
            lockCondition: false,
            isNotSelect: true,
            checkBtnDis: false,
            searchicon: "el-icon-a-042",
            searchBtnType: "primary",
            dialogVisible: false,
            roomnumber: [],
            roomtype: [],
            rules: {
                type: [
                    {
                        required: true,
                        message: "请选择房间类型",
                        trigger: "change",
                    },
                ],
                id: [
                    {
                        required: true,
                        message: "请选择房间号",
                        trigger: "change",
                    },
                ],
                indate: [
                    {
                        type: "date",
                        required: true,
                        message: "请选择入住时间",
                        trigger: "change",
                    },
                ],
                leavedate: [
                    {
                        type: "date",
                        required: true,
                        message: "请选择离店时间",
                        trigger: "change",
                    },
                ],
            },
        };
    },
    components: {
        footbar,
    },
    methods: {
        submitForm(formName) {
            this.dialogVisible = true;
        },
        resetForm(formName) {
            this.checkBtnDis = false;
            this.searchicon = "el-icon-a-042";
            this.searchBtnType = "primary";
            this.isNotSelect = true;
            this.price = 0;
            this.sumprice = 0;
            this.resinfo.isNeedInvoice = false;
            this.$refs[formName].resetFields();
        },
        confirmSubmitForm() {
            this.dialogVisible = false;
            if (
                this.resinfo.id == undefined ||
                this.resinfo.id == "" ||
                this.resinfo.id == null
            ) {
                alert("请选择房间号");
                return;
            }
            let json = {
                roomId: parseInt(this.resinfo.id),
                inTime: this.changeTimeStr(this.resinfo.indate),
                leaveTime: this.changeTimeStr(this.resinfo.leavedate),
                realPeople: this.resinfo.num,
                fapiao: this.fapiao,
            };
            this.axios
                .post("http://localhost:9151/user/bookRoom", json)
                .then((res) => {
                    if (res.data.code != "200") {
                        this.$message({
                            message: "登录信息过期",
                            type: "error",
                        });
                        this.$router.push("/");
                    } else {
                        this.$router.push("/submitok");
                    }
                })
                .catch((res) => {
                    this.$router.push("/submitfail");
                });
        },
        resolveData() {
            this.axios
                .get("http://localhost:9151/user/listTypes")
                .then((res) => {
                    this.roomtype = res.data.data;
                })
                .catch(() => {
                    console.log("error");
                });
        },
        checkroom() {
            this.checkBtnDis = true;
            this.searchicon = "el-icon-loading";
            this.axios
                .post("http://localhost:9151/user/listRoomsByTypeId", {
                    typeId: this.resinfo.type,
                    inTime: this.changeTimeStr(this.resinfo.indate),
                    leaveTime: this.changeTimeStr(this.resinfo.leavedate),
                })
                .then((res) => {
                    this.roomnumber = res.data.data;
                    this.searchicon = "el-icon-check";
                    this.searchBtnType = "success";
                    this.isNotSelect = false;
                    this.lockCondition = true;
                    this.price = this.roomnumber[0].type.price;
                    this.maxp = this.roomnumber[0].maxPeople;
                    this.calcPrice();
                })
                .catch(() => {
                    this.searchicon = "el-icon-close";
                    this.searchBtnType = "danger";
                    console.log("error");
                });
        },
        changeTimeStr(str) {
            str = str.toString();
            str = str.replace(/ GMT.+$/, ""); 
            let d = new Date(str);
            let a = [
                d.getFullYear(),
                d.getMonth() + 1,
                d.getDate(),
                d.getHours(),
                d.getMinutes(),
                d.getSeconds(),
            ];
            for (var i = 0, len = a.length; i < len; i++) {
                if (a[i] < 10) {
                    a[i] = "0" + a[i];
                }
            }
            str = a[0] + "-" + a[1] + "-" + a[2] + " " + a[3] + ":" + a[4];
            return str;
        },
        calcPrice() {
            let realday =
                Math.abs(this.resinfo.leavedate - this.resinfo.indate) / 86400000;
            realday = Math.ceil(realday);
            let price = this.price;
            this.sumprice = price * realday;
        },
    },
    mounted() {
        this.resolveData();
    },
    computed: {
        fapiao() {
            return this.resinfo.isNeedInvoice ? 1 : 0;
        },
    },
};
</script>

<style scoped="scoped">
.res-bg {
    background: linear-gradient(to bottom right, #6a11cb, #2575fc);
    background-size: cover;
    background-image: url(../assets/img/reservation.jpg);
    height: 30vh;
    border-bottom-left-radius: 15px;
    border-bottom-right-radius: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.text {
    font-size: 16px;
    color: #fff;
    line-height: 1.6;
}

.item {
    padding: 12px 0;
}

.res-form {
    margin: -5rem 1rem 1rem 1rem;
    background: #fff;
    border-radius: 15px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-header-title {
    font-size: 22px;
    color: #4e4e4e;
    font-weight: bold;
}

.search-btn {
    width: 100%;
    font-size: 18px;
    background: #007bff;
    color: white;
    transition: all 0.3s ease;
}

.search-btn:hover {
    background: #0056b3;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.el-form-item {
    margin-bottom: 20px;
}

.room-price {
    font-size: 24px;
    color: #e74c3c;
}

.submit-btn {
    width: 48%;
    margin-right: 2%;
}

.reset-btn {
    width: 48%;
}

.el-input-number-custom,
.el-select-custom,
.el-date-picker-custom {
    border-radius: 10px;
}

.el-switch-custom {
    width: 50px;
}

.footer {
    text-align: center;
    padding: 10px 0;
    background: #f1f1f1;
}

.el-dialog {
    font-size: 18px;
}
</style>
