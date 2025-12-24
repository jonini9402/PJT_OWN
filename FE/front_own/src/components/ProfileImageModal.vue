<template>
  <div v-if="isOpen" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <h2 class="modal-title">프로필 캐릭터를 선택하세요</h2>

      <div class="image-grid">
        <button
          v-for="(image, index) in images"
          :key="index"
          class="image-item"
          :class="{ selected: selectedImage === image }"
          @click="selectAndConfirm(image)"
        >
          <div class="img-container">
            <img :src="image" :alt="`프로필 ${index + 1}`" />
            
            <div v-if="selectedImage === image" class="selected-overlay"></div>
            <div v-if="selectedImage === image" class="check">✔</div>
            
          </div>
        </button>
      </div>

      <div class="modal-buttons">
        <button @click="closeModal" class="cancel-btn">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileImageModal',
  props: {
    isOpen: Boolean,
    images: Array,
    currentImage: String
  },
  data() {
    return {
      selectedImage: this.currentImage
    }
  },
  watch: {
    // 모달이 열릴 때마다 현재 선택된 이미지를 부모 값과 동기화
    currentImage(newVal) {
      this.selectedImage = newVal;
    }
  },
  methods: {
    selectAndConfirm(image) {
      this.selectedImage = image;
      this.$emit('select', image); // 부모에게 선택된 이미지 전달
      this.closeModal();
    },
    closeModal() {
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(5px);
  z-index: 2000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: #1e1e1e;
  padding: 40px;
  border-radius: 24px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.5);
  text-align: center;
  color: #fff;
}

.modal-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 25px;
  letter-spacing: -0.5px;
}

.image-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.image-item {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  outline: none;
}

.img-container {
  position: relative;
  width: 100%;
  aspect-ratio: 1 / 1;
  border-radius: 50%;
  overflow: hidden;
  border: 3px solid #333; 
  transition: all 0.2s ease;
}

.image-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-item:not(.selected):hover .img-container {
  transform: translateY(-3px);
  border-color: #555; 
}

.image-item.selected .img-container {
  border-color: #fff;
  transform: scale(1.05);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
}

.selected-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.4); 
  transition: opacity 0.2s ease;
}

.cancel-btn {
  padding: 12px 40px;
  background: #333; 
  border: 1px solid #444;
  border-radius: 12px;
  color: #d0d0d0;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.cancel-btn:hover {
  background: #444;
  color: #fff;
  border-color: #666;
}

.check {
  position: absolute;
  top: 6px;
  right: 6px;
  background: #1db954; /* 스포티파이 그린 */
  color: white;
  font-size: 14px;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0,0,0,0.3);
}
</style>