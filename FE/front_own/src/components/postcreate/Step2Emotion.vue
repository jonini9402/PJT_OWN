<template>
    <div class="step-container">
        <h2 class="step-title">운동하면서 어떤 감정이 들었나요?</h2>
        <p class="subtitle">최대 5개까지 선택 가능해요</p>

        <!-- 선택된 태그를 제목 밑으로 이동 -->
        <div class="selected-summary">
            <div v-if="createStore.selectedEmotionTags.length === 0" class="placeholder">
                태그를 선택해주세요
            </div>
            <div v-else class="selected-tags">
                <span v-for="emotion in createStore.selectedEmotionTags" :key="emotion.emotionTypeId" class="badge">
                    {{ emotion.emotionName }}
                </span>
            </div>
        </div>

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
                        :disabled="!isSelected(tag.emotionTypeId) && createStore.selectedEmotionTags.length >= 5"
                        >
                        {{ tag.emotionName }}
                    </button>
                </div>
            </div>
        </div>

        <div class="divider"></div>

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
    if (!isSelected(tag.emotionTypeId) && createStore.selectedEmotionTags.length >= 5) {
        return;
    }
    createStore.toggleEmotion(tag);
};

const prev = () => {
    createStore.setStep(1);
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
    font-size: 1.3rem;
    font-weight: 700;
    margin-bottom: 8px;
}

.subtitle {
    font-size: 0.8rem;
    color: rgba(0, 0, 0, 0.5);
    margin-bottom: 15px; /* 25px → 15px (선택된 태그 공간 확보) */
    font-weight: 500;
}

.selected-summary {
    width: 100%;
    min-height: 35px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 10px; /* 5px → 10px */
}

.selected-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    justify-content: center;
}

.badge {
    background: #333;
    color: white;
    padding: 6px 12px;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 600;
}

.placeholder {
    color: rgba(0, 0, 0, 0.4);
    font-size: 0.85rem;
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

.loading {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    font-size: 1.1rem;
}

.scroll-area {
    /* flex: 1; */
    height: 320px;
    width: 100%;
    overflow-y: auto;
    padding: 0 20px;
    
}

.scroll-area::-webkit-scrollbar {
    width: 6px;
}

.scroll-area::-webkit-scrollbar-track {
    background: transparent;
}

.scroll-area::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.3);
    border-radius: 10px;
}

.scroll-area::-webkit-scrollbar-thumb:hover {
    background: rgba(255, 255, 255, 0.5);
}

.emotion-group {
    margin-bottom: 25px;
    text-align: left;
    width: 100%;
}

.group-title {
    font-size: 1rem;
    color: #333;
    margin-bottom: 12px;
    font-weight: 600;
    padding-left: 5px;
}

.tag-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.tag-btn {
    padding: 8px 14px;
    border: 1.5px solid rgba(0, 0, 0, 0);
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.15);
    color: #333;
    font-size: 0.8rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s ease;
}

.tag-btn:hover:not(:disabled) {
    background: rgba(255, 255, 255, 0.35);
}

.tag-btn.active {
    background: #333;
    color: #fff;
    border-color: #333;
}

.tag-btn:disabled {
    opacity: 0.4;
    cursor: not-allowed;
}

.nav-area {
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 12px;
    padding-top: 22px;
}

.prev-btn, .next-btn {
    min-width: 100px;
    padding: 10px 16px;
    border-radius: 30px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s;
}

.prev-btn {
    background: rgba(255, 255, 255, 0.2);
    color: #333;
    border: 1.5px solid rgba(0, 0, 0, 0.1);
}

.prev-btn:hover {
    background: rgba(255, 255, 255, 0.35);
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
</style>