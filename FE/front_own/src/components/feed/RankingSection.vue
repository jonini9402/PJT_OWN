<template>
  <section class="ranking-section">
    <h3 class="section-title">이번 주 OWN 팀메이트들은 이런 노래를 들었어요</h3>
    
    <swiper
      v-if="rankList.length > 0"
      :modules="modules"
      :slides-per-view="3" 
      :space-between="25"
      class="my-swiper"
    >
      <swiper-slide v-for="(rank, index) in rankList" :key="index">
        <CombOfWeek :rank="rank" />
      </swiper-slide>
    </swiper>

    <div v-else class="empty-rank">
      이번 주 인기 곡을 집계 중입니다
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getMusicRank } from '@/api/post';
import CombOfWeek from './CombOfWeek.vue';

// Swiper 필수 컴포넌트 및 모듈 임포트
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Pagination, Autoplay } from 'swiper/modules';

// Swiper 스타일 임포트
import 'swiper/css';
import 'swiper/css/pagination';

const rankList = ref([]);
const modules = [Pagination, Autoplay];

const fetchRank = async () => {
  try {
    const response = await getMusicRank();
    rankList.value = response.data;
  } catch (error) {
    console.error("랭킹 로드 실패:", error);
  }
};

onMounted(() => {
  fetchRank();
});
</script>

<style scoped>
.ranking-section { padding: 20px 0; box-sizing: border-box; width: 100%; overflow: hidden;}
.section-title { padding: 0 50px; margin-bottom: 16px; font-weight: bold; }

/* Swiper가 담긴 영역의 여백 설정 */
.my-swiper {
  padding: 40px 30px 20px 50px; /* 상단 패딩을 크게 주어 커질 때 공간 확보 */
}

.empty-rank { padding: 40px; text-align: center; color: #999; }
</style>