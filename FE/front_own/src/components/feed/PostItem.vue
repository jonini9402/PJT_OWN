<template>
  <div class="post-item" v-if="post">

    <div class="post-main-container">

      <div class="post-left">
        <div>
          <div class="post-header">
            <div class="user-info">
              <img :src="post.profileImg || '/default-profile.png'" class="user-avatar" alt="프로필" />
              <div class="user-meta">

                <div class="user-name-row">
                  <span class="user-nickname">{{ post.nickname || '익명' }}</span>

                  <span v-if="post.tierLevel" class="tier-badge" :class="{
                    'tier-pro': post.tierLevel === 3,
                    'tier-amateur': post.tierLevel === 2,
                    'tier-newbie': post.tierLevel === 1
                  }">
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
            <p class="caption" :class="{ collapsed: !isExpanded }">{{ post.caption || '' }}</p>
            <span v-if="showCaptionMore && !isExpanded" class="more-text" @click="handleExpand">
              ... 더보기
            </span>
          </div>

        </div>

        <div class="post-bottom">
          <div class="tags">
            <div class="workout-tags">
              <span class="tag workout-tag" v-if="workoutName">{{ workoutName }}</span>
            </div>

            <div class="emotion-tags-container" :class="{ expanded: isExpanded }">
              <div class="emotion-tags">
                <span v-for="tag in visibleEmotionTags" :key="tag" class="tag emotion-tag">
                  {{ tag }}
                </span>
                
                <div v-if="!isExpanded && hiddenTagsCount > 0" class="tag-more-count" @click="handleExpand">
                  +{{ hiddenTagsCount }}
                </div>
                
              </div>
            </div>

            <div class="post-actions">
              <button class="action-btn" @click="toggleLike">
                <img
                  :src="isLiked ? heartFill : heart" alt="좋아요" class="action-icon" />
                <span>{{ likeCount }}</span>
              </button>

              <button class="action-btn" @click="toggleBookmark">
                <img
                  :src="isBookmarked ? bookmarkFill : bookmark" alt="북마크" class="action-icon" />
                <span>{{ bookmarkCount }}</span>
              </button>

            </div>
          </div>
        </div>
        
      </div>

      <div class="post-right">
        <MusicCardFeed v-if="post.musicTitle" :music="{
          musicTitle: post.musicTitle,
          artist: post.artist,
          albumImg: post.albumImg,
          previewUrl: post.previewUrl,
          spotifyTrackUrl: post.spotifyTrackUrl
        }" />
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

import heart from '@/assets/icons/heart.svg';
import heartFill from '@/assets/icons/heart_fill.svg';
import bookmark from '@/assets/icons/bookmark.svg';
import bookmarkFill from '@/assets/icons/bookmark_fill.svg';

const props = defineProps({
  post: {
    type: Object,
    required: true
  }
});


const createStore = useCreateStore();
const authStore = useAuthStore();

const isExpanded = ref(false);

const handleExpand = () => {
  isExpanded.value = true;
}

const showCaptionMore = computed(() => {
  return props.post?.caption && props.post.caption.length > 60;
});

const hiddenTagsCount = computed(() => {
  const total = props.post?.emotionTags?.length || 0;
  return total > 2 ? total - 2 : 0;
});

const visibleEmotionTags = computed(() => {
  const tags = props.post?.emotionTags || [];
  if (isExpanded.value) return tags;
  return tags.slice(0, 2);
});


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
  width: 100%;
  /* 부모인 main-content(820px)에 맞게 100%로 설정 */
  max-width: 800px;
  /* 최대 800px 유지 */
  height: auto;

  background: #2a2a2a;
  border-radius: 20px;
  padding: 30px;
  margin: 0 auto 24px auto;
  /* 중앙 정렬 및 아래 간격 */
  box-sizing: border-box;
  /* 패딩이 폭에 포함되도록 설정 (매우 중요) */
}

.post-content {
  margin-top: 24px;
}

.post-main-container {
  display: flex;
  height: 100%;
  justify-content: space-between;
  /* 양 끝으로 배치 */
  align-items: stretch;
  gap: 15px;
}

.post-left {
  flex: 1;
  /* 남는 공간을 다 쓰되 */
  min-width: 0;
  /* 내부 텍스트가 길어져도 우측을 밀지 않음 */
  display: flex;
  flex-direction: column;
  height: 100%;
}

.post-bottom {
  margin-top: auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.post-right {
  flex: 0 0 210px; 
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 오른쪽 정렬 */
  justify-content: flex-start; /* 상단 배치 */
}

/* 프로필 영역 */
.user-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-meta {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-nickname {
  font-size: 12pt;
  font-weight: bold;
  color: white;
  line-height: 1.2;
}

.post-date {
  margin-top: 5px;
  font-size: 9pt;
  color: #aaa;
}

/* 태그 영역 */
.tags {
  margin-top: 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  transition: all 0.3s ease;
}

.workout-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.emotion-tags {
  position: relative;

  width: calc(100% + 210px);

  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  overflow-x: auto;

  padding-bottom: 24px;
}

.tag {
  font-size: 0.9rem;
  padding: 6px 16px;
  border-radius: 8px;
}

.workout-tag {
  background: #ddd;
  color: #444;
}

.emotion-tag {
  background: #444;
  color: #ddd;
}

.caption {
  font-size: 10pt;
  color: #e0e0e0;
  line-height: 1.7;
  margin: 0;
  
  width: calc(100% + 15px);
  max-width: 120%;
  margin-right: -15px;

  white-space: pre-wrap;
  word-break: break-all;
  overflow-wrap: break-word;

  /* 캡션 여백 */
  min-height: 3.4em; 
  margin-bottom: 32px; 
  
  letter-spacing: 0.02em;
  word-spacing: 0.05em;
  
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  
  font-weight: 500;

  display: block;
  overflow: hidden;
  transition: max-height 0.4s cubic-bezier(0.4, 0, 0.2, 1), opacity 0.3s ease;
  opacity: 1;

}

.caption.collapsed {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;

  max-height: calc(3.4em);

}

/* 감정 태그 */
.emotion-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  align-items: center;
  transition: all 0.4s ease;
}

.tag-more-count {
  width: 32px;
  height: 32px;
  background: #444;
  color: #aaa;
  border-radius: 50%; /* 완전한 원형 */
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  font-weight: bold;
  cursor: pointer;
  border: 1px solid #555;
  transition: all 0.2s ease;
}

.tag-more-count:hover {
  background: #555;
  color: #fff;
}

/* 3. 태그 영역 확장 애니메이션 */
.emotion-tags-container {
  position: relative;
  overflow: visible;
  max-height: 40px; /* 접혔을 때 높이 */
  transition: max-height 0.4s ease-in-out;
}

.emotion-tags-container.expanded {
  max-height: 300px; /* 펼쳐졌을 때 여유 높이 */
}

.more-text {
  display: flex;
  margin-top: -30px;
  margin-bottom: 8px;
  font-size: 10pt;
  color: #aaa;
  cursor: pointer;
  user-select: none;
}

.more-text:hover {
  color: #fff;
}


.user-name-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.tier-badge {
  margin-top: 2px 8px;
  display: inline-block;
  padding: 3px 12px;
  border-radius: 12px;
  font-size: 0.7rem;
  font-weight: bold;
  color: #e5e5e5;
  width: fit-content;
}

.tier-pro {
  background-color: #2E3781;
}

.tier-amateur {
  background-color: #E1603F;
}

.tier-newbie {
  background-color: #69984d;
}

.post-actions {
  margin-top: auto;
  display: flex;
  gap: 20px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 6px;

  background: none;
  border: none;
  cursor: pointer;
  color: white;
}

.action-icon {
  width: 20px;
  height: 20px;
  object-fit: contain;

  transition: transform 0.2s cubic-bezier(0.175, 0.885, 0.32, 1.275), opacity 0.2s ease;
}

.action-btn:hover .action-icon {
  transform: scale(1.1);
  opacity: 0.85;
}

</style>