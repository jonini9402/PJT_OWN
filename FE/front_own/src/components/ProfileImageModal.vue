<template>
  <div v-if="isOpen" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <h2>프로필 캐릭터를 선택하세요</h2>

      <div class="image-grid">
        <button
          v-for="(image, index) in images"
          :key="index"
          class="image-item"
          :class="{ selected: selectedImage === image }"
          @click="selectAndConfirm(image)"
        >
          <img :src="image" :alt="`프로필 ${index + 1}`" />
          <span v-if="selectedImage === image" class="check">✔</span>
        </button>
      </div>

      <div class="modal-buttons">
        <button @click="closeModal" class="cancel-btn">취소</button>
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
  methods: {
    selectAndConfirm(image) {
      this.selectedImage = image;
      this.$emit('select', image);
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
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 2000;
}

.modal-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  background: white;
  padding: 32px;
  border-radius: 16px;
  width: 480px;
  max-width: 90%;
}

.image-item {
  position: relative;
  border: 2px solid transparent;
  border-radius: 12px;
  padding: 6px;
  cursor: pointer;
  background: none;
  transition: transform 0.15s, border-color 0.15s;
}

.image-item:hover {
  transform: scale(1.05);
}

.image-item.selected {
  border-color: #4CAF50;
  transform: scale(1.08);
}

.image-item img {
  width: 100%;
  height: 110px;
  object-fit: cover;
  border-radius: 10px;
}

.check {
  position: absolute;
  top: 6px;
  right: 6px;
  background: #4CAF50;
  color: white;
  font-size: 14px;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>