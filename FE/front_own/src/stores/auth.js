import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  // 상태 (데이터)
  state: () => ({
    user: null, // 유저 정보 객체
    isLoggedIn: false
  }),

  getters: {
    // 유저 ID
    userId: (state) => state.user?.userId ?? null,
    
    // 이메일
    email: (state) => state.user?.email ?? '',
    
    // 이름
    name: (state) => state.user?.name ?? '',
    
    // 닉네임
    nickname: (state) => state.user?.nickname ?? '',
    
    // 프로필 이미지
    profileImg: (state) => state.user?.profileImg ?? '',
    
    // 등급 레벨 (Newbie = 1 기준으로 통일)
    tierLevel: (state) => state.user?.tierLevel ?? 1,
    
    // 게시물 수
    postCount: (state) => state.user?.postCount ?? 0,
    
    // 등급 레이블
    tierLabel: (state) => {
      const level = state.user?.tierLevel ?? 1;
      if (level === 3) return 'Pro';
      if (level === 2) return 'Amateur';
      return 'Newbie';
    },
    
    // 등급 CSS 클래스
    tierClass: (state) => {
      const level = state.user?.tierLevel ?? 1;
      if (level === 3) return 'tier-pro';
      if (level === 2) return 'tier-amateur';
      return 'tier-newbie';
    }
  },

  actions: {
    /**
     * 로그인 - 유저 정보 저장
     */
    setUser(userData) {
      this.user = {
        userId: userData.userId ?? userData.id,
        email: userData.email,
        name: userData.name,
        nickname: userData.nickname,
        profileImg: userData.profileImg,
        tierLevel: userData.tierLevel ?? 1,
        postCount: userData.postCount ?? 0
      };

      this.isLoggedIn = true;

      // ✅ store 기준 데이터로 저장
      localStorage.setItem('own_user', JSON.stringify(this.user));
    },

    /**
     * 서버에서 유저 정보 가져오기 (세션 기반)
     */
    async fetchUser() {
      try {
        const response = await fetch('http://localhost:8080/api/user/me', {
          credentials: 'include'
        });

        if (response.ok) {
          const userData = await response.json();
          this.setUser(userData);
          return true;
        } else {
          this.clearUser();
          return false;
        }
      } catch (error) {
        console.error('유저 정보 조회 실패:', error);
        this.clearUser();
        return false;
      }
    },

    //서버에 회원정보 수정 요청 보내기
    async saveProfileToServer(updateData) {
      if (!this.userId) return false;

      try {
        // 1. 서버로 PUT요청 전송
        const response = await fetch(`http://localhost:8080/api/user/${this.userId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include', // 세션 유지를 위해 필수
          body: JSON.stringify(updateData),
        });

        if (response.ok) {
          // 2. 서버 저장이 성공하면 내 로컬 스토어 정보도 갱신
          this.updateUser(updateData); 
          return true;
        } else {
          console.error('서버 저장 실패');
          return false;
        }
      } catch (error) {
        console.error('회원 정보 수정 중 에러:', error);
        return false;
      }
    },

    /**
     * 로컬 유저 정보 삭제
     */
    clearUser() {
      this.user = null;
      this.isLoggedIn = false;
      localStorage.removeItem('own_user');
    },

    /**
     * 로그아웃
     */
    async logout() {
      try {
        await fetch('http://localhost:8080/api/user/logout', { 
          method: 'POST',
          credentials: 'include'
        });
      } catch (e) {
        console.error('로그아웃 요청 실패:', e);
      } finally {
        this.clearUser();
      }
    },

    /**
     * 회원 탈퇴
     */
    async withdraw() {
      if (!this.userId) return false;

      try {
        const response = await fetch(
          `http://localhost:8080/api/user/${this.userId}`,
          {
            method: 'DELETE',
            credentials: 'include'
          }
        );

        if (response.ok) {
          this.clearUser();
          return true;
        } else {
          console.error('회원 탈퇴 실패');
          return false;
        }
      } catch (error) {
        console.error('회원 탈퇴 요청 중 에러:', error);
        return false;
      }
    },

    /**
     * 유저 정보 일부 업데이트
     */
    updateUser(updates) {
      if (!this.user) return;

      this.user = { ...this.user, ...updates };
      localStorage.setItem('own_user', JSON.stringify(this.user));
    },

    /**
     * localStorage에서 유저 정보 복원 (🔥 핵심 수정)
     */
    restoreUser() {
      try {
        const savedUser = localStorage.getItem('own_user');
        if (savedUser) {
          const userData = JSON.parse(savedUser);
          this.setUser(userData); // ❗ 함수 호출로 복원
        }
      } catch (error) {
        console.error('유저 정보 복원 실패:', error);
        this.clearUser();
      }
    },

    /**
     * 게시글 작성 후 등급 갱신용
     */
    async refreshUserTier() {
      if (!this.isLoggedIn) return;

      try {
        const response = await fetch('http://localhost:8080/api/user/me', {
          credentials: 'include'
        });

        if (response.ok) {
          const updatedUser = await response.json();
          this.updateUser({
            tierLevel: updatedUser.tierLevel,
            postCount: updatedUser.postCount
          });
        }
      } catch (e) {
        console.error('등급 갱신 실패:', e);
      }
    }
  }
});
