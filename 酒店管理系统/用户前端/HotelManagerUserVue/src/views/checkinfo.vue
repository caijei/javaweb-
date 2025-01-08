<template>
  <div>
    <el-container class="wrapper">
      <el-main class="section">
        <div class="changeinfo-bg"></div>
        <el-card class="box-card changeinfo-card">
          <div class="text item">
            <p class="changeinfo-title">查看个人信息</p>
            <el-row :gutter="10" v-for="(value, label) in userInfoMap" :key="label">
              <el-col :span="8" class="text-right">
                <p class="info-label">{{ label }}：</p>
              </el-col>
              <el-col :span="16" class="text-left">
                <p class="info-value">{{ value }}</p>
              </el-col>
            </el-row>
            <el-button type="info" @click="back" class="back-btn">
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
      userinfo: {},
    };
  },
  components: {
    footbar,
  },
  mounted() {
    this.$store.commit("setMine");
    this.axios
      .get("http://localhost:9151/user/userDetail")
      .then((res) => {
        this.userinfo = res.data.data;
      })
      .catch((err) => {
        console.error("请求失败", err);
      });
  },
  computed: {
    showsex() {
      return this.userinfo.sex == 0 ? "女" : "男";
    },
    userInfoMap() {
      return {
        用户名: this.userinfo.userName,
        创建时间: this.userinfo.createTime,
        账号邮箱: this.userinfo.email,
        性别: this.showsex,
        用户ID: this.userinfo.id,
        账号积分: this.userinfo.jifen,
        手机号: this.userinfo.phone,
      };
    },
  },
  methods: {
    back() {
      this.$router.push("/mine");
    },
  },
};
</script>

<style scoped>
.changeinfo-bg {
  background: linear-gradient(45deg, #6a5acd, #1e90ff);
  height: 20vh;
  animation: gradient-move 4s infinite alternate;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

@keyframes gradient-move {
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
}

.wrapper {
  min-height: 94vh;
  background: #f5f5f5;
}

.changeinfo-card {
  margin: -5rem 1rem 1rem 1rem;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  background: #ffffff;
}

.changeinfo-title {
  font-size: 2.2rem;
  font-weight: bold;
  color: #4a4a4a;
  text-align: center;
  margin: 1.5rem 0;
}

.text {
  font-size: 16px;
  color: #333;
}

.info-label {
  font-size: 1.1rem;
  font-weight: bold;
  color: #555;
}

.info-value {
  font-size: 1rem;
  color: #444;
}

.back-btn {
  margin-top: 2rem;
  font-size: 1.1rem;
  color: #fff;
  background: linear-gradient(to right, #6a5acd, #1e90ff);
  border: none;
  border-radius: 25px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
}

.back-btn:hover {
  background: linear-gradient(to right, #1e90ff, #6a5acd);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.footer {
  background: #ffffff;
  box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
}

.el-main {
  max-height: 94vh;
  padding: 2rem;
}

.el-row {
  margin-bottom: 1rem;
}

#app {
  overflow: hidden;
}
</style>
