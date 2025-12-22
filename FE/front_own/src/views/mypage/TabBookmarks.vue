<template>
  <div class="post-list">
    <div v-if="!loading && posts.length === 0" class="empty-message">
      북마크한 게시물이 없습니다.
    </div>

    <PostItem v-for="post in posts" :key="post.postId" :post="post" />

    <div ref="loadMoreTrigger" class="loading-trigger">
      <p v-if="loading">북마크를 불러오는 중...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import PostItem from '@/components/feed/PostItem.vue'; 
import { getBookmarkedPosts } from '@/api/post';

const posts = ref([]); 
const loading = ref(false);
const loadMoreTrigger = ref(null);

const lastPostId = ref(null); 
const size = 5; 

const fetchPosts = async () => {
  if (loading.value) return;
  loading.value = true;
  
  try {
    const params = {
      size: size,
      lastPostId: lastPostId.value
    };

    const response = await getBookmarkedPosts(params);
    const newPosts = response.data;

    if(newPosts.length > 0) {
      posts.value.push(...newPosts);
      lastPostId.value = newPosts[newPosts.length - 1].postId;
    }
  } catch (error) {
    console.error("북마크 로드 실패", error);
  } finally{
    loading.value = false;
  }
};

onMounted(() => {
  fetchPosts();

  const observer = new IntersectionObserver((entries) => {
    if (entries[0].isIntersecting && !loading.value) {
      fetchPosts();
    }
  }, { threshold: 1.0 });

  if (loadMoreTrigger.value) {
    observer.observe(loadMoreTrigger.value);
  }
});
</script>

<style scoped>
.post-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  /* 탭 내부 여백이 필요하면 추가 */
  padding-top: 10px; 
}

.loading-trigger { 
  height: 80px; 
  display: flex; 
  justify-content: center; 
  align-items: center; 
  color: #888;
}

.empty-message {
  text-align: center;
  padding: 40px 0;
  color: #888;
}
</style>