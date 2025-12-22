<template>
  <div class="post-list">
    <PostItem v-for="post in posts" :key="post.id" :post="post" />

    <div ref="loadMoreTrigger" class="loading-trigger">
      <p v-if="loading">데이터를 불러오는 중...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import PostItem from './PostItem.vue';
import { getPosts } from '@/api/post';

const posts = ref([]); // 게시글 데이터 리스트
const loading = ref(false);
const loadMoreTrigger = ref(null);

const lastPostId = ref(null); // 마지막으로 가져온 포스트아이디
const size = 5; // 한페이지당 가져올 포스트 수

const fetchPosts = async () => {
  if (loading.value) return;
  loading.value = true;
  
  try {
    const params = {
      size: size,
      lastPostId: lastPostId.value
    };

    const response = await getPosts(params);
    const newPosts = response.data;

    if(newPosts.length > 0) {
      posts.value.push(...newPosts);
      lastPostId.value = newPosts[newPosts.length - 1].postId;
    }
  } catch (error) {
    console.error("로드 실패", error);
  } finally{
    loading.value = false;
  }
  
};

// Intersection Observer 설정 -> 무한 스크롤
onMounted(() => {
  const observer = new IntersectionObserver((entries) => {
    if (entries[0].isIntersecting) {
      fetchPosts();
    }
  }, { threshold: 1.0 });

  if (loadMoreTrigger.value) {
    observer.observe(loadMoreTrigger.value);
  }
});
</script>

<style scoped>
.loading-trigger { height: 80px; display: flex; justify-content: center; align-items: center; }
</style>