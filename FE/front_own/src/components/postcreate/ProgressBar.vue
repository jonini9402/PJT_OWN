<template>
    <div class="progress-wrapper">
        <div class="progress-bg">
            <div class="progress-fill" :style="{ width: `${(step / 4) * 100}%`}"></div>
        </div>
    </div>
</template>

<script setup>
defineProps({
    step: Number
});
</script>

<style scoped>
.progress-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
    margin-top: 40px;
}

.progress-bg {
    width: 100px; /* 전체 너비보다 작게 설정하여 포인트로 사용 */
    height: 4px;
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 2px;
    overflow: hidden;
}

.progress-fill {
    height: 100%;
    background-color: #333; /* 검은색 포인트 */
    transition: width 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>