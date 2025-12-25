<template>
  <div class="mini-profile" v-if="nickname">
    <div class="profile-image">
      <img :src="profileImg" alt="프로필" />
    </div>
    
    <p class="nickname">{{ nickname }}</p>
    
    <div class="tier-badge" :class="tierClass">
      {{ tierLabel }}
    </div>

    <button class="edit-text-btn" @click="goToEditPage">
      프로필 편집
    </button>
  </div>
  
  <div class="mini-profile" v-else>
    <p style="color: #999;">로딩중...</p>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const nickname = computed(() => authStore.nickname);
const profileImg = computed(() => authStore.profileImg || '/default-profile.png');
const tierLabel = computed(() => authStore.tierLabel);
const tierClass = computed(() => authStore.tierClass);

const goToEditPage = () => {
  router.push('/mypage/edit');
};
</script>

<style scoped>
.mini-profile {
  background-color: #2a2a2a;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  margin-bottom: 30px;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  margin: 0 auto 12px;
  background-color: #333;
  border: 3px solid #444;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.nickname {
  color: white;
  font-size: 16px;
  font-weight: 600;
  margin: 0 0 10px 0;
}

.tier-badge {
  display: inline-block;
  padding: 6px 20px;
  border-radius: 25px;
  color: #e5e5e5;
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 15px;
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

.edit-text-btn {
  width: 100%;
  padding: 8px;
  background-color: transparent;
  border: 1px solid #444;
  border-radius: 6px;
  color: #999;
  font-size: 12px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.edit-text-btn:hover {
  background-color: #333;
  border-color: #555;
  color: #ccc;
}
</style>