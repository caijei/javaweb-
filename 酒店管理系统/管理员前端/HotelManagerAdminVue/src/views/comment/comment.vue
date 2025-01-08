<template>
  <div class="comments-container">
    <el-row class="comment-row" v-for="(comm, index) in comment" :key="index">
      <el-col :span="12" :offset="6">
        <el-card class="comment-card" shadow="always">
          <div slot="header" class="card-header">
            <el-avatar :src="comm.user.avatar || 'https://via.placeholder.com/40'" class="user-avatar"></el-avatar>
            <div class="header-details">
              <span class="username">{{ comm.user.userName }}</span>
              <span class="user-id">(ID: {{ comm.user.id }})</span>
              <span class="contact-info">Phone: {{ comm.user.phone }} | Email: {{ comm.user.email }}</span>
            </div>
          </div>
          <div class="card-content">
            <p class="comment-info">留言内容：</p>
            <el-tag type="info" effect="dark">{{ comm.comment.information }}</el-tag>
            <p class="rating-info">评分等级：
              <el-rate v-model="comm.comment.type" disabled></el-rate>
            </p>
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
      comment: [],
    };
  },
  mounted() {
    this.axios
      .get("http://localhost:9151/admin/listComment")
      .then((res) => {
        this.comment = res.data.data;
        console.log(this.comment);
      })
      .catch((res) => {
        console.log("res: " + res);
      });
  },
};
</script>

<style scoped>
.comments-container {
  background: linear-gradient(to bottom, #1e3c72, #2a5298);
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
}

.comment-row {
  margin-bottom: 2rem;
  animation: fadeIn 0.8s ease-in-out;
}

.comment-card {
  border: 2px solid #4f8ef7;
  border-radius: 12px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.comment-card:hover {
  transform: translateY(-5px);
  box-shadow: 0px 8px 20px rgba(79, 142, 247, 0.5);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  font-size: 1.2rem;
  font-weight: bold;
  color: #fff;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.3);
}

.header-details {
  display: flex;
  flex-direction: column;
}

.username {
  color: #ffd700;
}

.user-id {
  color: black;
  font-size: 0.9rem;
}

.contact-info {
  font-size: 0.8rem;
  color: black;
}

.card-content {
  margin-top: 1rem;
  color: black;
}

.comment-info {
  font-size: 1rem;
  font-weight: 600;
}

.rating-info {
  margin-top: 0.5rem;
  font-size: 1rem;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
