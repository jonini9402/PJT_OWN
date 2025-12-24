<template>
  <div class="sidebar-left">
    <!-- 로고 -->
    <div class="logo">
      <router-link to="/">
        <img src="@/assets/logo/gray.png" alt="OWN LOGO" class="logo-img" />
      </router-link>
      <!--<h1>OWN</h1> -->
    </div>

    <!-- 미니 프로필 컴포넌트 -->
    <div class="profile-wrapper">
      <MiniProfile />
    </div>

    <!-- 메뉴 -->
    <nav class="menu">
      <router-link to="/" class="menu-item">
        <span class="menu-label">홈</span>
        <img src="@/assets/icons/Home.svg" class="menu-icon" alt="Home" />
      </router-link>

      <router-link to="/mypage" class="menu-item">
        <span class="menu-label">마이 로그</span>
        <img src="@/assets/icons/MyLog.svg" class="menu-icon" alt="MyLog" />
      </router-link>
    </nav>

    <!-- 로그아웃 버튼 -->
    <button class="logout-btn" @click="handleLogout">
      <span class="menu-label">로그아웃</span>
      <img src="@/assets/icons/Logout.svg" class="menu-icon" alt="Logout" />
    </button>

  </div>
</template>

<script>
import MiniProfile from '@/components/layout/MiniProfile.vue';
import { useAuthStore } from '@/stores/auth';

export default {
  name: 'SidebarLeft',

  components: {
    MiniProfile
  },

  methods: {
    async handleLogout() {
      try {
        const response = await fetch('http://localhost:8080/api/user/logout', {
          method: 'POST',
          credentials: 'include'
        });

        if (response.ok) {
          // authStore에서 사용자 정보 삭제
          const authStore = useAuthStore();
          authStore.clearUser();
          
          console.log('로그아웃 성공');
          alert('로그아웃되었습니다');
          
          //로그인 페이지로 이동
          this.$router.push('/login');
        } else {
          console.error('로그아웃 실패:', response.status);
          alert('로그아웃에 실패했습니다.');
        }
      } catch (error) {
        console.error('로그아웃 에러:', error);
        alert('로그아웃 중 오류가 발생했습니다.');
      }
    }
  }
}
</script>

<style scoped>
.sidebar-left {
  width: 220px;
  height: 100vh;
  background-color: #212121;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  position: sticky;
  top: 0;
}

/* 로고 */
.logo {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 40px; /* 미니 프로필과의 간격 */
  padding: 0 10px;
}

.logo-img {
  width: 80%;       /* 사이드바 너비에 맞춰 꽉 채우기 */
  max-width: 100px;  /* 너무 크면 최대 수치 제한 */
  height: auto;      /* 비율 유지 */
  object-fit: contain;
  transition: transform 0.2s;
}

.logo-img:hover {
  transform: scale(1.05); /* 로고 호버 시 살짝 커지는 효과 */
}



/* 메뉴 */
.menu {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.menu-item, .logout-btn {
  position: relative;
  padding: 14px 20px;
  color: #999;
  text-decoration: none;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: none;
  background-color: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  overflow: hidden;
}

.menu-item::before, .logout-btn::before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 5px;
  height: 100%;
  
  /* 마지막에 첫 번째 색상이 다시 와야 자연스럽게 이어집니다. */
  background: linear-gradient(
    to bottom, 
    #2E3781 0%, 
    #E1603F 33%, 
    #F3DBC8 66%, 
    #2E3781 100%
  );
  
  /* 배경 크기를 높게 잡습니다. */
  background-size: 100% 400%; 
  background-position: 0% 0%;
  
  opacity: 0; 
  transition: opacity 0.3s ease;
  z-index: 1;
}

/* 활성화된 버튼 바 표시 */
.menu-item.router-link-active::before {
  opacity: 1;
}

/* 호버 시 바 표시 및 애니메이션 */
.menu-item:hover::before, .logout-btn:hover::before {
  opacity: 1;
  animation: flowLine 5s linear infinite; /* 속도를 조금 늦추면 더 부드럽습니다 */
}

/* [핵심 수정] 애니메이션 위치값 조정 */
@keyframes flowLine {
  0% {
    /* 시작점 */
    background-position: 0% 0%;
  }
  100% {
    /* 배경 사이즈와 색상 배치에 맞춰 50% 지점으로 이동 (한 세트 이동) */
    /* 혹은 0% 100%가 어색하면 0% 50%로 시도해 보세요. */
    background-position: 0% 135%; 
  }
}

/* 5. 아이콘 및 프로필 호버 (기존 요청 유지) */
.menu-icon {
  width: 20px;
  height: 20px;
  transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.menu-item:hover .menu-icon, .logout-btn:hover .menu-icon {
  transform: scale(1.2);
}

.menu-item:hover, .logout-btn:hover,
.menu-item.router-link-active {
  color: #ffffff; /* 글자색을 하얗게 */
}

.menu-item:hover .menu-label, 
.logout-btn:hover .menu-label,
.menu-item.router-link-active .menu-label {
  font-weight: 700; /* 글씨 진하게 */
  transform: scale(1.05); /* 글씨 크기 1.05배 확대 */
}

.menu-label {
  font-size: 15px;
  transition: all 0.3s ease; /* 호버 시 변화를 부드럽게 연결 */
  display: inline-block; /* transform: scale 적용을 위해 필요 */
  transform-origin: left; /* 왼쪽 기준으로 커지게 설정 */
}

.logout-btn {
  width: 100%;
  margin-top: auto;
  font-family: inherit;
  text-align: left;
}

.logout-btn .menu-label {
  font-size: 15px;
}

</style>