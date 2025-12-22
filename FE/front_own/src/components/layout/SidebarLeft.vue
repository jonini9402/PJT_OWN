<template>
  <div class="sidebar-left">
    <!-- 로고 -->
    <div class="logo">
      <div class="logo-icon">로고</div>
      <h1>OWN</h1>
    </div>

    <!-- 미니 프로필 컴포넌트 -->
    <MiniProfile />

    <!-- 메뉴 -->
    <nav class="menu">
      <router-link to="/" class="menu-item">
        <span class="menu-label">피드</span>
      </router-link>

      <router-link to="/mypage" class="menu-item">
        <span class="menu-label">마이페이지</span>
      </router-link>
    </nav>

    <!-- 로그아웃 버튼 -->
    <button class="logout-btn" @click="handleLogout">
      로그아웃
    </button>
  </div>
</template>

<script>
import MiniProfile from '@/components/layout/MiniProfile.vue';

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
          alert('로그아웃되었습니다');
          this.$router.push('/login');
        }
      } catch (error) {
        console.error('로그아웃 실패:', error);
      }
    }
  }
}
</script>

<style scoped>
.sidebar-left {
  width: 240px;
  height: 100vh;
  background-color: #1a1a1a;
  border-right: 1px solid #2a2a2a;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  position: sticky;
  top: 0;
}

/* 로고 */
.logo {
  text-align: center;
  margin-bottom: 30px;
}

.logo-icon {
  font-size: 32px;
  margin-bottom: 8px;
}

.logo h1 {
  color: white;
  font-size: 24px;
  font-weight: bold;
  margin: 0;
  letter-spacing: 2px;
}

/* 메뉴 */
.menu {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.menu-item {
  padding: 12px 16px;
  color: #999;
  text-decoration: none;
  border-radius: 8px;
  transition: all 0.2s;
  text-align: left;
  border-left: 3px solid transparent;
}

.menu-item:hover {
  background-color: #2a2a2a;
  color: white;
}

.menu-item.router-link-active {
  background-color: #2a2a2a;
  color: white;
  border-left-color: #4169E1;
  font-weight: 600;
}

.menu-label {
  font-size: 15px;
}

/* 로그아웃 버튼 */
.logout-btn {
  width: 100%;
  padding: 12px;
  background-color: #2a2a2a;
  color: #999;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.2s;
  margin-top: auto;
}

.logout-btn:hover {
  background-color: #333;
  color: white;
}
</style>