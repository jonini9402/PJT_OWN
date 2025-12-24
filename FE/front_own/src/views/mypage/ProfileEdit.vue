<template>
  <div class="profile-edit-page">
    <div class="edit-container">
      <div class="form-section">
        <h1>프로필 수정</h1>

        <div class="edit-form">
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
              <div class="edit-icon-overlay">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32L19.513 8.2z" />
                </svg>
              </div>
            </div>
          </div>
          <p v-if="imageError" class="field-error center-error">{{ imageError }}</p>

          <div class="input-group">
            <div class="input-wrapper">
              <label>현재 비밀번호</label>
              <input
                v-model="oldPassword"
                type="password"
                placeholder="비밀번호를 변경하려면 현재 비밀번호를 입력해주세요"
                @blur="validateOldPassword"
                :class="{ 'error-border': oldPasswordError }"
              />
              <p v-if="oldPasswordError" class="field-error">{{ oldPasswordError }}</p>
            </div>

            <div class="input-wrapper">
              <label>새 비밀번호</label>
              <input
                v-model="newPassword"
                type="password"
                placeholder="영문과 숫자를 조합해 8~13자로 입력해주세요"
                @blur="validatePassword"
                :class="{ 'error-border': passwordError }"
              />
              <p v-if="passwordError" class="field-error">{{ passwordError }}</p>
            </div>

            <div class="input-wrapper">
              <label>새 비밀번호 확인</label>
              <input
                v-model="newPasswordConfirm"
                type="password"
                placeholder="새 비밀번호 확인"
                @blur="validatePasswordConfirm"
                :class="{ 'error-border': passwordConfirmError }"
              />
              <p v-if="passwordConfirmError" class="field-error">{{ passwordConfirmError }}</p>
            </div>
          </div>

          <button class="submit-btn" @click="handleUpdate">프로필 업데이트</button>

          <p v-if="errorMessage" class="error-global">
            {{ errorMessage }}
          </p>

          <p class="back-link">
            <router-link to="/mypage">마이페이지로 돌아가기</router-link>
          </p>

          <div class="divider"></div>

          <div class="delete-section">
            <button class="delete-btn" @click="handleDeleteUser">
              회원 탈퇴
            </button>
          </div>
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
// API 함수 import 
import { deleteUser } from '@/api/user.js';
import ProfileImageModal from '@/components/ProfileImageModal.vue';
import { useAuthStore } from '@/stores/auth'; // 유저 스토어 import

import avatar1 from '@/assets/images/avatar1.png';
import avatar2 from '@/assets/images/avatar2.png';
import avatar3 from '@/assets/images/avatar3.png';
import avatar4 from '@/assets/images/avatar4.png';
import avatar5 from '@/assets/images/avatar5.png';
import avatar6 from '@/assets/images/avatar6.png';

export default {
  name: 'ProfileEdit',
  components: { ProfileImageModal },
  data() {
    return {
      profileImg: '',
      oldPassword: '',
      newPassword: '',
      newPasswordConfirm: '',
      
      profileImages: [avatar1, avatar2, avatar3, avatar4, avatar5, avatar6],
      isModalOpen: false,

      imageError: '',
      oldPasswordError: '',
      passwordError: '',
      passwordConfirmError: '',
      errorMessage: '',
      
      userId: null,
      originalProfileImg: '' 
    }
  },

  methods: {
    openModal() { this.isModalOpen = true; },
    closeModal() { this.isModalOpen = false; },
    // 모달에서 이미지 선택 시
    handleImageSelect(selectedImage) {
      this.profileImg = selectedImage;
      this.imageError = "";
    },
    selectProfile(imgUrl) {
      this.profileImg = imgUrl;
      this.imageError = '';
    },
    //유효성 검사 로직
    validatePassword() {
      // 새 비밀번호가 비어있으면 검사 패스 (비밀번호 안 바꿀 거니까)
      if (!this.newPassword) {
        this.passwordError = '';
        return true;
      }
      const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$/;
      if (!passwordRegex.test(this.newPassword)) {
        this.passwordError = '영문, 숫자 조합 8~13자';
        return false;
      }
      this.passwordError = '';
      return true;
    },

    validatePasswordConfirm() {
      if (!this.newPassword) return true; // 새 비번 없으면 패스

      if (this.newPassword !== this.newPasswordConfirm) {
        this.passwordConfirmError = '비밀번호가 일치하지 않습니다';
        return false;
      }
      this.passwordConfirmError = '';
      return true;
    },

    async handleUpdate() {
      this.errorMessage = '';
      this.oldPasswordError = '';
      this.passwordError = '';
      this.passwordConfirmError = '';

      // 1. 변경사항 체크
      const isProfileImgChanged = this.profileImg !== this.originalProfileImg;
      // "새 비밀번호" 칸에 입력이 있어야만 비밀번호 변경 시도로 간주
      const isPasswordChanging = this.newPassword && this.newPassword.length > 0;

      if (!isProfileImgChanged && !isPasswordChanging) {
        this.errorMessage = '변경할 내용이 없습니다';
        return;
      }

      // 2. 비밀번호 변경 시에만 유효성 검사 수행
      if (isPasswordChanging) {
        // 기존 비밀번호 입력 확인
        if (!this.oldPassword) {
          this.oldPasswordError = '현재 비밀번호를 입력해주세요.';
          return;
        }
        // 새 비밀번호 유효성 확인
        if (!this.validatePassword()) return;
        if (!this.validatePasswordConfirm()) return;
      }

      const authStore = useAuthStore();

      try {
        // 3. 서버로 보낼 데이터 구성
        const updateData = {
          // 이미지는 항상 현재 상태(변경된 값)를 보냄
          profileImg: this.profileImg, 
          
          // 비밀번호 변경이 아니면 null을 보내서 서버가 "비밀번호 유지"로 인식하게 함
          oldPassword: isPasswordChanging ? this.oldPassword : null,
          newPassword: isPasswordChanging ? this.newPassword : null,
          newPasswordConfirm: isPasswordChanging ? this.newPasswordConfirm : null 
        };

        const success = await authStore.saveProfileToServer(updateData);

        if (success) {
          alert('프로필이 수정되었습니다');
          this.$router.push('/mypage');
        } else {
          this.errorMessage = '수정에 실패했습니다. 입력 정보를 확인해주세요.';
        }

      } catch (error) {
        console.error('프로필 수정 에러:', error);
        this.errorMessage = '서버 오류가 발생했습니다.';
      }
    },

    //회원 탈퇴
    async handleDeleteUser() {
      if (!confirm("정말로 탈퇴하시겠습니까?\n삭제된 계정은 복구할 수 없습니다.")) {
        return;
      }
      try {
        await deleteUser(this.userId);
        const authStore = useAuthStore();
        authStore.logout(); 
        alert("회원 탈퇴가 완료되었습니다.");
        this.$router.push('/login');
      } catch (error) {
        console.error("회원 탈퇴 실패:", error);
        this.errorMessage = "회원 탈퇴 처리 중 오류가 발생했습니다.";
      }
    },

    async fetchCurrentProfile() {
      const authStore = useAuthStore();
      if (!authStore.user) {
         await authStore.fetchUser();
      }
      if (authStore.user) {
        this.userId = authStore.userId;
        this.profileImg = authStore.profileImg; // 스토어 이미지 가져오기
        this.originalProfileImg = authStore.profileImg; // 비교용 원본 저장
      }
    }
  },

  mounted() {
    this.fetchCurrentProfile();
  }
}
</script>

<style scoped>
.profile-edit-page {
  width: 100%;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient( #1f1f1f 100%);
  padding: 40px 0;
}

.edit-container {
  width: 420px;
  padding: 40px;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  text-align: center;
}

.form-section h1 {
  margin-bottom: 30px;
  font-size: 24px;
  font-weight: 700;
  color: #333;
  letter-spacing: 1px;
}

.profile-image-section {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
  cursor: pointer;
}

.profile-image-wrapper {
  width: 100px;
  height: 100px;
  position: relative;
  border-radius: 50%;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.selected-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid rgba(255, 255, 255, 0.8);
}

.default-icon {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgba(255, 255, 255, 0.8);
}
.default-icon svg { width: 60%; height: 60%; }

.edit-icon-overlay {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 32px;
  height: 32px;
  background-color: #222;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid rgba(255, 255, 255, 0.9);
  color: white;
}
.edit-icon-overlay svg { width: 18px; height: 18px; }

.input-group {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 30px;
  text-align: left;
}
.input-wrapper { display: flex; flex-direction: column; }
.input-wrapper label {
  font-size: 11px;
  color: #eee;
  margin-bottom: 4px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-shadow: 0 1px 2px rgba(0,0,0,0.1);
}

.edit-form input {
  width: 100%;
  padding: 8px 0;
  box-sizing: border-box;
  background: transparent;
  border: none;
  border-bottom: 1px solid rgba(255, 255, 255, 0.6);
  color: white;
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s;
  border-radius: 0;
}

.edit-form input::placeholder {
  color: rgba(255, 255, 255, 0.6);
  font-size: 13px;
}
.edit-form input:focus { border-bottom-color: white; }
.edit-form input.error-border { border-bottom-color: #ff6b6b; }

.field-error {
  color: #ff6b6b;
  font-size: 11px;
  margin-top: 4px;
  font-weight: 600;
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

.submit-btn {
  width: 100%;
  padding: 14px;
  background-color: #222;
  color: white;
  border: none;
  border-radius: 30px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: background-color 0.2s;
  margin-bottom: 15px;
  letter-spacing: 1px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
.submit-btn:hover { background-color: #000; transform: translateY(-1px); }

.back-link {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
  margin-top: 10px;
}
.back-link a {
  color: white;
  text-decoration: none;
  font-weight: 700;
}
.back-link a:hover { text-decoration: underline; }

/* --- 회원 탈퇴버튼 스타일 추가--- */

.divider {
  width: 100%;
  height: 1px;
  background-color: rgba(255, 255, 255, 0.2);
  margin: 30px 0 20px 0;
}

.delete-section {
  text-align: center;
}

.delete-btn {
  background: transparent;
  color: rgba(224, 62, 62, 0.6);
  border:  none;   
  padding:  4px 0;       
  font-size:  11px;
  border-radius: 20%;
  cursor: pointer;
  transition: color 0.15s ease;
  font-weight: 500;   
}

.delete-btn:hover {
  color: rgba(224, 62, 62, 0.9);
  text-decoration: underline;
}
</style>