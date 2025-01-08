<template>
  <div>
    <el-container class="wrapper">
      <el-main class="section text-center">
        <div class="contact-bg"></div>
        <el-card class="box-card contact-card">
          <div class="text item">
            <p class="contact-title">联系我们</p>
            <div class="contact-info text-left">
              <p class="contact-eng">Jxust Hotel, Inc</p>
              <p>江西理工大学三江校区</p>
              <p>江西理工大学城星级宾馆</p>
              <p>Tel：010-12345678</p>
              <p>Fax：010-88888889</p>
            </div>
            <p class="contact-subtitle">服务评价</p>
            <span>为我们的服务打个分吧~</span>
            <el-rate v-model="contact.eva" :colors="colors" class="mb-1 rate-styled">
            </el-rate>
            <el-form :model="contact" status-icon ref="contact" class="mb-1">
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10}"
                placeholder="输入你的建议或意见吧..."
                v-model="contact.comment"
                class="custom-input"
              >
              </el-input>
            </el-form>
            <el-button
              @click="submitBtn"
              :type="btnType"
              class="contactbtn animated-btn"
              :disabled="disabled"
            >
              <i :class="iconstyle"></i> {{ btnText }}
            </el-button>
            <el-button @click="back" class="return-btn">
              返回
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
import store from "./../store";
export default {
  data() {
    return {
      contact: {
        comment: "",
        eva: null,
      },
      iconstyle: "el-icon-document-checked",
      disabled: false,
      btnType: "primary",
      isRealcontact: false,
      colors: ["#6A9EFF", "#FF9F43", "#FF5656"],
      btnText: "提交反馈",
    };
  },
  components: {
    footbar,
  },
  mounted() {
    this.$store.commit("setMine");
  },
  methods: {
    back() {
      this.$router.push("/mine");
    },
    submitBtn() {
      console.log(this.contact);
      this.disabled = true;
      this.iconstyle = "el-icon-loading";
      this.axios
        .post("http://localhost:9151/user/publishComment", {
          information: this.contact.comment,
          type: this.contact.eva,
        })
        .then((res) => {
          console.log(res);
          if (res.data.code == 200) {
            this.iconstyle = "el-icon-check";
            this.btnType = "success";
            this.btnText = "您的反馈已提交！";
          } else {
            this.iconstyle = "el-icon-close";
            this.btnType = "danger";
            this.btnText = res.data.data;
          }
        })
        .catch((res) => {
          console.log(res);
          this.iconstyle = "el-icon-close";
          this.btnType = "danger";
          this.btnText = "您的反馈未能提交";
        });
    },
  },
};
</script>

<style scoped>
.contact-bg {
  background: linear-gradient(45deg, #6a9eff, #a867ff);
  height: 20vh;
  background-size: 100%;
  border: 0px solid transparent;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.text {
  font-size: 16px;
  color: #333;
}

.contact-title {
  font-size: 2.5rem;
  font-weight: 600;
  margin-top: 1rem;
  color: #4e62cc;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.contact-info p {
  margin: 0.4rem 0;
  color: #555;
}

.contact-eng {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 0;
  color: #4e62cc;
}

.contact-subtitle {
  font-size: 1.8rem;
  font-weight: 400;
  margin: 1rem 0;
  color: #ff5656;
}

.contact-card {
  margin: -5rem 1rem 1rem 1rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.wrapper {
  min-height: 94vh;
  background: #f5f8ff;
}

.el-main {
  max-height: 94vh;
}

.rate-styled {
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.custom-input {
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  padding: 10px;
  transition: border-color 0.3s ease-in-out;
}

.custom-input:focus {
  border-color: #4e62cc;
  outline: none;
}

.animated-btn {
  margin-top: 1rem;
  background: linear-gradient(90deg, #6a9eff, #ff5656);
  color: white;
  border-radius: 20px;
  font-size: 1rem;
  font-weight: bold;
  transition: transform 0.2s, background 0.3s;
}

.animated-btn:hover {
  transform: scale(1.05);
  background: linear-gradient(90deg, #ff5656, #6a9eff);
}

.return-btn {
  margin-top: 1rem;
  color: #4e62cc;
  font-size: 1rem;
  font-weight: bold;
}

.footer {
  background: #4e62cc;
  color: white;
  padding: 10px 0;
  font-size: 0.9rem;
  text-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
}
</style>
