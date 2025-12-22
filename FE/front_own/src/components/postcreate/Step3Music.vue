<template>
    <div>
        <h2>어떤 노래를 들었나요?</h2>

        <div class="search-bar">
            <input 
                v-model="searchQuery" 
                @keyup.enter="handleSearch" 
                placeholder="제목을 검색하세요"
            />
            <button @click="handleSearch" :disabled="!searchQuery.trim()">검색</button>
        </div>

        <div class="music-selection-area">
            <div class="selection-preview">
                <h3>현재 선택된 음악</h3>
                <div v-if="createStore.selectedMusic" class="preview-card">
                    <img :src="createStore.selectedMusic.albumImg" class="preview-img" />
                    <div class="preview-info">
                        <p class="p-title">{{ createStore.selectedMusic.musicTitle }}</p>
                        <p class="p-artist">{{ createStore.selectedMusic.artist }}</p>
                    </div>
                    <MusicPlayer :previewUrl="createStore.selectedMusic.previewUrl" />
                </div>
                <div v-else class="empty-msg">오른쪽에서 음악을 검색하고 선택해주세요!</div>
            </div>

            <div class="search-results">
                <h3>검색 결과</h3>
                <div v-if="isSearching" class="loading-msg">검색 중...</div>
                <div v-else-if="searchResults.length > 0" class="results-scroll">
                    <MusicCardSearch 
                        v-for="item in searchResults" 
                        :key="item.spotifyTrackId"
                        :music="item"
                        :isSelected="createStore.selectedMusic?.spotifyTrackId === item.spotifyTrackId"
                        @select="confirmSelection"
                    />
                </div>
                <p v-else class="empty-msg">검색 결과가 없습니다.</p>
            </div>
        </div>


        <div class="nav-btns">
            <button @click="createStore.setStep(4)" :disable="!createStore.selectedMusic">다음</button>
        </div>

    </div>
</template>

<script setup>
import MusicCardSearch from '../post/MusicCardSearch.vue';
import MusicPlayer from '../post/MusicPlayer.vue';
import { useCreateStore } from '@/stores/create';
import { searchSpotify } from '@/api/music';
import { ref } from 'vue';

const createStore = useCreateStore();
const searchQuery = ref('');
const searchResults = ref([]);
const isSearching = ref(false);

const spotifyToken = 'BQBzT8SJCQUR_7ZOfcplmkZvH7JKU5Bzsfk5EfZA0Id1mV8e3qWwB3i4w4-PX5gme5ydPGZ4cXc4KFJCQ6c2H0jH-1k0BakIIWKgWg7iREFYCc12UC0FZI8CCeJDJx2dVV_u9DGlsNk';

const handleSearch = async () => {
    if (!searchQuery.value.trim()) return;

    isSearching.value = true;

    try {
        const response = await searchSpotify(searchQuery.value, spotifyToken);

        searchResults.value = response.data.tracks.items.map(track => ({
            spotifyTrackId: track.id,
            musicTitle: track.name,
            artist: track.artists[0].name,
            albumImg: track.album.images[0]?.url,
            duration: track.duration_ms,
            previewUrl: track.preview_url
        }));

        console.log("검색 결과:", searchResults.value);
    } catch (error) {
        console.error("Spotify 검색 실패:", error);
        alert("검색에 실패했습니다. 다시 시도해주세요.");
        searchResults.value = [];
    } finally {
        isSearching.value = false;
    }
};

const confirmSelection = (music) => {
    // Step4에서 필요한 형식으로 저장
    createStore.selectedMusic = {
        spotifyTrackId: music.spotifyTrackId,
        musicTitle: music.musicTitle,
        artist: music.artist,
        albumImg: music.albumImg,
        duration: music.duration,
        previewUrl: music.previewUrl
    };
    
    console.log("선택된 음악:", createStore.selectedMusic);
};

const getTemporaryToken = async () => {
  const clientId = '41753e8381d14ace9eaf71a3e0c01870';
  const clientSecret = 'f72dcaae55184cba9828075e5629032d';

  const params = new URLSearchParams();
  params.append('grant_type', 'client_credentials');

  const response = await fetch('https://accounts.spotify.com/api/token', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa(clientId + ':' + clientSecret)
    },
    body: params
  });

  const data = await response.json();
  console.log("새로운 토큰입니다:", data.access_token);
};

// 실행
getTemporaryToken();

</script>

<style scoped>

.step-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

h2 {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
}

.search-bar { 
    display: flex; 
    gap: 10px; 
    margin-bottom: 20px;
    max-width: 600px;
    margin-left: auto;
    margin-right: auto;
}

.search-bar input { 
    flex: 1; 
    padding: 12px; 
    border: 1px solid #ddd; 
    border-radius: 8px;
    font-size: 1rem;
}

.search-bar input:focus {
    outline: none;
    border-color: #42b883;
}

.search-bar button {
    padding: 12px 24px;
    background: #42b883;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-weight: bold;
    transition: background 0.3s;
}

.search-bar button:hover:not(:disabled) {
    background: #359268;
}

.search-bar button:disabled {
    background: #ccc;
    cursor: not-allowed;
}

.music-selection-area { 
    display: flex; 
    gap: 30px; 
    margin: 20px 0; 
    height: 450px; 
}

.selection-preview { 
    flex: 1; 
    background: #f8f9fa; 
    border-radius: 12px; 
    padding: 20px; 
    display: flex; 
    flex-direction: column; 
    align-items: center; 
    border: 1px dashed #ccc; 
}

.selection-preview h3 {
    margin-bottom: 20px;
    color: #666;
    font-size: 0.95rem;
}

.preview-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

.preview-img { 
    width: 100%; 
    max-width: 200px; 
    border-radius: 8px; 
    box-shadow: 0 4px 10px rgba(0,0,0,0.2); 
    margin-bottom: 15px; 
}

.preview-info {
    text-align: center;
    width: 100%;
    margin-bottom: 15px;
}

.p-title { 
    font-weight: bold; 
    font-size: 1.1rem; 
    margin: 5px 0;
    color: #333;
}

.p-artist { 
    color: #666; 
    margin-bottom: 15px;
    font-size: 0.95rem;
}

.search-results { 
    flex: 1.5; 
    background: #fff; 
    border-radius: 12px; 
    padding: 20px; 
    display: flex; 
    flex-direction: column;
    border: 1px solid #eee;
}

.search-results h3 {
    margin-bottom: 15px;
    color: #333;
    font-size: 1.1rem;
}

.results-scroll { 
    overflow-y: auto; 
    flex: 1; 
}

.loading-msg {
    text-align: center;
    padding: 40px;
    color: #666;
}

.empty-msg { 
    margin-top: 100px; 
    color: #999; 
    text-align: center; 
}

.nav-btns {
    display: flex;
    gap: 10px;
    margin-top: 20px;
}

.next-btn {
    flex: 1;
    background: #333;
    color: white;
    padding: 15px;
    border-radius: 8px;
    border: none;
    font-weight: bold;
    font-size: 1rem;
    cursor: pointer;
    transition: background 0.3s;
}

.next-btn:hover:not(:disabled) {
    background: #42b883;
}

.next-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
}

</style>