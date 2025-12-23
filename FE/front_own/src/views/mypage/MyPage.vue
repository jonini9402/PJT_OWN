<template>
  <div class="mypage-content">
    <ProfileHeader @tab-changed="handleTabChange" />

    <div class="tab-content">
      <div v-if="userId">
        
        <div v-if="currentTab === 'journal'">
          <TabMyPost :userId="userId" />
        </div>

        <div v-if="currentTab === 'likes'">
          <TabLikes :userId="userId" />
        </div>

        <div v-if="currentTab === 'bookmarks'">
          <TabBookmarks :userId="userId" />
        </div>
    </div>
  </div>
</div>
</template>

<script>
import ProfileHeader from '@/components/mypage/ProfileHeader.vue';
import TabLikes from './TabLikes.vue';
import TabBookmarks from './TabBookmarks.vue';
import TabMyPost from './TabMyPost.vue'; 
import api from '@/api';

export default {
  name: 'MyPage',
  
  components: {
    ProfileHeader,
    TabLikes,
    TabBookmarks,
    TabMyPost
  },

  data() {
    return {
      currentTab: 'journal', // 기본 탭
      userId: null,
    }
  },

  methods: {
    handleTabChange(tabId) {
      this.currentTab = tabId;
      console.log('현재 탭:', tabId);
    },

    async fetchMyInfo() {
      try {
        // 백엔드 엔드포인트에 맞춰 수정 (/api/user/me 등)
        const response = await api.get('/user/me'); 
        
        // 응답 구조에 따라 id 경로 수정 (response.data.userId 또는 response.data.id)
        this.userId = response.data.userId || response.data.id;
        console.log("내 ID 로드 완료:", this.userId);
        
      } catch (error) {
        console.error('내 정보 로드 실패:', error);
      }
    }
  },

  mounted() {
    this.fetchMyInfo();
  }
}
</script>

<style scoped>
.mypage-content {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.tab-content {
  margin-top: 20px;
}
</style>