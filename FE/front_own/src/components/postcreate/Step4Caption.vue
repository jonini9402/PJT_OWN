<template>
    <div class="step-container">
        <h2 class="step-title">오늘의 운동을 마음껏 표현해 주세요</h2>

        <div class="divider"></div>

        <div class="caption-area">
            <textarea
              v-model="createStore.caption"
              :placeholder="placeholderText"
              maxlength="400"
              class="glass-textarea"
            ></textarea>
            <div class="char-count">{{ createStore.caption.length }} / 400</div>
        </div>

        <div class="divider"></div>

        <div class="nav-area">
            <button @click="prev" class="prev-btn">이전</button>
            <button 
                @click="handleSubmit"
                :disabled="!createStore.caption.trim()"
                class="submit-btn"
            >
                완료
            </button>
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue';
import { useCreateStore } from '@/stores/create';
import { createPost } from '@/api/post';
import { saveMusicToDb } from '@/api/music';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const createStore = useCreateStore();
const authStore = useAuthStore();
const router = useRouter();

const placeholderText = computed(() => {
    const title = createStore.selectedMusic?.musicTitle || '음악';
    return `"${title}"의 리듬에 맞춰 움직인 오늘, 어떤 기분이 드셨나요?`;
});

const prev = () => {
    createStore.setStep(3);
};

const handleSubmit = async () => {
    try {
        const musicPayload = {
            spotifyTrackId: createStore.selectedMusic.spotifyTrackId,
            musicTitle: createStore.selectedMusic.musicTitle,
            artist: createStore.selectedMusic.artist,
            albumImg: createStore.selectedMusic.albumImg,
            duration: parseInt(createStore.selectedMusic.duration) || 0,
            previewUrl: createStore.selectedMusic.previewUrl || null,
        };

        const musicResponse = await saveMusicToDb(musicPayload);
        const savedMusicId = musicResponse.data.musicId;

        const postPayload = {
            userId: authStore.userId,
            workoutTag: createStore.workoutTag?.workoutTypeId,
            emotionTags: createStore.selectedEmotionTags.map(tag => tag.emotionTypeId),
            musicId: savedMusicId,
            caption: createStore.caption.trim(),
        };

        await createPost(postPayload);
        alert("운동일지가 등록되었습니다!");
        createStore.resetData();
        router.push('/');
    } catch (error) {
        console.error("저장 실패:", error);
    }
};
</script>

<style scoped>
.step-container {
    width: 100%; /* 부모 너비(800px)를 꽉 채우도록 설정 */
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box; /* 패딩이 너비를 늘리지 않게 방지 */
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

.caption-area {
    width: 100%;
    flex: 1;
    display: flex;
    flex-direction: column;
    position: relative;
    padding: 0 40px;
    box-sizing: border-box; /* 중요: 좌우 40px 패딩을 너비 계산에 포함 */
}

.glass-textarea {
    width: 100%;
    height: 100%;
    padding: 30px;
    background: rgba(255, 255, 255, 0.15);
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 25px;
    font-size: 1.1rem;
    line-height: 1.6;
    color: #333;
    resize: none;
    outline: none;
    transition: all 0.3s;
    box-sizing: border-box;
}

.glass-textarea:focus {
    background: rgba(255, 255, 255, 0.25);
    border-color: rgba(255, 255, 255, 0.5);
}

.char-count {
    position: absolute;
    bottom: 20px;
    right: 60px;
    font-size: 0.9rem;
    color: rgba(0, 0, 0, 0.4);
}

.nav-area {
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 20px;
    padding-top: 20px;
}

.prev-btn, .submit-btn {
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

.submit-btn {
    background: #333;
    color: white;
    border: none;
}

.submit-btn:disabled {
    background: rgba(0, 0, 0, 0.2);
    cursor: not-allowed;
}
</style>