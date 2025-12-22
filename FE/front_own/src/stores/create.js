import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getWorkoutTags } from '@/api/workout';
import { getEmotionTags } from '@/api/emotion';

export const useCreateStore = defineStore('create', () => {
  
  const currentStep = ref(1);
  const workoutTags = ref([]);
  const workoutTag = ref('');
  const allEmotionTags = ref([]);
  const selectedEmotionTags = ref([]);
  const selectedMusic = ref(null);
  const caption = ref('');

  const setStep = (step) => { currentStep.value = step; };

  const fetchWorkoutTags = async () => {
    try {
      const response = await getWorkoutTags();
      workoutTags.value = response.data;
    } catch (error) {
      console.error("태그를 불러오는데 실패했습니다", error);
    }
  }

  const fetchEmotionTags = async () => {
    try {
      const response = await getEmotionTags();
      allEmotionTags.value = response.data;
    } catch(error) {
      console.error("감정 태그 로드 실패", error);
    }
  }

  //객체 배열에서 ID로 중복 체크
  const toggleEmotion = (tag) => {
    const index = selectedEmotionTags.value.findIndex(e => e.emotionTypeId === tag.emotionTypeId);
    
    if(index > -1) {
      selectedEmotionTags.value.splice(index, 1);
    } else {
      if(selectedEmotionTags.value.length < 5) {
        selectedEmotionTags.value.push({
          emotionTypeId: tag.emotionTypeId,
          emotionName: tag.emotionName
        });
      } else {
        alert("감정 태그는 최대 5개까지 선택 가능합니다");
      }
    }
  }


  const resetData = () => {
    currentStep.value = 1;
    workoutTag.value = '';
    selectedEmotionTags.value = [];
    selectedMusic.value = null;
    caption.value = '';
  };

  return { currentStep, workoutTag, selectedEmotionTags, selectedMusic, caption, setStep, resetData, workoutTags, fetchWorkoutTags, allEmotionTags, fetchEmotionTags, toggleEmotion };
});
