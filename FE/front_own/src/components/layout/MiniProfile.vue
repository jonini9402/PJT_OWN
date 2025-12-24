<template>
  <div class="mini-profile" v-if="nickname">
    <div class="profile-image">
      <img :src="profileImg" alt="프로필" />
    </div>
    
    <p class="nickname">{{ nickname }}</p>
    
    <div class="tier-badge" :class="tierClass">
      {{ tierLabel }}
    </div>
  </div>
  
  <div class="mini-profile" v-else>
    <p style="color: #999;">로딩중...</p>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();

// computed로 getter 값들을 받아오기
const nickname = computed(() => authStore.nickname);
const profileImg = computed(() => authStore.profileImg || '/default-profile.png');
const tierLabel = computed(() => authStore.tierLabel);
const tierClass = computed(() => authStore.tierClass);
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
  border-radius: 6px;
  color: #e5e5e5;
  font-size: 13px;
  font-weight: bold;
}

.tier-pro {
  background-color: #2E3781;
}

.tier-amateur {
  background-color: #E1603F;
}

.tier-newbie {
  background-color: #b9a798;
}
</style>