<template>
    <div>
        <button @click="togglePlay" class="play-btn">
            <span v-if="!isPlaying">▶</span>
            <span v-else>>⏸</span>
        </button>
        
    </div>
</template>

<script setup>

import { onBeforeMount, ref } from 'vue';

const props = defineProps({
    previewUrl: String // 미리듣기 URL
});

const isPlaying = ref(false);
const audio = ref(null);

const togglePlay = () => {
    if(!props.previewUrl) {
        alert("미리듣기를 지원하지 않는 곡입니다.");
        return;
    }

    if(!audio.value) {
        audio.value = new Audio(props.previewUrl);
        audio.value.onended = () => {
            isPlaying.value = false;
        }
    }

    if(isPlaying.value){
        audio.value.pause();
    } else {
        audio.value.play();
    } 
    isPlaying.value = !isPlaying.value;

};

onBeforeMount(() => {
    if (audio.value) {
        audio.value.pause();
        audio.value = null;
    }
});


</script>

<style scoped>

.play-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: rgba(0, 0, 255, 0.2);
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.3s;
}
.play-btn:hover{
    background: rgba(255, 255, 255, 0.4);
}


</style>