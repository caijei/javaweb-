<template>
  <div>
    <el-row>
      <el-col :span="16" :offset="4">
        <div class="search-form">
          <div class="text item">
            <p class="search-title">输入要查询的房间 ID</p>
            <el-form :model="search" status-icon ref="search" label-width="80px">
              <el-form-item label="ID" prop="id">
                <el-input type="text" v-model="search.id" class="input-box"></el-input>
              </el-form-item>
            </el-form>
            <el-button :type="btnType" @click="searchBtn" class="searchbtn" :disabled="disabled">
              查询
            </el-button>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" :offset="2" v-if="isRealsearch">
        <el-card class="box-card">
          <div slot="header" class="clearfix header-box">
            <span>房间详情</span>
          </div>
          <div class="text item">
            <div class="user-list">
              <el-row :gutter="20">
                <el-col :span="8">
                  <strong>房间 ID:</strong> {{ listData.room.id }}
                </el-col>
                <el-col :span="8">
                  <strong>最大人数:</strong> {{ listData.room.maxPeople }}
                </el-col>
                <el-col :span="8">
                  <strong>房间号:</strong> {{ listData.room.number }}
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="8">
                  <strong>特性:</strong> {{ listData.type.feature }}
                </el-col>
                <el-col :span="8">
                  <strong>房间简介:</strong> {{ listData.type.introduce }}
                </el-col>
                <el-col :span="8">
                  <strong>每日单价:</strong> ￥{{ listData.type.price }}
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="24">
                  <strong>房间类别:</strong> {{ listData.type.typeName }}
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="24">
                  <strong>房间参数:</strong>
                  <p>{{ listData.room.introduce }}</p>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        search: {
          id: '',
        },
        iconstyle: 'el-icon-a-042',
        disabled: false,
        btnType: 'primary',
        isRealsearch: false,
        listData: {},
      };
    },
    methods: {
      searchBtn() {
        this.axios
          .post("http://localhost:9151/admin/roomDetail?roomId=" + this.search.id)
          .then((res) => {
            this.listData = res.data.data;
            this.isRealsearch = true;
            console.log(res.data.data);
          })
          .catch((res) => {
            console.log("err: " + res);
          });
      },
    },
  };
</script>

<style scoped>
/* 整体页面背景 */
body {
  background: linear-gradient(to bottom, #1e90ff, #1e90ff);
  font-family: 'Arial', sans-serif;
  color: #ffffff;
}

/* 搜索框样式 */
.search-form {
  margin: 3rem auto;
  padding: 2rem;
  background: #0961687c;
  border-radius: 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
  text-align: center;
}

.search-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 2rem;
  color: #ecf0f1;
}

.input-box {
  border: 2px solid #1abc9c;
  border-radius: 10px;
  padding: 10px;
  font-size: 1rem;
  color: #2c3e50;
  width: 100%;
}

.searchbtn {
  background: linear-gradient(90deg, #1abc9c, #16a085);
  color: white;
  padding: 12px 30px;
  font-size: 1.4rem;
  font-weight: bold;
  border-radius: 15px;
  transition: all 0.3s ease;
  margin-top: 1rem;
}

.searchbtn:hover {
  background: linear-gradient(90deg, #16a085, #149174);
  transform: scale(1.1);
  box-shadow: 0 5px 15px rgba(22, 160, 133, 0.5);
}

/* 房间详情卡片样式 */
.box-card {
  background: #1e90ff;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(9, 28, 134, 0.5);
  padding: 2rem;
}

.header-box {
  background: linear-gradient(90deg, #3498db, #2980b9);
  padding: 15px;
  font-size: 1.5rem;
  color: #ffffff;
  text-align: center;
  font-weight: bold;
  border-radius: 15px;
}

.user-list {
  margin-top: 2rem;
  padding: 1.5rem;
  border-radius: 15px;
  background: rgba(255, 255, 255, 0.2);
}

.user-list strong {
  color: #f39c12;
  font-size: 1.2rem;
}

p {
  font-size: 1rem;
  margin-top: 0.5rem;
  color: #bdc3c7;
}
</style>
