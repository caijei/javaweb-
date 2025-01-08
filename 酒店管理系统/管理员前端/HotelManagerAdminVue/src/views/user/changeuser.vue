<template>
  <el-row>
    <el-col :span="16" :offset="4">
      <div class="change-form">
        <div class="text item">
          <p class="change-title">输入修改用户 ID</p>
          <el-form :model="change" status-icon ref="change" label-width="80px">
            <el-form-item label="ID" prop="id">
              <el-input type="text" v-model="change.id"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" @click="queryBtn" class="query-btn mb-1">
            查询
          </el-button>
          <p class="change-title">输入修改信息</p>
          <el-form :model="change" status-icon ref="change" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input type="text" v-model="change.userName"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="username">
              <el-input type="datetime" v-model="change.createTime"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="text" v-model="change.password" disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="积分" prop="jifenb">
              <el-input type="text" v-model="change.jifen"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
              <el-input type="text" v-model="change.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input type="email" v-model="change.email"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-select v-model="change.sex" placeholder="请选择">
                <el-option label="男(1)" value="1"></el-option>
                <el-option label="女(0)" value="0"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="账号状态" prop="state">
              <el-input type="text" v-model="change.state"></el-input>
            </el-form-item>
          </el-form>
          <el-button :type="btnType" @click="changeBtn" class="change-btn" :disabled="disabled">
            确认修改
          </el-button>
        </div>
      </div>
    </el-col>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
      <span>信息修改成功。</span>
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
        id: '',
        createTime: '',
        userName: '',
        password: '',
        email: '',
        phone: '',
        sex: '',
        jifen: '',
        state: '',
      },
      genderRaw: '',
      iconstyle: 'iconfont icon-r-yes',
      disabled: false,
      btnType: 'success',
      isRealchange: false,
      dialogVisible: false,
    };
  },
  methods: {
    changeBtn() {
      this.axios
        .post('http://localhost:9151/admin/updateUser', this.change)
        .then((res) => {
          console.log(res);
          this.dialogVisible = true;
        })
        .catch((res) => {
          console.log('error: ' + res);
        });
    },
    queryBtn() {
      this.axios
        .post('http://localhost:9151/admin/getUserById?userId=' + this.change.id)
        .then((res) => {
          console.log(res.data.data);
          this.change.createTime = res.data.data.createTime;
          this.change.userName = res.data.data.userName;
          this.change.password = res.data.data.password;
          this.change.email = res.data.data.email;
          this.change.phone = res.data.data.phone;
          this.change.jifen = res.data.data.jifen;
          this.change.state = res.data.data.state;
          this.change.sex = res.data.data.sex;
        })
        .catch((res) => {
          console.log('err:' + res);
        });
    },
  },
};
</script>

<style scoped>
body {
  background: linear-gradient(135deg, #f6d365, #fda085);
  font-family: 'Roboto', sans-serif;
  color: #333;
}

.change-form {
  background: #fff;
  border-radius: 16px;
  padding: 3rem 4rem; /* 内部增加间距 */
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  margin: 3rem auto; /* 外部增加间距 */
  max-width: 800px; /* 增加盒子的宽度 */
}

.change-title {
  font-size: 1.8rem; /* 标题更大 */
  font-weight: bold;
  text-align: center;
  color: #4a4a4a;
  margin-bottom: 2rem; /* 增加标题的下间距 */
}

.el-input,
.el-select {
  border-radius: 8px;
}

.query-btn,
.change-btn {
  width: 100%;
  font-size: 1.2rem; /* 按钮更大 */
  font-weight: bold;
  border-radius: 8px;
  background: linear-gradient(90deg, #1e90ff, #00bfff);
  color: #fff;
  margin-bottom: 1.5rem; /* 增加按钮之间的间距 */
  padding: 0.8rem 0; /* 增加按钮的内部间距 */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.query-btn:hover,
.change-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.el-form-item {
  margin-bottom: 1.8rem; /* 增加表单项间距 */
}

.el-dialog .dialog-footer {
  text-align: center;
}
</style>
