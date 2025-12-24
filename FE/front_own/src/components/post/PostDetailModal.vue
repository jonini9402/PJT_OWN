<template>
  <div class="post-detail-modal" v-if="post" @click.self="closeModal">
    <div class="modal-content">

      <button @click="closeModal" class="close-btn">X</button>

      <div class="music-section" :style="{ backgroundColor: bgColor, backgroundImage: gradientBg }">
        
        <div class="album-img-container">
          <img 
            :src="post.albumImg" 
            alt="Album Art" 
            class="album-img" 
            crossorigin="anonymous" 
            @load="extractColor" 
          />
          <div class="play-overlay">
            <MusicPlayer class="music-player-btn" :previewUrl="post.previewUrl" />
          </div>
        </div>

        <div class="music-info">
          <div class="music-title">
            <h3>{{ post.musicTitle }}</h3>
            <p class="artist">{{ post.artist }}</p>
          </div>
        </div>

        <button @mouseover="hoverSpotify = true" @mouseleave="hoverSpotify = false" class="spotify-btn">
          <span v-if="!hoverSpotify">Spotify에서 듣기</span>
          <span v-else>Open Spotify</span>
        </button>
      </div>

      <div class="post-section">
        
        <p class="post-date">{{ formatPostDate(post.createdAt) }}의 OWN 기록</p>
        
        <hr class="divider" />
        <p class="caption">{{ post.caption }}</p>
        <div class="workout-header" style="margin-bottom: 12px;">
          <span class="workout-title">{{ workoutName }}</span>
          <span class="workout-subtitle" style="font-size: 0.9rem; color: #888; font-weight: normal;">, 이런 무드였어요</span>
        </div>

        <div class="tags-section">
          <div class="emotion-tags">
            <span v-for="tag in post.emotionTags" :key="tag" class="emotion-tag">
              #{{ tag }}
            </span>
          </div>
        </div>

        

        <div style="flex-grow: 1;"></div>

        <hr class="divider" style="margin-top: 30px; opacity: 0.3;" />

        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
          
          <div class="like-bookmark-count">
            <div class="icon-wrapper">
              <img :src="post.isLiked ? heartFill : heartOutline" alt="좋아요" class="action-icon" />
              <span class="count-text">{{ post.likeCount }}</span>
            </div>
            <div class="icon-wrapper">
              <img :src="post.isBookmarked ? bookmarkFill : bookmarkOutline" alt="북마크" class="action-icon" />
              <span class="count-text">{{ post.bookmarkCount }}</span>
            </div>
          </div>

          <div class="action-buttons" style="margin-top: 0;">
            <button class="delete-btn" @click="deletePost">삭제하기</button>
          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import MusicPlayer from './MusicPlayer.vue';
import ColorThief from 'colorthief';
import { useCreateStore } from '@/stores/create';

// 아이콘
import heartOutline from '@/assets/icons/heart.svg';
import heartFill from '@/assets/icons/heart_fill.svg';
import bookmarkOutline from '@/assets/icons/bookmark.svg';
import bookmarkFill from '@/assets/icons/bookmark_fill.svg';

const props = defineProps({ post: Object })
const emit = defineEmits(['close', 'post-deleted']);
const createStore = useCreateStore();

const workoutName = computed(() => {
  if (!createStore.workoutTags || !props.post?.workoutTag) return "운동";
  const found = createStore.workoutTags.find(t => t.workoutTypeId === props.post.workoutTag);
  return found?.workoutName || "운동";
})

const closeModal = () => emit('close');
const hoverSpotify = ref(false);
const bgColor = ref('#111');
const gradientBg = ref('linear-gradient(to bottom, rgba(0,0,0,0), rgba(0,0,0,0.8))');

const extractColor = () => {
  const imgElement = document.querySelector('.album-img');
  const colorThief = new ColorThief();
  if (imgElement && imgElement.complete) {
    try {
      const color = colorThief.getColor(imgElement);
      bgColor.value = `rgb(${color[0]}, ${color[1]}, ${color[2]})`;
    } catch (e) { console.log(e); }
  }
}

const formatPostDate = (date) => {
  const postDate = new Date(date);
  return `${postDate.getFullYear()}년 ${postDate.getMonth() + 1}월 ${postDate.getDate()}일`;
};

const deletePost = async () => {
  if (!confirm("정말 이 게시글을 삭제하시겠습니까? 복구할 수 없습니다.")) return;
  try {
    const response = await fetch(`http://localhost:8080/api/post/${props.post.postId}`, {
      method: 'DELETE',
      credentials: 'include',
    });
    if (response.ok) {
      alert("게시글이 삭제되었습니다.");
      window.location.reload();
    } else {
      alert("삭제 중 오류가 발생했습니다.");
    }
  } catch (error) {
    console.error("삭제 에러:", error);
  }
};
</script>

<style scoped>
/* 모달 배경 */
.post-detail-modal {
  position: fixed;
  top: 0; left: 0; width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.75);
  backdrop-filter: blur(8px);
  display: flex; justify-content: center; align-items: center;
  z-index: 1000;
}

/* 모달 박스 */
.modal-content {
  display: flex;
  width: 900px; max-width: 90%; height: 600px;
  background-color: #1e1e1e;
  border-radius: 20px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.6);
}

/* 닫기 버튼 */
.close-btn {
  position: absolute; top: 20px; right: 20px;
  background: rgba(0, 0, 0, 0.3);
  width: 36px; height: 36px;
  border-radius: 50%; border: none;
  color: #fff; font-size: 1.2rem; cursor: pointer; z-index: 20;
  display: flex; align-items: center; justify-content: center;
  transition: all 0.2s;
}

/* --- 왼쪽: 음악 섹션 --- */
.music-section {
  width: 40%; padding: 40px 30px;
  color: white;
  display: flex; flex-direction: column;
  justify-content: center; align-items: center;
  position: relative; text-align: center;
  background: #222; /* 기본 배경색 */
}

/* 앨범 아트 컨테이너 */
.album-img-container {
  width: 240px; height: 240px;
  position: relative;
  border-radius: 12px; overflow: hidden;
  box-shadow: 0 15px 35px rgba(0,0,0,0.5);
  margin-bottom: 30px;
}
.album-img { width: 100%; height: 100%; object-fit: cover; transition: transform 0.3s, filter 0.3s; }

/* 재생 버튼 오버레이 */
.play-overlay {
  position: absolute; top: 0; left: 0; width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.3);
  display: flex; justify-content: center; align-items: center;
  opacity: 0; transition: opacity 0.3s ease; cursor: pointer;
}
.album-img-container:hover .play-overlay { opacity: 1; }
.album-img-container:hover .album-img { transform: scale(1.05); filter: blur(2px); }

/* 플레이어 버튼 크기 */
.music-player-btn { transform: scale(1.5); filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5)); }

.music-info { margin-bottom: 25px; }
.music-title h3 { font-size: 1.5rem; margin-bottom: 6px; font-weight: 700; }
.artist { font-size: 1rem; opacity: 0.8; }

/* Spotify 버튼 */
.spotify-btn {
  background: rgba(29, 185, 84, 0.9); color: white;
  border: 1px solid rgba(255,255,255,0.2); border-radius: 30px;
  padding: 10px 24px; font-size: 0.9rem; font-weight: 600;
  cursor: pointer; transition: all 0.2s;
  display: flex; align-items: center; gap: 8px;
}
.spotify-btn:hover { background: #1db954; transform: translateY(-2px); }

/* --- 오른쪽: 포스트 섹션 --- */
.post-section {
  width: 60%; background-color: #181818;
  padding: 45px;
  display: flex; flex-direction: column;
  overflow-y: auto; color: #fff;
}

/* 스크롤바 디자인 */
.post-section::-webkit-scrollbar { width: 6px; }
.post-section::-webkit-scrollbar-thumb { background-color: #333; border-radius: 3px; }
.post-section::-webkit-scrollbar-track { background-color: transparent; }

.post-date { font-size: 0.9rem; color: #888; margin-bottom: 10px; }
.divider { border: none; border-top: 1px solid rgba(255,255,255,0.08); margin: 20px 0; }

.workout-title { font-size: 1.3rem; font-weight: 700; color: #e8e8e8; }

/* 태그 영역  */
.tags-section { margin-top: 10px; margin-bottom: 30px; }
.emotion-tags { display: flex; flex-wrap: wrap; gap: 8px; }
.emotion-tag {
  background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.15);
  color: #ccc; padding: 5px 12px; border-radius: 8px; font-size: 0.8rem;
}

/* 본문 텍스트 */
.caption {
  font-size: 1rem; color: #e0e0e0;
  line-height: 1.8; font-weight: 300; white-space: pre-wrap;
  margin-bottom: 20px;
}

/* 하단 액션바 */
.like-bookmark-count { display: flex; gap: 20px; }
.icon-wrapper { display: flex; align-items: center; gap: 6px; cursor: pointer; transition: transform 0.2s; opacity: 0.9; }
.icon-wrapper:hover { transform: scale(1.1); opacity: 1; }
.action-icon { width: 22px; height: 22px; }
.count-text { font-size: 0.9rem; color: #bbb; }

/* 삭제 버튼 (미니멀) */
.delete-btn {
  background: none; border: none; color: #666;
  padding: 8px 12px; font-size: 0.85rem; font-weight: 500;
  cursor: pointer; transition: all 0.2s;
}
.delete-btn:hover { color: #ff5e57; background: rgba(255,94,87,0.1); border-radius: 6px; }
</style>