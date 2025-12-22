<template>
  <div class="mypage-container">
    <!-- 왼쪽 사이드바 -->
    <SidebarLeft />

    <!-- 메인 콘텐츠 -->
    <div class="mypage-content">
      <!-- 프로필 헤더 -->
      <ProfileHeader @tab-changed="handleTabChange" />

      <!-- 탭별 콘텐츠 -->
      <div class="tab-content">
        <!-- 나의 운동일지 탭 -->
        <div v-if="currentTab === 'journal'">
          <p style="color: white;">나의 운동일지 내용이 여기 들어갑니다</p>
        </div>

        <!-- 좋아요 게시물 탭 -->
        <div v-if="currentTab === 'likes'">
          <p style="color: white;">좋아요한 게시물이 여기 들어갑니다</p>
        </div>

        <!-- 북마크 탭 -->
        <div v-if="currentTab === 'bookmarks'">
          <p style="color: white;">북마크한 게시물이 여기 들어갑니다</p>
        </div>
      </div>
    </div>

    <!-- 오른쪽 사이드바 -->
    <SidebarRight /> 
  </div>
</template>

<script>
import ProfileHeader from '@/components/mypage/ProfileHeader.vue';
import SidebarLeft from '@/components/layout/SidebarLeft.vue';
import SidebarRight from '@/components/layout/SidebarRight.vue';

export default {
  name: 'MyPage',
  
  components: {
    ProfileHeader,
    SidebarLeft,
    SidebarRight
  },

  data() {
    return {
      currentTab: 'journal'
    }
  },

  methods: {
    handleTabChange(tabId) {
      this.currentTab = tabId;
      console.log('현재 탭:', tabId);
    }
  }
}
</script>

<style scoped>
.mypage-container {
  display: flex;
  min-height: 100vh;
  background-color: #0a0a0a;
}

.mypage-content {
  flex: 1;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.tab-content {
  margin-top: 20px;
}
</style>