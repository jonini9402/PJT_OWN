<template>
  <div class="login-page">

    <!-- 배경화면-->
    <div class="bg-animation"></div>

    <div class="login-container">
    <!-- 왼쪽 이미지 영역 -->
        <div class="image-section"> 
            <!--목업 이미지 넣어야됨-->
            <!-- <img src="@/assets/mockup-image.jpg" alt="OWN Mockup" /> -->
          </div>
        
        <!-- 오른쪽: 로그인 폼 -->
          <div class="form-section">
            <h1>OWN</h1>
          <p class="welcome-text">Welcome to OWN</p>

        <!-- 입력 폼-->
        <div class="login-form">
          <input v-model="email" type="email" placeholder="이메일" />

          <input v-model="password" type="password" placeholder="비밀번호" />
          <button @click="handleLogin">로그인</button>

          <!-- 에러 메시지-->
          <p v-if="errorMessage" class="error">
            {{ errorMessage }}
          </p>
        <!-- 회원가입 링크 -->
            <p class="register-link">
              계정이 없으신가요?
              <router-link to="/signup">가입하기</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { useAuthStore } from '@/stores/auth.js';
import { login } from "@/api/user.js";

export default {
  name: "LoginPage",

  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },

  data() {
    return {
      email: "",
      password: "", 
      errorMessage: "",
    };
  },
  methods: {
    async handleLogin() {
       if (!this.email) {
        this.errorMessage = "이메일을 입력해주세요";
        return;
      }
       if (!this.password) {
        this.errorMessage = "비밀번호를 입력해주세요";
        return;
      }
        // 에러 메시지 초기화
      this.errorMessage = "";
      
      try {
        // 백엔드에 로그인 요청
        const response = await login(this.email, this.password);

        // 성공 (200)
        this.authStore.setUser(response.data);

        sessionStorage.setItem('isLogin', 'true');

         alert('로그인 성공!');
        // 메인 페이지로 이동
        this.$router.push("/"); //메인 피드 경로 이게 맞나

      } catch (error) {
        // 실패 처리

        if (error.response) {
          // 백엔드에서 응답은 왔는데 에러인 경우
          if (error.response.status === 401) {
            // 인증 실패
            this.errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다";
          } else if (error.response.status === 400) {
            // 잘못된 요청
            this.errorMessage = "입력 정보를 확인해주세요";
          } else {
            this.errorMessage = "로그인에 실패했습니다";
          }
        } else {
          // 네트워크 에러 (서버가 꺼져있거나)
          this.errorMessage = "서버와 연결할 수 없습니다";
        }
      }
    },
  },
};
</script>
<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  margin: 0;
  padding: 0;
}

.bg-animation {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0; /* 배경이므로 가장 뒤로 */
  overflow: hidden;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.2),
    rgba(0, 0, 0, 0.1)
  );
}

.bg-animation::before,
.bg-animation::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 200%;
  height: 200%;
  background: conic-gradient(
    from 0deg,
    #E1603F,
    #2E3781,
    #E1603F,
    #F3DBC8,
    #2E3781
  );
  transform: translate(-50%, -50%);
  animation: rotate 8s linear infinite;
  filter: blur(50px);
  opacity: 0.8;
}

.bg-animation::after {
  width: 180%;
  height: 180%;
  animation: rotate-reverse 10s linear infinite;
  opacity: 0.6;
}

@keyframes rotate {
  0% { transform: translate(-50%, -50%) rotate(0deg); }
  100% { transform: translate(-50%, -50%) rotate(360deg); }
}

@keyframes rotate-reverse {
  0% { transform: translate(-50%, -50%) rotate(0deg); }
  100% { transform: translate(-50%, -50%) rotate(-360deg); }
}

.login-container {
  z-index: 1;
  display: flex;
  width: 80%;
  max-width: 1200px;
  height: 600px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}


/* 왼쪽: 이미지 영역 */
.image-section {
  flex: 1;
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
.image-section img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
/* 오른쪽: 로그인 폼 */
.form-section {
  flex: 1;
  padding: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.login-form input {
  width: 100%;
  padding: 12px;
  margin: 10px 0;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.login-form button {
  width: 100%;
  padding: 12px;
  margin: 20px 0 10px 0;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.error {
  color: red;
}
.register-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #666;
}

.register-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>
