<template>
  <div class="signup-page">
    <div class="signup-container">
      <div class="form-section">
        <h1>회원가입</h1>

        <div class="signup-form">
          <div class="profile-image-section" @click="openModal">
            <div class="profile-image-wrapper">
              <img 
                v-if="profileImg" 
                :src="profileImg" 
                alt="선택된 프로필 이미지" 
                class="selected-img"
              />
              <div v-else class="default-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6">
                  <path fill-rule="evenodd" d="M7.5 6a4.5 4.5 0 119 0 4.5 4.5 0 01-9 0zM3.751 20.105a8.25 8.25 0 0116.498 0 .75.75 0 01-.437.695A18.683 18.683 0 0112 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 01-.437-.695z" clip-rule="evenodd" />
                </svg>
              </div>
              <div class="add-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path fill-rule="evenodd" d="M12 3.75a.75.75 0 01.75.75v6.75h6.75a.75.75 0 010 1.5h-6.75v6.75a.75.75 0 01-1.5 0v-6.75H4.5a.75.75 0 010-1.5h6.75V4.5a.75.75 0 01.75-.75z" clip-rule="evenodd" />
                </svg>
              </div>
            </div>
          </div>
          <p v-if="imageError" class="field-error center-error">{{ imageError }}</p>

          <div class="input-group">
            <div class="input-wrapper">
              <label>EMAIL</label>
              <input
                v-model="email"
                type="email"
                @blur="validateEmail"
                :class="{ 'error-border': emailError }"
              />
              <p v-if="emailError" class="field-error">{{ emailError }}</p>
            </div>

            <div class="input-wrapper">
              <label>PASSWORD</label>
              <input
                v-model="password"
                type="password"
                @blur="validatePassword"
                :class="{ 'error-border': passwordError }"
              />
              <p v-if="passwordError" class="field-error">{{ passwordError }}</p>
            </div>

            <div class="input-wrapper">
              <label>PASSWORD CONFIRM</label>
              <input
                v-model="passwordConfirm"
                type="password"
                @blur="validatePasswordConfirm"
                :class="{ 'error-border': passwordConfirmError }"
              />
              <p v-if="passwordConfirmError" class="field-error">{{ passwordConfirmError }}</p>
            </div>

            <div class="input-wrapper">
              <label>NAME</label>
              <input
                v-model="name"
                type="text"
                @blur="validateName"
                :class="{ 'error-border': nameError }"
              />
              <p v-if="nameError" class="field-error">{{ nameError }}</p>
            </div>

            <div class="input-wrapper">
              <label>NICKNAME</label>
              <input
                v-model="nickname"
                type="text"
                @blur="validateNickname"
                :class="{ 'error-border': nicknameError }"
              />
              <p v-if="nicknameError" class="field-error">{{ nicknameError }}</p>
            </div>
          </div>

          <button class="submit-btn" @click="handleSignup">SIGN IN</button>

          <p v-if="errorMessage" class="error-global">
            {{ errorMessage }}
          </p>

          <p class="login-link">
            Already have an account?
            <router-link to="/login">Login</router-link>
          </p>
        </div>
      </div>
    </div>

    <ProfileImageModal 
      :isOpen="isModalOpen"
      :images="profileImages"
      :currentImage="profileImg"
      @select="handleImageSelect"
      @close="closeModal"
    />
  </div>
</template>

<script>
import ProfileImageModal from '@/components/ProfileImageModal.vue';
import {
  signup,
  checkEmailDuplicate,
  checkNicknameDuplicate,
} from "@/api/user.js";
import avatar1 from '@/assets/images/avatar1.png';
import avatar2 from '@/assets/images/avatar2.png';
import avatar3 from '@/assets/images/avatar3.png';
import avatar4 from '@/assets/images/avatar4.png';
import avatar5 from '@/assets/images/avatar5.png';
import avatar6 from '@/assets/images/avatar6.png';

export default {
  name: "SignupPage",
  components:{
    ProfileImageModal
  },
  data() {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      name: "",
      nickname: "",
      profileImg: "",

      profileImages: [
        avatar1, avatar2, avatar3, avatar4, avatar5, avatar6
      ],

      isModalOpen: false,

      emailError: "",
      passwordError: "",
      passwordConfirmError: "",
      nameError: "",
      nicknameError: "",
      imageError: "",
      errorMessage: "",
    };
  },
  methods: {
    openModal() { this.isModalOpen = true; },
    closeModal() { this.isModalOpen = false; },
    handleImageSelect(selectedImage) {
      this.profileImg = selectedImage;
      this.imageError = "";
    },
    selectProfile(imgUrl) {
      this.profileImg = imgUrl;
      this.imageError = "";
    },
    async validateEmail() {
      if (!this.email) { this.emailError = ""; return false; }
      if (!this.email.includes("@")) { this.emailError = "이메일 형식이 올바르지 않습니다"; return false; }
      try {
        const res = await checkEmailDuplicate(this.email);
        if (res.data === true) { this.emailError = "이미 사용 중인 이메일입니다"; return false; }
      } catch (e) { this.emailError = "이메일 확인 중 오류가 발생했습니다"; return false; }
      this.emailError = ""; return true;
    },
    validatePassword() {
      const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$/;
      if (!pattern.test(this.password)) { this.passwordError = "영문, 숫자 조합 8~13자"; return false; }
      this.passwordError = ""; return true;
    },
    validatePasswordConfirm() {
      if (this.password !== this.passwordConfirm) { this.passwordConfirmError = "비밀번호가 일치하지 않습니다"; return false; }
      this.passwordConfirmError = ""; return true;
    },
    validateName() {
      if (!this.name) { this.nameError = ""; return false; }
      const namePattern = /^[가-힣a-zA-Z0-9]{3,20}$/;
      if (!namePattern.test(this.name)) { this.nameError = "한글, 영어, 숫자로 3~20자"; return false; }
      if (/[^가-힣a-zA-Z0-9]/.test(this.name)) { this.nameError = "특수문자 금지"; return false; }
      this.nameError = ""; return true;
    },
    async validateNickname() {
      if (!this.nickname) { this.nicknameError = ""; return false; }
      try {
        const res = await checkNicknameDuplicate(this.nickname);
        if (res.data === true) { this.nicknameError = "이미 사용 중인 닉네임입니다"; return false; }
      } catch (e) { this.nicknameError = "오류 발생"; return false; }
      this.nicknameError = ""; return true;
    },
    async handleSignup() {
      if (!this.profileImg) { this.imageError = "프로필 이미지를 선택해주세요"; this.errorMessage = ""; return; }
      const isEmailValid = await this.validateEmail();
      const isPasswordValid = this.validatePassword();
      const isPasswordConfirmValid = this.validatePasswordConfirm();
      const isNameValid = this.validateName();
      const isNicknameValid = await this.validateNickname();

      if (!isEmailValid || !isPasswordValid || !isPasswordConfirmValid || !isNameValid || !isNicknameValid) {
        this.errorMessage = "입력 정보를 다시 확인해주세요";
        return;
      }
      const payload = {
        email: this.email,
        password: this.password,
        name: this.name,
        nickname: this.nickname,
        profileImg: this.profileImg,
      };
      await signup(payload);
      alert("회원가입이 완료되었습니다!");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
/* 전체 페이지 배경: 부드러운 그라데이션 */
.signup-page {
  width: 100%;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(#141414 100%); /* 일단 블랙으로 */
  padding: 40px 0;
}

/* 유리 질감 컨테이너 카드 */
.signup-container {
  width: 420px;
  padding: 40px;
  border-radius: 24px;
  /* Glassmorphism 효과 핵심 */
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  text-align: center;
}

/* 헤더 */
.form-section h1 {
  margin-bottom: 30px;
  font-size: 24px;
  font-weight: 700;
  color: #333; /* 진한 회색 텍스트 */
  letter-spacing: 1px;
}

/* 프로필 이미지 섹션 */
.profile-image-section {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
  cursor: pointer;
}

.profile-image-wrapper {
  width: 90px;
  height: 90px;
  position: relative;
}

/* 선택된 이미지 */
.selected-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid rgba(255, 255, 255, 0.5);
}

/* 기본 아이콘 스타일 */
.default-icon {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: rgba(0, 0, 0, 0.6); /* 진한 회색 배경 */
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgba(255, 255, 255, 0.8);
}
.default-icon svg {
  width: 60%;
  height: 60%;
}

/* 추가 아이콘 (작은 동그라미) */
.add-icon {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 28px;
  height: 28px;
  background-color: #ddd; /* 밝은 회색 */
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid rgba(255, 255, 255, 0.8);
  color: #666;
}
.add-icon svg {
  width: 16px;
  height: 16px;
  stroke-width: 2;
}


/* 입력 필드 그룹 */
.input-group {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 30px;
  text-align: left;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
}

.input-wrapper label {
  font-size: 11px;
  color: #eee; /* 밝은 흰색 라벨 */
  margin-bottom: 4px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

/* 입력 필드 스타일 (밑줄) */
.signup-form input {
  width: 100%;
  padding: 8px 0;
  box-sizing: border-box;
  background: transparent; /* 배경 투명 */
  border: none;
  border-bottom: 1px solid rgba(255, 255, 255, 0.6); /* 흰색 밑줄 */
  color: white;
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s;
  border-radius: 0;
}

.signup-form input:focus {
  border-bottom-color: white; /* 포커스 시 더 밝게 */
}

.signup-form input.error-border {
  border-bottom-color: #ff6b6b; /* 에러 시 붉은색 */
}

/* 에러 메시지 */
.field-error {
  color: #ff6b6b;
  font-size: 11px;
  margin-top: 4px;
}
.center-error {
  text-align: center;
  margin-top: -20px;
  margin-bottom: 20px;
}
.error-global {
  color: #ff6b6b;
  font-size: 13px;
  margin-bottom: 15px;
  font-weight: bold;
}

/* 제출 버튼 (검은색) */
.submit-btn {
  width: 100%;
  padding: 14px;
  background-color: #222; /* 진한 검은색 */
  color: white;
  border: none;
  border-radius: 30px; /* 둥근 모서리 */
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: background-color 0.2s;
  margin-bottom: 15px;
  letter-spacing: 1px;
}

.submit-btn:hover {
  background-color: #000;
}

/* 로그인 링크 */
.login-link {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
}

.login-link a {
  color: white;
  text-decoration: none;
  font-weight: 700;
  margin-left: 4px;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>