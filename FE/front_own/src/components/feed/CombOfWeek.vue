<template>
    <div class="workout-label">{{ rank.workoutName }}</div>

    <div class="card-container">
        
        <div class="comb-card">
            <div class="album-wrapper">
                <img :src="rank.albumImg" alt="Album Art" class="album-img" />
            </div>

            <div class="music-content">
                <div class = "music-info">

                    <div class="title-container">
                        <h5 class="title">{{ rank.musicTitle }}</h5>
                    </div>

                    <p class="artist">{{ rank.artist }}</p>
                </div>    
            </div>
        </div>
    </div>
</template>

<script setup>

defineProps({
    rank: Object
});

</script>

<style scoped>

.card-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: transform 0.3s ease; /* 부드러운 애니메이션 */
  cursor: pointer;
  width: 100%;
}

/* 호버 시 상, 좌, 우로 커지는 효과 */
.card-container:hover {
  /* 위로 이동시키면서 전체적으로 크기를 키움 */
  transform: translateY(-20px) scale(1.15); 
  z-index: 10; /* 다른 카드보다 위에 보이도록 */
}

.comb-card {
  width: 100%;
  background: #2a2a2a;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  gap: 10px;
  overflow: hidden;
  border-radius: 10px;
  box-sizing: border-box;
}

.workout-label {
  font-size: 1.1rem;
  font-weight: bold;
  color: #f8f8f8;
  margin-bottom: 12px;
  text-align: center;
  width: 100%;
}

.album-wrapper {
  width: 100%;
  aspect-ratio: 1 / 1; /* 정사각형 유지 */
  overflow: hidden;
  border-radius: 8px;
}

.album-img {
  width: 100%;
  height: 100%;
  /* 내용이 잘린다면 'contain'으로, 꽉 채우고 싶다면 'cover' 유지 */
  object-fit: cover; 
  display: block;
}

/* 음악 정보 영역 */
.music-content {
  width: 100%;
  display: flex;
  justify-content: flex-start; /* 텍스트는 왼쪽 정렬(그대로 유지) */
}

.music-info {
  width: 100%;
  overflow: hidden;
}

.title-container {
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
}

.title {
  font-size: 0.9rem;
  font-weight: 600;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #f7f7f7;
}

.card-container:hover .title {
  /* animation: marquee 5s linear infinite;
}

@keyframes marquee {
  0% { transform: translateX(0); }
  100% { transform: translateX(-100%); } */
}

.artist {
  font-size: 0.8rem;
  color: #c7c7c7;
  margin: 2px 0 0 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

</style>