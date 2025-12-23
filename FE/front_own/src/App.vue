<template>
  <div class="app-container">
    <!--사이드바 있는 defaultLayout-->
    <template v-if="isDefaultLayout">
      <sidebar-left />

      <main class="main-content">
        <router-view />
      </main>

      <sidebarRight />
    </template>

    <!--사이드바 없는 auth-content-->
    <template v-else>
      <main class="auth-content">
        <router-view />
      </main>
    </template>

  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { useCreateStore } from '@/stores/create';
import SidebarLeft from './components/layout/SidebarLeft.vue';
import SidebarRight from './components/layout/SidebarRight.vue';

const route = useRoute();
const authStore = useAuthStore();
const createStore = useCreateStore();

const isDefaultLayout = computed(() => route.meta.layout === 'DefaultLayout');

onMounted(async () => {
  console.log('🚀 앱 초기화 시작...');
  
  // 1. 유저 정보 복원 (localStorage에서)
  authStore.restoreUser();
  
  // 2. 세션 확인 및 최신 유저 정보 가져오기
  if (authStore.isLoggedIn) {
    await authStore.fetchUser();
  }
  
  // 3. 운동/감정 태그 데이터 로드 (앱 시작 시 한 번만)
  await createStore.loadAllTags();
  
  console.log('앱 초기화 완료!');
});
</script>

<style scoped>
.app-container {
  display: flex;
  justify-content: center;
  width: 100%;
  min-height: 100vh;
  background-color: #212121; /* 다크모드 배경 */
  color: #fff;
}

.main-content {
  flex: 0 0 820px;
  width: 820px;
  border-left: 1px solid #2a2a2a;
  border-right: 1px solid #2a2a2a;
  height: 100vh;
  overflow-y: auto; /* 본문만 스크롤 가능하게 */
}

.main-content::-webkit-scrollbar {
  display: none;
}

.auth-content {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>