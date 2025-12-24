<template>
  <div class="music-card-feed" :style="{ backgroundColor: bgColor, backgroundImage: gradientBg }">
    <div class="card-top">
      <img :src="music.albumImg" alt="Album Art" class="album-img"
        crossorigin="anonymous" @load="extractColor" @error="handleImageError" />
      <a href="https://www.spotify.com" target="_blank" class="spotify-link">
        <img src="@/assets/spotifyLogo/spotify_logo_white.png" alt="SpotifyLogo" class="spotify-logo"/>
      </a>
    </div>
    
    <div class="card-bottom">
      <div class="music-info">
        <h4 class="title">{{ music.musicTitle }}</h4>
        <p class="artist">{{ music.artist }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import ColorThief from 'colorthief';
import MusicPlayer from './MusicPlayer.vue';

defineProps({
  music: Object // { title, artist, albumImg, previewUrl } 구성
});

const bgColor = ref('#1e1e1e'); // 기본 배경색
const gradientBg = ref('linear-gradient(to bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.4))');

// ColorThief 활용해서 색상 추출
const extractColor = (event) => {
  const imgElement = event.target;
  const colorThief = new ColorThief();

  if (imgElement && imgElement.complete) {
    try {
      const color = colorThief.getColor(imgElement);
      const rgb = `rgb(${color[0]}, ${color[1]}, ${color[2]})`;
      bgColor.value = rgb;
      // 이미지에 맞춰 그라디언트 업데이트 (필요 시)
      gradientBg.value = `linear-gradient(to bottom, ${rgb}, rgba(0, 0, 0, 0.7))`;
    } catch (error) {
      console.error("색상 추출 실패:", error);
    }
  }
};

// 이미지 로드 실패 시 대체 이미지로 교체하는 함수
const handleImageError = (e) => {
  e.target.onerror = null;
  e.target.src = 'https://placehold.co/50x50?text=No+Img'; // 혹은 서비스 기본 로고
};

</script>

<style scoped>
.music-card-feed {
  width: 180px;
  height: 180px;
  padding: 14px; /* 내부 여백 20px */
  background: #1e1e1e;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
}

.card-top {
  display: flex;
  justify-content: space-between; /* 앨범이미지 좌측 상단 */
  align-items: flex-start;
  width: 100%;
}

.album-img {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
}

.spotify-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  z-index: 5;
}

.spotify-logo{
  width: 30px;
  height: 30px;
  opacity: 0.9;

  cursor: pointer;
  transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275), opacity 0.3s ease;
}

.spotify-logo:hover {
  transform: scale(1.3);
  opacity: 1;
}

.spotify-logo:active{
  transform: scale(1.1);
}

.card-bottom {
  display: flex;
  align-items: flex-end; /* 제목/가수와 플레이어 하단 정렬 */
  justify-content: space-between;
}

.music-info {
  flex: 1;
  margin-right: 8px;
  overflow: hidden;
}

.title {
  color: white;
  margin: 0;
  font-size: 14pt; /* 제목 18pt */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.artist {
  color: #aaa;
  margin: 2px 0 0 0;
  font-size: 10pt; /* 가수 12pt */
}
</style>