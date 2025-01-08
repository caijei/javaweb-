<template>
  <div>
    <el-container class="wrapper">
      <el-main class="section text-center">
        <div class="changepswd-bg"></div>
        <el-card class="box-card changepswd-card">
          <div class="text item">
            <p class="changepswd-title">
              修改密码
            </p>
            <el-form :model="changepswd" status-icon ref="changepswd" label-width="80px">
              <el-form-item label="原密码" prop="old">
                <el-input type="password" v-model="changepswd.old" suffix-icon="el-icon-unlock"></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="new">
                <el-input type="password" v-model="changepswd.new" suffix-icon="el-icon-lock"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="confirm">
                <el-input type="password" v-model="changepswd.confirm" suffix-icon="el-icon-lock"></el-input>
              </el-form-item>
            </el-form>
            <el-button :type="btnType" @click="changepswdBtn" class="changepswdbtn animated-btn" :disabled="disabled">
              <i :class="iconstyle"></i> 修改
            </el-button>
            <el-button @click="back" class="return-btn">
              <i class="iconfont icon-r-left"></i> 返回
            </el-button>
          </div>
        </el-card>
      </el-main>
    </el-container>
    <el-footer class="footer text-center">
      <footbar></footbar>
    </el-footer>
  </div>
</template>

<script>
import footbar from "@/components/footbar.vue";
export default {
  data() {
    return {
      changepswd: {
        old: "",
        new: "",
        confirm: "",
      },
      iconstyle: "el-icon-a-032",
      disabled: false,
      btnType: "primary",
    };
  },
  components: {
    footbar,
  },
  methods: {
    back() {
      this.$router.push("/mine");
    },
    changepswdBtn() {
      this.iconstyle = "el-icon-loading";
      this.disabled = true;
      this.axios
        .post("http://localhost:9151/user/updatePassword", {
          oldPassword: this.changepswd.old,
          newPassword: this.changepswd.new,
        })
        .then((res) => {
          this.iconstyle = "el-icon-check";
          this.btnType = "success";
          setTimeout(() => {
            this.disabled = false;
            this.$router.push("/mine");
          }, 2000);
        })
        .catch((res) => {
          this.iconstyle = "el-icon-close";
          this.btnType = "danger";
          setTimeout(() => {
            this.disabled = false;
            this.iconstyle = "el-icon-a-032";
            this.btnType = "primary";
          }, 2000);
        });
    },
  },
};
</script>

<style scoped>
.changepswd-bg {
  background: linear-gradient(135deg, #4a90e2, #9013fe);
  height: 20vh;
  background-size: cover;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.changepswd-title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #4a90e2;
  margin: 1rem 0;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.changepswd-card {
  margin: -5rem 1rem 1rem 1rem;
  padding: 1.5rem;
  border-radius: 15px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
  background: white;
}

.animated-btn {
  background: linear-gradient(90deg, #4a90e2, #9013fe);
  color: white;
  border-radius: 25px;
  font-size: 1.2rem;
  padding: 10px 20px;
  transition: transform 0.2s, background 0.3s;
}

.animated-btn:hover {
  transform: scale(1.1);
  background: linear-gradient(90deg, #9013fe, #4a90e2);
}

.return-btn {
  margin-top: 1rem;
  color: #4a90e2;
  font-size: 1.2rem;
  font-weight: bold;
  background: transparent;
  border: 2px solid #4a90e2;
  border-radius: 25px;
  padding: 10px 20px;
  transition: all 0.3s;
}

.return-btn:hover {
  color: white;
  background: #4a90e2;
  border-color: #4a90e2;
}

.el-form-item input {
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  padding: 8px;
  transition: border-color 0.3s;
}

.el-form-item input:focus {
  border-color: #4a90e2;
  outline: none;
}

.wrapper {
  min-height: 94vh;
  background: #f7f9fc;
}

.footer {
  background: #4a90e2;
  color: white;
  padding: 10px 0;
  text-align: center;
  font-size: 1rem;
}

.footer a {
  color: #ffd700;
}
</style>
