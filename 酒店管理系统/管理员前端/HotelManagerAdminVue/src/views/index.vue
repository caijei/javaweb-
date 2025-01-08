<template>
  <el-row>
    <el-col :span="24" class="text-center">
      <p class="title1">
        欢迎使用酒店管理系统
      </p>
    </el-col>
    <el-col :span="24" class="text-center" id="homeCol">
      <img :src="require('@/assets/home.jpg')" class="home-img" />
    </el-col>
  </el-row>
</template>

<script>
</script>

<style scoped>
.title1 {
  font-size: 50px;
  font-weight: 700;
  background: linear-gradient(45deg, #ff6f00, #ff3d00, #9c27b0, #2196f3, #4caf50);
  background-clip: text;
  color: transparent;
  text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.5);
  animation: fadeIn 2s ease-in-out, gradientMove 4s linear infinite;
}

#homeCol {
  background-size: cover;
  color: white;
  padding: 50px 0;
  position: relative;
  overflow: hidden;
  animation: slideIn 2s ease-in-out;
}

.home-img {
  width: 90%;
  max-height: 500px;
  border-radius: 15px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  transition: transform 0.5s ease;
}

.home-img:hover {
  transform: scale(1.1);
}

@keyframes fadeIn {
  0% {
    opacity: 0;
    transform: translateY(-30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  0% {
    transform: translateY(100px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes gradientMove {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
</style>
