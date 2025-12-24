<template>
    <div class="step-container">
        <h2 class = "step-title">오늘 한 운동은 무엇인가요?</h2>

        <div class="divider"></div>

        <!-- <div v-if="createStore.fetchWorkoutTags.length === 0">로딩 중</div> -->

        <div class="tag-grid">
            <button
              v-for="tag in createStore.workoutTags"
              :key="tag.workoutTypeId"
              :class="['tag-btn', { active: createStore.workoutTag?.workoutTypeId === tag.workoutTypeId }]"
              @click="selectTag(tag)">
              {{ tag.workoutName }}
            </button>
        </div>

        <div class="divider"></div>

        <div class="nav-area">
            <button @click="next" :disabled="!createStore.workoutTag" class="next-btn">
                다음
            </button>
        </div>

    </div>
</template>

<script setup>
import { useCreateStore } from '@/stores/create';
import { onMounted } from 'vue';

const createStore = useCreateStore();

onMounted(() => {
    createStore.fetchWorkoutTags();
});

const selectTag = (tag) => {
    createStore.workoutTag = {
        workoutTypeId: tag.workoutTypeId,
        workoutName: tag.workoutName
    }
};

const next = () => {
    createStore.setStep(2);
};

</script>

<style scoped>

.step-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;

}

.step-title {
    font-size: 1.6rem;
    font-weight: 700;
    margin-bottom: 40px;
}

.divider {
    width: 90%;
    height: 2px;
    background: linear-gradient(
        90deg, 
        rgba(255, 255, 255, 0) 0%, 
        rgba(255, 255, 255, 0.6) 50%, 
        rgba(255, 255, 255, 0) 100%
    );
    margin: 20px 0;
}

.tag-grid {
    flex: 1;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 15px;
    align-content: center;
    width: 100%;
    padding: 40px 0;
    margin: 20px 0;

}

.tag-btn {
    padding: 12px 20px;
    border: 1.5px solid rgba(0, 0, 0, 0);
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.1);
    color: #333;
    font-size: 1.3rem;
    font-weight: 600;
    cursor: pointer;
    align-items: center;
    transition: all 0.2s ease;
}

.tag-btn:hover {
    background: rgba(255, 255, 255, 0.3);
}

.tag-btn.active {
    background: #333; /* 선택 시 검은색 배경 */
    color: #fff;
    border-color: #333;
}

.nav-area {
    width: 100%;
    display: flex;
    justify-content: center;
    padding-top: 20px;
}

.next-btn {
    width: 160px;
    padding: 15px;
    background: #333;
    color: white;
    border: none;
    border-radius: 30px; /* 알약 형태 */
    font-size: 1.1rem;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s;
}

.next-btn:disabled {
    background: rgba(0, 0, 0, 0.2);
    cursor: not-allowed;
}

.next-btn:active {
    transform: scale(0.95);
}

</style>