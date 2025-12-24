<template>
  <div class="feed-page">
    <header class="feed-header">
    </header>
    
    <RankingSection></RankingSection>

    <h3 class="List-title">최신 포스트</h3>
    <PostList />
  </div>
</template>

<script setup>
import PostList from '@/components/feed/PostList.vue';
import RankingSection from '@/components/feed/RankingSection.vue';
</script>

<style scoped>
.feed-page { max-width: 620px; margin: 0 auto; }
.feed-header { margin-bottom: 24px; }
.List-title {
  padding: 10px 20px;
  margin-bottom: 4px;
}

</style>