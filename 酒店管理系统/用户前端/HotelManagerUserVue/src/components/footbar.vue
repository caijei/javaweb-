<template>
  <div class="pop-footer">
    <div>
      <el-row style="border-top: 1px grey solid;">
        <el-col :span="8">
          <router-link to="/findroom">
            <div :class="findCls" v-on:click="findBtn"><i class="el-icon-a-042"></i> 找房</div>
          </router-link>
        </el-col>
        <el-col :span="8" style="border-right: 1px grey solid;border-left: 1px grey solid;">
          <router-link to="/reservation-c">
            <div :class="reservationCls" v-on:click="reservationBtn"><i class="el-icon-a-011"></i> 订房</div>
          </router-link>
        </el-col>
        <el-col :span="8">
          <router-link to="/mine">
            <div :class="mineCls" v-on:click="mineBtn"><i class="el-icon-a-052"></i> 我的</div>
          </router-link>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import 'element-ui/lib/theme-chalk/index.css';
  import store from './../store';
  export default {
    data() {
      return {
        findCls: "",
        reservationCls: "",
        mineCls: "",
      }
    },
    methods: {
      findBtn() {
        this.$store.commit('setFind');
        this.changeColor();
      },
      reservationBtn() {
        this.$store.commit('setRes');
        this.changeColor();
      },
      mineBtn() {
        this.$store.commit('setMine');
        this.changeColor();
      },
      initColor() {
        this.findCls = "grid-content text-white";
        this.reservationCls = "grid-content text-white";
        this.mineCls = "grid-content text-white";
      },
      changeColor() {
        switch (this.$store.state.position) {
          case "findroom":
            this.findCls += " footer-active";
            break;
          case "resveration":
            this.reservationCls += " footer-active";
            break;
          case "mine":
            this.mineCls += " footer-active";
            break;
        }
      }
    },
    created() {
      this.initColor();
      this.changeColor();
    },
  }
</script>

<style scoped="scoped">
  .el-row {
    background-color: rgb(176, 196, 222);
    min-height: 6vh;
    line-height: 6vh;
    color: #fff;
    font-size: 1rem;
  }

  .footer-active {
    background: rgb(65, 105, 225);
  }

  .text-white {
    color: #fff;
    text-decoration: none;
  }

  a:hover {
    text-decoration: none;
    color: #fff;
  }
</style>
