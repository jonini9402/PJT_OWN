<template>
   <div class="post-item" v-if="post">

    <div class="post-main-container">

      <div class="post-left">
        <div class="post-header">
          <div class="user-info">
            <img 
              :src="post.userProfile || '/default-profile.png'" 
              class="user-avatar" 
              alt="프로필"
            />
            <div class="user-meta">
              <span class="user-nickname">{{ post.nickname || '익명' }}</span>
              <span class="post-date">{{ formatDate(post.createdAt) }}</span>
              <span class="tier-badge" v-if="post.tierLevel">{{ post.tierLevel }}</span>
            </div>
          </div>
        </div>

       <div class="post-content">
         <p class="caption">{{ post.caption || '' }}</p>
         <div class="tags">
           <span class="tag workout-tag" v-if="workoutName">{{ workoutName }}</span>
           <span 
             v-for="tag in (post.emotionTags || [])" 
             :key="tag" 
             class="tag emotion-tag"
            >
            {{ tag }}
            </span>
         </div>
       </div>

        <div class="post-actions">
          <button class="action-btn" @click="toggleLike">
            {{ isLiked ? '❤️' : '🤍' }} {{ likeCount }}
          </button>
          <button class="action-btn" @click="toggleBookmark">
            {{ isBookmarked ? '🔖' : '📑' }} {{ bookmarkCount }}
          </button>
        </div>  
      </div>

      <div class="post-right">
        <MusicCardFeed 
          v-if="post.musicTitle"
          :music="{
            musicTitle: post.musicTitle,
            artist: post.artist,
            albumImg: post.albumImg,
            previewUrl: post.previewUrl
          }" 
        />
      </div>

    </div>



    
  </div>
</template>


<script setup>
import { computed, ref, onMounted } from 'vue';
import MusicCardFeed from '@/components/post/MusicCardFeed.vue';
import { useCreateStore } from '@/stores/create';
import { useAuthStore } from '@/stores/auth';
import { addLike, removeLike, checkLiked } from '@/api/like';
import { addBookmark, removeBookmark, checkBookmarked } from '@/api/bookmark';

const props = defineProps({
  post: {
    type: Object,
    required: true
  }
});


const createStore = useCreateStore();
const authStore = useAuthStore();

const formatDate = (dateArray) => {
  if (!dateArray) return '방금 전';
  
  try {
    // dateArray가 배열인 경우 [2025, 12, 23, 14, 30, 0]
    if (Array.isArray(dateArray)) {
      const [year, month, day] = dateArray;
      return `${year}.${month}.${day}`;
    }
    // 문자열이나 Date 객체인 경우
    return new Date(dateArray).toLocaleDateString('ko-KR');
  } catch (error) {
    return '방금 전';
  }
};

const workoutName = computed(() => {
  if (!createStore.workoutTags || !props.post?.workoutTag) {
    return "운동";
  }
  
  const found = createStore.workoutTags.find(
    t => t.workoutTypeId === props.post.workoutTag
  );
  
  return found?.workoutName || "운동";
});

// 좋아요/북마크 상태
const isLiked = ref(false);
const isBookmarked = ref(false);
const likeCount = ref(props.post.likeCount || 0);
const bookmarkCount = ref(props.post.bookmarkCount || 0);

const toggleLike = async () => {
  // 로그인 체크
  if (!authStore.isLoggedIn) {
    alert('로그인이 필요합니다!');
    return;
  }

  try {
    if (isLiked.value) {
      // 좋아요 취소
      await removeLike(props.post.postId, authStore.userId);
      isLiked.value = false;
      likeCount.value--;
    } else {
      // 좋아요 추가
      await addLike(props.post.postId, authStore.userId);
      isLiked.value = true;
      likeCount.value++;
    }
  } catch (error) {
    console.error('좋아요 처리 실패:', error);
    alert('좋아요 처리에 실패했습니다.');
  }
};


const toggleBookmark = async () => {
  // 로그인 체크
  if (!authStore.isLoggedIn) {
    alert('로그인이 필요합니다!');
    return;
  }

  try {
    if (isBookmarked.value) {
      // 북마크 취소
      await removeBookmark(props.post.postId, authStore.userId);
      isBookmarked.value = false;
      bookmarkCount.value--;
    } else {
      // 북마크 추가
      await addBookmark(props.post.postId, authStore.userId);
      isBookmarked.value = true;
      bookmarkCount.value++;
    }
  } catch (error) {
    console.error('북마크 처리 실패:', error);
    alert('북마크 처리에 실패했습니다.');
  }
};

onMounted(async () => {
// createStore 데이터 로드 (아직 안 되어 있다면)
  if (!createStore.workoutTags || createStore.workoutTags.length === 0) {
    await createStore.loadWorkoutTags();
  }
  if (!createStore.allEmotionTags || createStore.allEmotionTags.length === 0) {
    await createStore.loadEmotionTags();
  }

  if (authStore.isLoggedIn && props.post?.postId) {
    try {
      // 좋아요 눌렀는지 확인
      const likedResponse = await checkLiked(props.post.postId, authStore.userId);
      isLiked.value = likedResponse.data;

      // 북마크 눌렀는지 확인
      const bookmarkedResponse = await checkBookmarked(props.post.postId, authStore.userId);
      isBookmarked.value = bookmarkedResponse.data;
    } catch (error) {
      console.error('초기 상태 확인 실패:', error);
    }
  }
});

</script>

<style scoped>
.post-item {
  width: 100%;       /* 부모인 main-content(820px)에 맞게 100%로 설정 */
  max-width: 800px;  /* 최대 800px 유지 */
  height: 350px;
  background: #2a2a2a;
  border-radius: 20px;
  padding: 30px;
  margin: 0 auto 24px auto; /* 중앙 정렬 및 아래 간격 */
  box-sizing: border-box;    /* 패딩이 폭에 포함되도록 설정 (매우 중요) */
}

.post-main-container {
  display: flex;
  height: 100%;
  justify-content: space-between; /* 양 끝으로 배치 */
  align-items: flex-start;
}

.post-left {
  flex: 1;           /* 남는 공간을 다 쓰되 */
  min-width: 0;      /* 내부 텍스트가 길어져도 우측을 밀지 않음 */
  display: flex;
  flex-direction: column;
  height: 100%;
}

.post-right {
  flex: 0 0 210px;   /* 우측 영역 폭 210px 절대 고정 */
  /* 좌측과의 최소 간격 */
}

/* 프로필 영역 */
.user-info { display: flex; align-items: center; gap: 12px; }
.user-avatar { width: 60px; height: 60px; border-radius: 50%; object-fit: cover; }
.user-meta { display: flex; flex-direction: column; gap: 2px; }
.user-nickname { font-size: 20pt; font-weight: bold; color: white; }
.post-date { font-size: 14pt; color: #aaa; }

/* 태그 영역 */
.tags { margin: 15px 0; display: flex; gap: 8px; flex-wrap: wrap; }
.tag { font-size: 0.9rem; padding: 6px 16px; border-radius: 25px; }
.workout-tag { background: #4169E1; color: white; }
.emotion-tag { background: #444; color: #ddd; }

/*캡션 영역 */
.caption { 
  height: 100px; 
  font-size: 20pt; 
  color: #eee; 
  margin: 10px 0;
  overflow-y: auto;
  line-height: 1.4;
}

.post-actions { margin-top: auto; display: flex; gap: 20px; }
.action-btn { background: none; border: none; cursor: pointer; color: white; font-size: 1.1rem; }
</style>