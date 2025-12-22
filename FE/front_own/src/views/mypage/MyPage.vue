<template>
  <div class="mypage-content">
    <ProfileHeader @tab-changed="handleTabChange" />

    <div class="tab-content">
      <div v-if="currentTab === 'journal'">
        <TabMyPost />
      </div>

      <div v-if="currentTab === 'likes'">
        <TabLikes />
      </div>

      <div v-if="currentTab === 'bookmarks'">
        <TabBookmarks />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileHeader from '@/components/mypage/ProfileHeader.vue';
import TabLikes from './TabLikes.vue';
import TabBookmarks from './TabBookmarks.vue';
import TabMyPost from './TabMyPost.vue'; 

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
      currentTab: 'journal' // 기본 탭
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
.mypage-content {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.tab-content {
  margin-top: 20px;
}
</style>