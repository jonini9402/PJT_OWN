import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'MainFeed',
    component: () => import('@/views/feed/MainFeedPage.vue'),
    meta: { requiresAuth: true, layout: 'DefaultLayout' } // 로그인을 해야만 접근가능하도록 설정 & 사이드바가 있는 defaultLayout
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/LoginPage.vue'),
    meta: { requiresAuth: false, layout: 'AuthLayout'} 
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('@/views/auth/SignupPage.vue'),
    meta: { requiresAuth: false, layout: 'AuthLayout' }
  },
  {
  path: '/mypage',
  name: 'MyPage',
  component: () => import('@/views/mypage/MyPage.vue'),
  meta: { requiresAuth: true, layout: 'DefaultLayout' } // 로그인을 해야만 접근가능하도록 설정 & 사이드바가 있는 defaultLayout
},
{
  path: '/mypage/edit',
  name: 'ProfileEdit',
  component: () => import('@/views/mypage/ProfileEdit.vue'),
  meta: { requiresAuth: true, layout: 'DefaultLayout' } // 로그인을 해야만 접근가능하도록 설정 & 사이드바가 있는 defaultLayout
},
{
    path: '/post/create',
    name: 'PostCreate',
    component: () => import('@/views/postcreate/PostCreatePage.vue'),
    meta: { requiresAuth: false, layout: 'AuthLayout' }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

// 네비게이션 가드 - 페이지 이동 전마다 실행한다
import { useAuthStore } from '@/stores/auth';

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  //로그인이 필요한 페이지인데 로그인이 안 되어 있다면 로그인 페이지로
  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    next('/login');
  }
  //이미 로그인했는데 로그인/회원가입 페이지로 가려고 하면 메인으로
  else if ((to.name === 'Login' || to.name === 'Signup') && authStore.isLoggedIn) {
    next('/');
  } else {
    next(); // 그 외에는 허용
  }
});


export default router
