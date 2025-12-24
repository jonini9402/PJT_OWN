<template>
    <div class="step-container">
        <h2 class="step-title">운동하면서 어떤 감정이 들었나요?</h2>

        <div class="divider"></div>

        <div v-if="createStore.allEmotionTags.length === 0" class="loading">
            감정 태그 불러오는 중...
        </div>

        <div v-else class="scroll-area">
            <div v-for="(group, title) in groupedEmotions" :key="title" class="emotion-group">
                <h3 class="group-title">{{ title }}</h3>
                <div class="tag-wrap">
                    <button
                        v-for="tag in group"
                        :key="tag.emotionTypeId"
                        :class="['tag-btn', {active: isSelected(tag.emotionTypeId)}]"
                        @click="handleEmotion(tag)"
                        >
                        {{ tag.emotionName }}
                    </button>
                </div>
            </div>
        </div>

        <div class="divider"></div>

        <div class="selected-summary">
            <div v-if="createStore.selectedEmotionTags.length === 0" class="placeholder">
                태그를 선택해주세요.
            </div>
            <div v-else class="selected-tags">
                <span v-for="emotion in createStore.selectedEmotionTags" :key="emotion.emotionTypeId" class="badge">
                    {{ emotion.emotionName }}
                </span>
            </div>
        </div>

        <div class="nav-area">
            <button @click="prev" class="prev-btn">이전</button>
            <button
                @click="next"
                :disabled="createStore.selectedEmotionTags.length === 0"
                class="next-btn"
            >
                다음
            </button>
        </div>
    </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useCreateStore } from '@/stores/create';

const createStore = useCreateStore();

onMounted(() => {
    createStore.fetchEmotionTags();
})

const groupedEmotions = computed(() => {
    const all = createStore.allEmotionTags;
    if(all.length === 0) return {};

    return {
        "차분해요": all.slice(0, 8),
        "에너지 넘쳐요": all.slice(8, 18),
        "좋아요": all.slice(18, 27),
        "아쉬워요": all.slice(27, 35)
    };
});

const isSelected = (emotionTypeId) => {
    return createStore.selectedEmotionTags.some(e => e.emotionTypeId === emotionTypeId);
};

const handleEmotion = (tag) => {
    createStore.toggleEmotion(tag);
};

const prev = () => {
    createStore.setStep(1); // 1단계로 돌아가기
};

const next = () => {
    if (createStore.selectedEmotionTags.length > 0) {
        console.log("Step2 완료 - 선택된 태그:", createStore.selectedEmotionTags);
        createStore.setStep(3);
    }
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
    margin: 15px 0;
}

.loading {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 1.1rem;
}

.scroll-area {
    flex: 1;
    width: 100%;
    overflow-y: auto;
    padding: 0 20px;
    margin: 10px 0;
}

/* 크롬, 사파리용 스크롤바 */
.scroll-area::-webkit-scrollbar {
    width: 6px; /* 스크롤바 너비 */
}

.scroll-area::-webkit-scrollbar-track {
    background: transparent; /* 트랙 배경 투명 */
}

.scroll-area::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.3); /* 작은 바: 반투명 화이트 */
    border-radius: 10px;
}

.scroll-area::-webkit-scrollbar-thumb:hover {
    background: rgba(255, 255, 255, 0.5); /* 호버 시 약간 더 선명하게 */
}

.emotion-group {
    margin-bottom: 30px;
    text-align: left;
    width: 100%;
}

.group-title {
    font-size: 1.1rem;
    color: #333;
    margin-bottom: 15px;
    font-weight: 600;
    padding-left: 5px;
}

.tag-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.tag-btn {
    padding: 10px 18px;
    border: 1.5px solid rgba(0, 0, 0, 0);
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.15);
    color: #333;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s ease;
}

.tag-btn:hover {
    background: rgba(255, 255, 255, 0.35);
}

.tag-btn.active {
    background: #333;
    color: #fff;
    border-color: #333;
}

.selected-summary {
    width: 100%;
    min-height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 10px;
}

.selected-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
}

.badge {
    background: rgba(0, 0, 0, 0.7);
    color: white;
    padding: 5px 12px;
    border-radius: 20px;
    font-size: 0.8rem;
    font-weight: 400;
}

.placeholder {
    color: rgba(0, 0, 0, 0.4);
    font-size: 0.9rem;
}

.nav-area {
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 20px; /* 버튼 사이 간격 */
    padding-top: 10px;
}

.prev-btn, .next-btn {
    width: 140px;
    padding: 15px;
    border-radius: 30px;
    font-size: 1.1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s;
}

.prev-btn {
    background: rgba(255, 255, 255, 0.2);
    color: #333;
    border: 1px solid rgba(0, 0, 0, 0.1);
}

.prev-btn:hover {
    background: rgba(255, 255, 255, 0.4);
}

.next-btn {
    background: #333;
    color: white;
    border: none;
}

.next-btn:disabled {
    background: rgba(0, 0, 0, 0.2);
    cursor: not-allowed;
}

.next-btn:active:not(:disabled), .prev-btn:active {
    transform: scale(0.95);
}

.loading {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
}
</style>