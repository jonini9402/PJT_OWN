<template>
    <div class="wrapping-page">

        <div class="bg-animation"></div>

        <div class="create-page">
            <Step1Workout v-if="createStore.currentStep === 1" />
            <Step2Emotion v-if="createStore.currentStep === 2" />
            <Step3Music v-if="createStore.currentStep === 3" />
            <Step4Caption v-if="createStore.currentStep === 4" />
    
            <ProgressBar :step="createStore.currentStep"/>
        </div>

    </div>

</template>

<script setup>
import ProgressBar from '@/components/postcreate/ProgressBar.vue';
import Step1Workout from '@/components/postcreate/Step1Workout.vue';
import Step2Emotion from '@/components/postcreate/Step2Emotion.vue';
import Step3Music from '@/components/postcreate/Step3Music.vue';
import Step4Caption from '@/components/postcreate/Step4Caption.vue';
import { useCreateStore } from '@/stores/create';

const createStore = useCreateStore();

</script>

<style scoped>
    
.wrapping-page {
    width: 100vw;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    overflow: hidden;
}

.create-page {
    position: relative;
    width: 800px;
    height: 960px;
    background: rgba(255, 255, 255, 0.15);
    backdrop-filter: blur(20px);
    -webkit-backdrop-filter: blur(20px);
    border-radius: 40px;
    
    /* 테두리 ?? */
    border-width: 0.1px;
    border-style: solid;
    border-color: rgb(240, 240, 240);

    box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    padding: 60px 40px;
    margin: 0 auto;
    box-sizing: border-box;
    z-index: 5;
}


.bg-animation {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0; /* 배경이므로 가장 뒤로 */
  overflow: hidden;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.2),
    rgba(0, 0, 0, 0.1)
  );
}

.bg-animation::before,
.bg-animation::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 200%;
  height: 200%;
  background: conic-gradient(
    from 0deg,
    #E1603F,
    #2E3781,
    #E1603F,
    #F3DBC8,
    #2E3781
  );
  transform: translate(-50%, -50%);
  animation: rotate 8s linear infinite;
  filter: blur(50px);
  opacity: 0.8;
}

.bg-animation::after {
  width: 180%;
  height: 180%;
  animation: rotate-reverse 10s linear infinite;
  opacity: 0.6;
}

@keyframes rotate {
  0% { transform: translate(-50%, -50%) rotate(0deg); }
  100% { transform: translate(-50%, -50%) rotate(360deg); }
}

@keyframes rotate-reverse {
  0% { transform: translate(-50%, -50%) rotate(0deg); }
  100% { transform: translate(-50%, -50%) rotate(-360deg); }
}

</style>