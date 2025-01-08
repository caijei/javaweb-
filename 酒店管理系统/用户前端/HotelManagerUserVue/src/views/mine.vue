<template>
  <div>
    <el-container class="wrapper">
      <el-main class="section text-left">
        <div class="mine-bg"></div>
        <el-card class="box-card mine-card">
          <div class="text item">
            <el-row>
              <el-col :span="6" class="text-right">
                <img v-if="userdata.sex == 1" src="../assets/img/man.png" class="avatar" />
                <img v-else src="../assets/img/woman.png" class="avatar" />
              </el-col>
              <el-col :span="18">
                <p class="username">
                  {{userdata.userName}}
                  <i v-if="userdata.sex == 1" class="el-icon-male man"></i>
                  <i v-else class="el-icon-female woman"></i>
                </p>
                <p class="userid">id：{{userdata.id}}</p>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <el-card class="box-card mine-card-tool">
          <div class="text item">
            <el-row>
              <el-col :span="6" class="text-center">
                <router-link to="/history" class="tool-link">
                  <p class="tool-text">
                    我的订单
                  </p>
                </router-link>
              </el-col>
              <el-col :span="6" class="text-center">
                <router-link to="/checkinfo" class="tool-link">
                  <p class="tool-text">
                    我的信息
                  </p>
                </router-link>
              </el-col>
              <el-col :span="6" class="text-center">
                <router-link to="/changepswd" class="tool-link">
                  <p class="tool-text">
                    密码设置
                  </p>
                </router-link>
              </el-col>
              <el-col :span="6" class="text-center">
                <router-link to="/contact" class="tool-link">
                  <p class="tool-text">
                    联系我们
                  </p>
                </router-link>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <div class="btn-logout">
          <el-button type="danger" class="btn-block btn-logout-animated" @click="dialogVisible = true">
            退出登录
          </el-button>
        </div>
      </el-main>
      <el-footer class="footer">
        <footbar></footbar>
      </el-footer>
    </el-container>

    <el-dialog title="退出" :visible.sync="dialogVisible" width="95%">
      <span>你确定要退出登录吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false" class="dialog-cancel">取消</el-button>
        <el-button @click="logoutBtn" class="dialog-confirm">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import footbar from "@/components/footbar.vue";
export default {
  data() {
    return {
      dialogVisible: false,
      userdata: {},
    };
  },
  components: {
    footbar,
  },
  methods: {
    logoutBtn() {
      this.dialogVisible = false;
      this.$router.push("/login");
      this.axios
        .get("http://localhost:9151/user/logout")
        .then((res) => {
          // console.log("成功" + res);
        })
        .catch((res) => {
          console.log("失败" + res);
        });
    },
  },
  mounted() {
    this.axios
      .get("http://localhost:9151/user/userDetail")
      .then((res) => {
        this.userdata = res.data.data;
        // console.log(this.userdata);
      })
      .catch((res) => {
        console.log("失败" + res);
      });
  },
};
</script>

<style scoped>
/* 动态渐变背景 */
.mine-bg {
  background: linear-gradient(135deg, #6a5acd, #1e90ff);
  height: 30vh;
  background-size: 200% 200%;
  animation: gradient-animation 5s infinite alternate;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

@keyframes gradient-animation {
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
}

/* 卡片阴影效果 */
.mine-card,
.mine-card-tool {
  margin: -3rem 1rem 1rem 1rem;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  background: #ffffff;
}

/* 头像样式 */
.avatar {
  width: 5rem;
  border-radius: 50%;
  border: 3px solid #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* 用户名和性别图标 */
.username {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.man {
  color: #409eff;
}

.woman {
  color: #ff557f;
}

/* 工具卡片的链接和文字 */
.tool-link {
  text-decoration: none;
  color: inherit;
}

.tool-text {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  transition: color 0.3s;
}

.tool-link:hover .tool-text {
  color: #1e90ff;
}

/* 退出按钮样式 */
.btn-logout-animated {
  background: linear-gradient(45deg, #ff6b6b, #ff8e53);
  color: #fff;
  font-size: 1.2rem;
  border-radius: 25px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  transition: all 0.3s;
}

.btn-logout-animated:hover {
  background: linear-gradient(45deg, #ff8e53, #ff6b6b);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

/* 对话框按钮样式 */
.dialog-cancel,
.dialog-confirm {
  font-size: 1rem;
  border-radius: 20px;
}

.dialog-cancel {
  background-color: #ebeef5;
  color: #333;
}

.dialog-confirm {
  background-color: #ff6b6b;
  color: #fff;
}

.dialog-confirm:hover {
  background-color: #ff8e53;
}

/* 全局字体和布局调整 */
.text {
  font-size: 1rem;
  color: #333;
}

.footer {
  background: #f5f5f5;
  box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
  padding: 1rem;
  text-align: center;
}
</style>
