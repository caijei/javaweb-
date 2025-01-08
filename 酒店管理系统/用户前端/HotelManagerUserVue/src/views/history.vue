<template>
  <el-container class="wrapper">
    <el-main class="section text-left">
      <el-row>
        <el-col :span="24">
          <el-card class="box-card">
            <el-row>
              <el-col :span="18">
                <span class="order-num">当前共查找到{{length}}条订单</span>
              </el-col>
              <el-col :span="6" class="text-right">
                <el-button @click="back" type="text" class="order-back">返回</el-button>
              </el-col>
            </el-row>
          </el-card>
        </el-col>

        <el-col :span="24" v-for="(order, index) in orderdata" :key="index">
          <el-card class="box-card order-card">
            <el-row class="text item" :gutter="10">
              <el-col :span="8" class="order-head">
                <div class="text-center">
                  <i class="iconfont icon-r-paper order-icon"></i>
                </div>
                <div class="text-center">
                  <p class="order-id">订单号</p>
                  <p class="order-id-data">{{order.order.id}}</p>
                  <p class="order-time">下单时间</p>
                  <p class="order-time-data">{{order.order.createTime}}</p>
                </div>
              </el-col>
              <el-col :span="16" class="order-detail">
                <p>预订房间类型：{{order.room.type.typeName}}</p>
                <p>预订房间号：{{order.room.number}}</p>
                <p>入住时间：{{order.order.inTime}}</p>
                <p>离店时间：{{order.order.leaveTime}}</p>
                <p>入住人数：{{order.order.realPeople}}</p>
                <p>提供发票：
                  <span v-if="order.order.fapiao == 1">是</span>
                  <span v-else>否</span>
                </p>
                <p>预计费用：{{order.order.realPrice}}</p>
                <p>是否受理：
                  <span v-if="order.order.flag == 1">是</span>
                  <span v-else>否</span>
                </p>
              </el-col>
            </el-row>
          </el-card>
        </el-col>

        <el-col :span="24" class="text-center mb-1">
          <span class="end-message">--- 没有了 ---</span>
        </el-col>
      </el-row>

    </el-main>
    <el-footer class="footer">
      <footbar></footbar>
    </el-footer>
  </el-container>
</template>

<script>
  import footbar from "@/components/footbar.vue";
  export default {
    data() {
      return {
        orderdata: {},
        length: '',
      };
    },
    components: {
      footbar,
    },
    methods: {
      back() {
        this.$router.push("/mine");
      },
    },
    mounted() {
      this.axios.get("http://localhost:9151/user/historyOrder")
        .then(res => {
          this.orderdata = res.data.data;
          this.length = this.orderdata.length;
        })
        .catch(res => {
          console.log("wa!");
        });
    }
  };
</script>

<style scoped>
/* 背景和全局字体 */
.wrapper {
  background: linear-gradient(120deg, #89f7fe, #66a6ff);
  min-height: 100vh;
  font-family: 'Arial', sans-serif;
}

/* 标题卡片样式 */
.box-card {
  margin: 1rem;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.8);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}
.box-card:hover {
  transform: translateY(-5px);
}

/* 订单卡片样式 */
.order-card {
  background: linear-gradient(145deg, #e6f7ff, #ffffff);
  border: 1px solid #dcdfe6;
}

/* 订单头部图标和文字 */
.order-head {
  text-align: center;
  background: linear-gradient(90deg, #6dd5fa, #2980b9);
  border-radius: 10px;
  color: white;
  padding: 10px;
}
.order-icon {
  font-size: 48px;
  color: white;
  margin-bottom: 10px;
}
.order-id, .order-time {
  font-weight: bold;
}
.order-id-data, .order-time-data {
  font-size: 14px;
  margin-top: 5px;
  color: #d1ecf1;
}

/* 订单详情样式 */
.order-detail p {
  margin: 8px 0;
  color: #555;
  font-size: 16px;
}

/* 返回按钮样式 */
.order-back {
  color: #007bff;
  font-weight: bold;
  transition: color 0.3s;
}
.order-back:hover {
  color: #0056b3;
}

/* 底部信息 */
.end-message {
  color: #666;
  font-size: 14px;
  margin-top: 20px;
  font-style: italic;
}
</style>
