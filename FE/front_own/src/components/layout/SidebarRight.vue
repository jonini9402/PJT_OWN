<template>
    <aside class="sidebar-right">

        <section class="stats-section">
            <div class="circular-progress">
                <svg viewBox="0 0 36 36" class="circular-chart">
                    <path class="circle-bg"
                         d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                    <path class="circle"
                        :stroke-dasharray="`${(workoutCount / 7) * 100}, 100`"
                        d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                     <text x="18" y="20.35" class="percentage">{{ workoutCount }}</text>
                </svg>
            </div>
            <p class="stats-label">이번 주 {{ workoutCount }}번 OWN완 했어요!</p>
        </section>

        <section class="action-section">
            <button class="write-log-btn" @click="goToCreatePage">
                <span class="btn-text">작성하기</span>
            </button>
        </section>

    </aside>
</template>

<script setup>

import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { getWeeklyCount } from '@/api/post';

const router = useRouter();
const route = useRoute();
const workoutCount = ref(0);

const fetchWeeklyCount = async () => {
    try {
        const response = await getWeeklyCount();
        workoutCount.value = response.data;
        console.log('이번주 운동 횟수:', workoutCount.value);
    } catch (error) {
        console.error("데이터 로드 실패", error);
    }
}

const goToCreatePage = () => {
    router.push('/post/create');
}

// 컴포넌트 마운트 시 데이터 로드
onMounted(fetchWeeklyCount);

// 라우트 변경 감지 - 포스트 작성 후 돌아왔을 때 업데이트
watch(() => route.path, (newPath) => {
    if (newPath === '/' || newPath === '/mypage') {
        fetchWeeklyCount();
    }
});

</script>

<style scoped>
.sidebar-right {
    width: 320px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 30px;
    
}

.circular-chart {
    display: block;
    margin: 10px auto;
    max-width: 150px;
}

.circle-bg {
    fill: none;
    stroke: #333;
    stroke-width: 3px;
}

.circle {
    fill:none;
    stroke: #1DB954;
    stroke-width: 3px;
    stroke-linecap: round;
    transition: stroke-dasharray 0.5s ease;
}

.percentage {
    fill: #fff;
    font-size: 0.5em;
    text-anchor: middle;
    font-weight: bold;
}

.stats-label {
    text-align: center;
    font-size: 14px;
    color: #ddd;
}

.write-log-btn {
  width: 100%;
  padding: 15px;
  background: linear-gradient(135deg, #1DB954 0%, #17a34a 100%);
  border: none;
  border-radius: 12px;
  color: white;
  font-weight: 700;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 4px 15px rgba(29, 185, 84, 0.2);
}

.write-log-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(29, 185, 84, 0.4);
  filter: brightness(1.1);
}

.write-log-btn:active {
  transform: scale(0.96);
  box-shadow: 0 2px 10px rgba(29, 185, 84, 0.3);
}

</style>