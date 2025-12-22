<template>
    <div class="step-container">
        <h2>운동하면서 어떤 감정이 들었나요?</h2>
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

        <div class="selected-summary">
            <div v-if="createStore.selectedEmotionTags.length === 0" class="placeholder">
                태그를 선택해주세요.
            </div>
            <div v-else class="selected-tags">
                <span v-for="emotion in createStore.selectedEmotionTags" :key="emotion.emotionTypeId" class="badge">
                    #{{ emotion.emotionName }}
                </span>
            </div>
        </div>

        <div class="nav-btn">
            <button
                @click="next"
                :disabled="createStore.selectedEmotionTags.length === 0"
                class="next-btn"
                > 다음 </button>
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

const next = () => {
    if (createStore.selectedEmotionTags.length > 0) {
        console.log("Step2 완료 - 선택된 태그:", createStore.selectedEmotionTags);
        createStore.setStep(3);
    }
};

</script>

<style scoped>
.step-container {
    max-width: 700px;
    margin: 0 auto;
    padding: 20px;
}

h2 {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
}

.loading {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 1.1rem;
}

.scroll-area {
    height: 400px;
    overflow-y: auto;
    padding-right: 10px;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.emotion-group {
    margin-bottom: 25px;
}

.group-title { 
    font-size: 1.1rem; 
    margin: 20px 0 10px; 
    color: #333;
    font-weight: 600;
}

.tag-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.tag-btn {
    padding: 8px 14px;
    border: 1px solid #ddd;
    border-radius: 20px;
    background: white;
    font-size: 0.9rem;
    cursor: pointer;
    transition: all 0.2s;
}

.tag-btn:hover {
    border-color: #42b883;
    transform: translateY(-1px);
}

.tag-btn.active {
    background: #42b883;
    color: white;
    border-color: #42b883;
}

.selected-summary {
    min-height: 60px;
    background: #f9f9f9;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 20px;
}

.placeholder {
    color: #999;
    text-align: center;
    padding: 10px;
}

.selected-tags { 
    display: flex; 
    flex-wrap: wrap; 
    gap: 8px; 
}

.badge {
    background: #333;
    color: white;
    padding: 6px 12px;
    border-radius: 15px;
    font-size: 0.85rem;
}

.nav-btn { 
    display: flex; 
    gap: 10px; 
    margin-top: 20px; 
}

.next-btn { 
    flex: 1; 
    padding: 15px; 
    border-radius: 8px; 
    cursor: pointer; 
    border: none;
    background: #333;
    color: white;
    font-size: 1rem;
    font-weight: bold;
    transition: background 0.3s;
}

.next-btn:hover:not(:disabled) {
    background: #42b883;
}

.next-btn:disabled { 
    background: #ccc; 
    cursor: not-allowed; 
}
</style>