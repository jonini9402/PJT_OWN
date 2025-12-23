<template>
    <div class="post-detail-modal" v-if="post" @click.self="closeModal">
        <div class="modal-content">

            <!-- 닫기 버튼 -->
            <button @click="closeModal" class="close-btn">X</button>

            <!-- 음악 섹션 -->
            <div class="music-section" :style="{ backgroundColor: bgColor, backgroundImage: gradientBg}">
                <div class="album-img-container">
                    <img :src="post.albumImg" alt="Album Art" class="album-img" crossorigin="anonymous" @load="extractColor" />
                </div>
                
                <MusicPlayer class="music-player" :previewUrl="post.previewUrl" />

                <div class="music-info">
                    <div class="music-title">
                        <h3>{{ post.musicTitle }}</h3>
                        <p class="artist">{{ post.artist }}</p>
                    </div>
                </div>

                <button @mouseover="hoverSpotify = true" @mouseleave="hoverSpotify = false" class="spotify-btn">
                    <span v-if="!hoverSpotify">Spotify</span>
                    <span v-else>Spotify에서 듣기</span>
                </button>
            </div>

            <!-- 포스트 섹션 -->
            <div class="post-section">
                <p class="post-date">{{ formatPostDate(post.createdAt) }}의 OWN 기록</p>
                
                <hr class="divider"/>

                <p class="caption">{{ post.caption }}</p>

                <hr class="divider"/>

                <div class="tags-section">
                    <p class="tag-info">{{ workoutName }}, 이런 무드였어요</p>
                    <div class="emotion-tags">
                        <span v-for="tag in post.emotionTags" :key="tag" class="emotion-tag">{{ tag }}</span>
                    </div>
                </div>
                
                <div class="like-bookmark-count">
                    <span>❤️: {{ post.likeCount }}</span>
                    <span>🔖: {{ post.bookmarkCount }}</span>
                </div>

            </div>

        </div>

    </div>
</template>

<script setup>

import { ref, computed } from 'vue';
import MusicPlayer from './MusicPlayer.vue';
// import ColorThief from '@/assets/utils/color-thief.umd';
import ColorThief from 'colorthief';
import { useCreateStore } from '@/stores/create';

const props = defineProps({
    post: Object,
})

const emit = defineEmits(['close']);
const createStore = useCreateStore();

const workoutName = computed(() => {
    if(!createStore.workoutTags || !props.post?.workoutTag) {
        return "운동";
    }

    const found = createStore.workoutTags.find(t => t.workoutTypeId === props.post.workoutTag);

    return found?.workoutName || "운동";
})


const closeModal = () => {
    emit('close');
}

const hoverSpotify = ref(false);
const bgColor = ref('#000000'); //배경색 기본 검정으로 설정
const gradientBg = ref('linear-gradient(to bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.65))')

// //ColorThief 활용해서 색상 추출
const extractColor = () => {
    const imgElement = document.querySelector('.album-img');
    const colorThief = new ColorThief();

    if(imgElement && imgElement.complete) {
        const color = colorThief.getColor(imgElement);
        bgColor.value = `rgb(${color[0]}, ${color[1]}, ${color[2]})`;
    }
}


// 'YYYY년 MM월 DD일 형식으로 변환'
const formatPostDate = (date) => {
    const postDate = new Date(date);
    return `${postDate.getFullYear()}년 ${postDate.getMonth() + 1}월 ${postDate.getDate()}일`;
};


</script>

<style scoped>

.post-detail-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  display: flex;
  width: 80%;
  height: 80%;
  background-color: white;
  border-radius: 12px;
  overflow: hidden;
  position: relative;
  flex-direction: row;
}

/* 닫기 버튼 */
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #333;
  cursor: pointer;
  z-index: 10;
}

/* 음악 섹션 */
.music-section {
  width: 50%;
  padding: 20px;
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #ff9f9f; /* 기본 배경 색상 */

  position: relative;
}

.album-img-container {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.album-img {
  width: 80%;
  height: auto;
  border-radius: 8px;
}

.music-info {
  margin-top: 20px;
  text-align: center;
  position: absolute;
  bottom: 20px;
  width: 100%;
  text-align: center;
}

.music-title h3 {
  font-size: 1.5rem;
  margin: 10px 0;
}

.artist {
  font-size: 1rem;
}

.spotify-btn {
  background: #1db954;
  color: white;
  border: none;
  border-radius: 30px;
  padding: 12px 24px;
  cursor: pointer;
  transition: transform 0.3s ease, background-color 0.3s ease;
  margin-top: 20px;
}

.spotify-btn:hover {
  transform: scale(1.1);  /* 크기 확대 */
  background-color: #1a9132;  /* 색상 변화 */
}

.preview-btn {
  background-color: #333;
  color: white;
  padding: 10px;
  border-radius: 8px;
  margin-top: 20px;
  cursor: pointer;
}

/* 포스트 섹션 */
.post-section {
  background-color: #181818;
  width: 50%;
  padding: 20px;
  overflow-y: auto;
}

.post-date {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.divider {
  border: none;
  border-top: 1px solid #ddd;
  margin: 20px 0;
}

.caption {
  font-size: 1rem;
  color: #333;
}

.tags-section {
  margin-top: 20px;
}

.tag-info {
  font-weight: bold;
}

.emotion-tags {
  margin-top: 10px;
}

.emotion-tag {
  background: #e0e0e0;
  padding: 6px 12px;
  border-radius: 20px;
  margin-right: 8px;
  font-size: 0.9rem;
}

.like-bookmark-count {
  margin-top: 20px;
  font-size: 1rem;
}

.like-bookmark-count span {
  display: block;
}

</style>