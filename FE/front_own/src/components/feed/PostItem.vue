<template>
   <div class="post-item" v-if="post">

    <div class="post-main-container">

      <div class="post-left">
        <div class="post-header">
          <div class="user-info">
            <img 
              :src="post.profileImg || '/default-profile.png'" 
              class="user-avatar" 
              alt="프로필"
            />
            <div class="user-meta">

              <div class="user-name-row">
                <span class="user-nickname">{{ post.nickname || '익명' }}</span>

                <span
                  v-if="post.tierLevel"
                    class="tier-badge"
                    :class="{
                      'tier-pro': post.tierLevel === 3,
                      'tier-amateur': post.tierLevel === 2,
                      'tier-newbie': post.tierLevel === 1
                        }" >
                  <template v-if="post.tierLevel === 3">Pro</template>
                  <template v-else-if="post.tierLevel === 2">Amateur</template>
                  <template v-else>Newbie</template>
                </span>
                
              </div>
              <span class="post-date">{{ formatDate(post.createdAt) }}</span>


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

  let createdDate;

  // [2025, 12, 23, 14, 30, 0] 형태 대응
  if (Array.isArray(dateArray)) {
    const [year, month, day, hour = 0, min = 0, sec = 0] = dateArray;
    createdDate = new Date(year, month - 1, day, hour, min, sec);
  } else {
    createdDate = new Date(dateArray);
  }

  const now = new Date();
  const diffMs = now - createdDate;
  const diffSec = Math.floor(diffMs / 1000);
  const diffMin = Math.floor(diffSec / 60);
  const diffHour = Math.floor(diffMin / 60);
  const diffDay = Math.floor(diffHour / 24);

  if (diffSec < 60) return '방금 전';
  if (diffMin < 60) return `${diffMin}분 전`;
  if (diffHour < 24) return `${diffHour}시간 전`;
  if (diffDay < 7) return `${diffDay}일 전`;

  // 일주일 넘으면 날짜 표시
  return createdDate.toLocaleDateString('ko-KR');
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

      // 수정된 부분: 0보다 클때만 1감소, 아니면 0으로 고정
      if (likeCount.value > 0) {
        likeCount.value--;
      } else {
        likeCount.value = 0;
      }
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
      // 수정된 부분: 0보다 클때만 1 감소
      if (bookmarkCount.value > 0) {
        bookmarkCount.value--;
      } else {
        bookmarkCount.value = 0;
      }
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

const tierLabel = computed(() => {
  if (props.post.tierLevel === 3) return 'Pro';
  if (props.post.tierLevel === 2) return 'Amateur';
  return 'Newbie';
});

const tierClass = computed(() => {
  if (props.post.tierLevel === 3) return 'tier-pro';
  if (props.post.tierLevel === 2) return 'tier-amateur';
  return 'tier-newbie';
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
.user-info { display: flex; align-items: center; gap: 16px; }
.user-avatar { width: 44px; height: 44px; border-radius: 50%; object-fit: cover; }
.user-meta { display: flex; flex-direction: column; gap: 2px; }
.user-nickname { font-size: 14pt; font-weight: bold; color: white; line-height: 1.2;}
.post-date { font-size: 10pt; color: #aaa; }

/* 태그 영역 */
.tags { margin: 15px 0; display: flex; gap: 8px; flex-wrap: wrap; }
.tag { font-size: 0.9rem; padding: 6px 16px; border-radius: 25px; }
.workout-tag { background: #4169E1; color: white; }
.emotion-tag { background: #444; color: #ddd; }

/*캡션 영역 */
.caption { 
  height: 100px; 
  font-size: 12pt; 
  color: #eee; 
  margin: 10px 0;
  overflow-y: auto;
  line-height: 1.4;
}

.user-name-row {
  display: flex;
  align-items: center;
  gap: 14px;
}

.tier-badge {
  margin-top: 2px 8px;
  display: inline-block;
  padding: 3px 12px;
  border-radius: 12px;
  font-size: 0.7rem;
  font-weight: bold;
  color: white;
  width: fit-content;
}

.tier-pro {
  background-color: #4169E1;
}

.tier-amateur {
  background-color: #FF6B6B;
}

.tier-newbie {
  background-color: #51CF66;
}

.post-actions { margin-top: auto; display: flex; gap: 20px; }
.action-btn { background: none; border: none; cursor: pointer; color: white; font-size: 1.1rem; }
</style>