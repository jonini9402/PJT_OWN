<template>
    <div 
        :class="['music-card', { selected: isSelected }]" 
        @click="$emit('select', music)"
    >
        <img :src="music.albumImg" class="album-art" />
        <div class="info">
            <div class="title">{{ music.musicTitle }}</div>
            <div class="artist">{{ music.artist }}</div>
        </div>
        </div>
</template>

<script setup>
defineProps({
    music: Object,
    isSelected: Boolean
});
defineEmits(['select']);
</script>

<style scoped>
.music-card {
    display: flex;
    align-items: center;
    width: 100%;
    box-sizing: border-box;
    padding: 12px;
    margin-bottom: 10px;
    background: rgba(255, 255, 255, 0.1); /* 기본: 아주 투명한 흰색 */
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 15px;
    cursor: pointer;
    transition: all 0.2s ease;
    min-width: 0;
}

/* 마우스 올렸을 때: 조금 더 밝아짐 */
.music-card:hover {
    background: rgba(255, 255, 255, 0.25);
}

/* --- 선택되었을 때 스타일 변경 --- */
.music-card.selected {
    background: rgba(0, 0, 0, 0.08); /* 연한 회색 톤으로 변경 */
    border: 1px solid rgba(0, 0, 0, 0.1); /* 보더도 살짝 어둡게 */
    /* 글자색은 그대로 유지 (white로 바뀌지 않음) */
}

.album-art {
    width: 50px;
    height: 50px;
    flex-shrink: 0;
    border-radius: 8px;
    margin-right: 15px;
    object-fit: cover;
}

.info {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.title {
    font-size: 1rem;
    font-weight: 600;
    color: #333; /* 폰트 색상 고정 */
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-bottom: 2px;
}

.artist {
    font-size: 0.85rem;
    color: #666; /* 폰트 색상 고정 */
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>