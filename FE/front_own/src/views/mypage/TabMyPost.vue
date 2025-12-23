<template>
  <div class="tab-my-post">
    <!-- 로딩 중 -->
    <div v-if="loading" class="loading">
      <p>로딩 중...</p>
    </div>

    <!-- 게시물이 없을 때 -->
    <div v-else-if="posts.length === 0" class="empty-state">
      <p>아직 작성한 운동일지가 없습니다</p>
      <button @click="goToCreate">첫 운동일지 작성하기</button>
    </div>

    <!-- 플레이리스트 형태 리스트 -->
    <div v-else class="playlist">
      <div 
        v-for="post in posts" 
        :key="post.postId"
        class="playlist-item"
        @click="openModal(post)"
      >
        <!-- 재생 아이콘 (호버 시에만 표시) -->
        <div class="play-icon">
          ▶
        </div>

        <!-- 앨범 커버 -->
        <div class="album-cover">
          <img :src="post.albumImg || '/default-album.png'" alt="앨범 커버" />
        </div>

        <!-- 음악 정보 -->
        <div class="music-info">
          <p class="music-title">{{ post.musicTitle || '곡 제목' }}</p>
          <p class="artist">{{ post.artist || '아티스트' }}</p>
        </div>

        <!-- 작성 날짜 -->
        <div class="post-date">
          {{ formatDate(post.createdAt) }}
        </div>
      </div>
    </div>

    <PostDetailModal
      v-if="isModalOpen"
      :post="selectedPost"
      @close="closeModal"
    />

  </div>
</template>

<script>
import PostDetailModal from '@/components/post/PostDetailModal.vue';

export default {
  name: 'TabMyPost',

  components: {
    PostDetailModal
  },

  data() {
    return {
      posts: [],
      loading: false,
      userId: null,
      isModalOpen: false,
      selectedPost: null
    };
  },

  methods: {
    // 내 게시물 목록 가져오기
    async fetchMyPosts() {
      this.loading = true;

      try {
        // 먼저 내 userId 가져오기
        const userResponse = await fetch('http://localhost:8080/api/user/me', {
          credentials: 'include'
        });

        if (userResponse.ok) {
          const userData = await userResponse.json();
          this.userId = userData.id || userData.userId;

          // 내 게시물 조회
          const postsResponse = await fetch(
            `http://localhost:8080/api/post/users/${this.userId}`,
            {
              credentials: 'include'
            }
          );

          if (postsResponse.ok) {
            const data = await postsResponse.json();
            this.posts = data;
          }
        }
      } catch (error) {
        console.error('게시물 조회 실패:', error);
      } finally {
        this.loading = false;
      }
    },

    // 게시물 상세로 이동
    goToDetail(postId) {
      this.$router.push(`/post/${postId}`);
    },

    // 작성 페이지로 이동
    goToCreate() {
      this.$router.push('/post/create');
    },

    // 날짜 포맷팅
    formatDate(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}년 ${month}월 ${day}일`;
    },

    openModal(post) {
      this.selectedPost = post;
      this.isModalOpen = true;
    },

    closeModal() {
      this.isModalOpen = false;
      this.selectedPost = null;
    }

  },

  mounted() {
    this.fetchMyPosts();
  }
}
</script>

<style scoped>
.tab-my-post {
  padding: 20px 0;
}

/* 로딩 */
.loading {
  text-align: center;
  padding: 40px;
  color: #999;
}

/* 빈 상태 */
.empty-state {
  text-align: center;
  padding: 60px 20px;
}

.empty-state p {
  color: #999;
  font-size: 16px;
  margin-bottom: 20px;
}

.empty-state button {
  padding: 12px 24px;
  background-color: #4169E1;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
}

.empty-state button:hover {
  background-color: #3158c9;
}

/* 플레이리스트 */
.playlist {
  display: flex;
  flex-direction: column;
}

.playlist-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px 16px;
  background-color: #1a1a1a;
  cursor: pointer;
  transition: all 0.2s;
  border-bottom: 1px solid #2a2a2a;
}

.playlist-item:hover {
  background-color: #2a2a2a;
}

.playlist-item:first-child {
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.playlist-item:last-child {
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  border-bottom: none;
}

/* 재생 아이콘 - 기본적으로 숨김 */
.play-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  opacity: 0;
  transition: opacity 0.2s;
}

/* 호버 시에만 재생 아이콘 표시 */
.playlist-item:hover .play-icon {
  opacity: 1;
}

/* 앨범 커버 */
.album-cover {
  width: 56px;
  height: 56px;
  border-radius: 6px;
  overflow: hidden;
  background-color: #333;
  flex-shrink: 0;
}

.album-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 음악 정보 */
.music-info {
  flex: 1;
  min-width: 0;
}

.music-title {
  color: white;
  font-size: 15px;
  font-weight: 500;
  margin: 0 0 4px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.artist {
  color: #999;
  font-size: 13px;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 작성 날짜 */
.post-date {
  color: #666;
  font-size: 13px;
  white-space: nowrap;
}
</style>