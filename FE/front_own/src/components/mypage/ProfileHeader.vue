<template>
  <div class="profile-header">
    <div class="profile-left">
      <div class="profile-image">
        <img :src="userInfo.profileImg || '/default-profile.png'" alt="프로필" />
      </div>
      
      </div>

    <div class="profile-right">
      <div class="profile-top">
        <div class="name-group">
          <h2 class="nickname">{{ userInfo.nickname }}</h2>
          <span class="tier-tag" :class="tierClass">{{ tierLabel }}</span>
        </div>
        
        <button class="edit-profile-btn" @click="goToEditPage">
          회원정보 수정
        </button>
      </div>

      <p class="post-count">{{ userInfo.postCount }} posts</p>

      <div class="tab-menu">
        <button 
          v-for="tab in tabs" 
          :key="tab.id"
          :class="['tab-btn', { active: currentTab === tab.id }]"
          @click="changeTab(tab.id)"
        >
          {{ tab.label }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileHeader',
  
  data() {
    return {
      userInfo: {
        name: '',
        nickname: '',
        email: '',
        profileImg: '',
        tierLevel: 0,
        postCount: 0
      },
      currentTab: 'journal',
      tabs: [
        { id: 'journal', label: '마이 포스트' },
        { id: 'likes', label: '좋아요 포스트' },
        { id: 'bookmarks', label: '북마크 포스트' }
      ]
    }
  },

  computed: {
    tierLabel() {
      if (this.userInfo.tierLevel === 3) return 'Pro';
      if (this.userInfo.tierLevel === 2) return 'Amateur';
      return 'Newbie';
    },
    
    tierClass() {
      if (this.userInfo.tierLevel === 3) return 'tier-pro';
      if (this.userInfo.tierLevel === 2) return 'tier-amateur';
      return 'tier-newbie';
    }
  },

  methods: {
    async fetchUserProfile(userId) {
      const url = userId 
        ? `http://localhost:8080/api/user/${userId}`
        : `http://localhost:8080/api/user/me`;

      try {
        const response = await fetch(url, {
          credentials: 'include' 
        });
        
        if (!response.ok) {
          throw new Error('프로필 조회 실패');
        }
        
        const data = await response.json();
        
        this.userInfo = {
          name: data.name,
          nickname: data.nickname,
          email: data.email,
          profileImg: data.profileImg,
          tierLevel: data.tierLevel,
          postCount: data.postCount
        };
      } catch (error) {
        console.error('프로필 조회 실패:', error);
      }
    },

    changeTab(tabId) {
      this.currentTab = tabId;
      this.$emit('tab-changed', tabId);
    },

    // 회원정보 수정 페이지로 이동
    goToEditPage() {
      this.$router.push('/mypage/edit');
    }
  },

  mounted() {
    this.fetchUserProfile();
  }
}
</script>

<style scoped>
.profile-header {
  display: flex;
  gap: 30px;
  padding: 24px;
  background-color: #1a1a1a;
  border-radius: 12px;
  margin-bottom: 20px;
}

.profile-left {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.profile-image {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #333;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.tier-pro { background-color: #2E3781; }
.tier-amateur { background-color: #E1603F; }
.tier-newbie { background-color: #F3DBC8; }

.profile-right {
  flex: 1;
}

/* 양끝 정렬을 위해 수정 */
.profile-top {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 닉네임은 왼쪽, 버튼은 오른쪽 */
  margin-bottom: 10px;
}

/* 닉네임과 태그를 묶어주는 그룹 */
.name-group {
  display: flex;
  align-items: center;
  gap: 12px;
}

.nickname {
  color: white;
  font-size: 16px;
  margin: 0;
  font-weight: 600;
}

.tier-tag {
  padding: 4px 14px;
  color: white;
  border-radius: 25px;
  font-size: 10px;
  font-weight: bold;
}

/* 회원수정 버튼 스타일 */
.edit-profile-btn {
  background-color: #333;
  color: #ccc;
  border: 1px solid #444;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 10px;
  cursor: pointer;
  transition: all 0.2s;
}

.edit-profile-btn:hover {
  background-color: #444;
  color: white;
  border-color: #666;
}

.post-count {
  color: #999;
  margin: 0 0 24px 0;
  font-size: 15px;
}

.tab-menu {
  display: flex;
  gap: 0;
  border-bottom: 1px solid #333;
}

.tab-btn {
  padding: 14px 24px;
  background-color: transparent;
  color: #999;
  border: none;
  cursor: pointer;
  border-bottom: 3px solid transparent;
  font-size: 12px;
  font-weight: 500;
  transition: all 0.2s;
}

.tab-btn:hover {
  color: #ccc;
}

.tab-btn.active {
  color: white;
  border-bottom-color: #4169E1;
}
</style>