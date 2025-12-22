<template>
  <div v-if="isOpen" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <h2>프로필 이미지 선택</h2>
      
      <div class="image-grid">
        <div 
          v-for="(image, index) in images" 
          :key="index"
          class="image-item"
          :class="{ selected: selectedImage === image }"
          @click="selectImage(image)"
        >
          <img :src="image" :alt="`프로필 ${index + 1}`" />
        </div>
      </div>
      
      <div class="modal-buttons">
        <button @click="closeModal" class="cancel-btn">취소</button>
        <button @click="confirmSelection" class="confirm-btn">선택</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileImageModal',
  props: {
    isOpen: {
      type: Boolean,
      required: true
    },
    images: {
      type: Array,
      required: true
    },
    currentImage: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      selectedImage: this.currentImage
    }
  },
  methods: {
    selectImage(image) {
      this.selectedImage = image;
    },
    confirmSelection() {
      this.$emit('select', this.selectedImage);
      this.closeModal();
    },
    closeModal() {
      this.$emit('close');
    }
  },
  watch: {
    currentImage(newVal) {
      this.selectedImage = newVal;
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 12px;
  max-width: 500px;
  width: 90%;
  max-height: 80vh;
  overflow-y: auto;
}

h2 {
  margin-bottom: 20px;
  text-align: center;
}

.image-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
  margin-bottom: 20px;
}

.image-item {
  cursor: pointer;
  border: 3px solid transparent;
  border-radius: 8px;
  padding: 5px;
  transition: all 0.2s;
}

.image-item:hover {
  border-color: #ddd;
}

.image-item.selected {
  border-color: #4CAF50;
  background-color: #f0f8f0;
}

.image-item img {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
}

.modal-buttons {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

.cancel-btn, .confirm-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
}

.cancel-btn {
  background-color: #f5f5f5;
  color: #333;
}

.confirm-btn {
  background-color: #4CAF50;
  color: white;
}
</style>