<template>
    <div class="step-container">
        <h2 class="step-title">어떤 노래를 들었나요?</h2>

        <div class="search-section">
            <div class="search-bar">
                <input 
                    v-model="searchQuery" 
                    @keyup.enter="handleSearch" 
                    placeholder="곡 제목 또는 아티스트를 입력하세요"
                />
                <button @click="handleSearch" :disabled="!searchQuery.trim()" class="search-btn">검색</button>
            </div>
        </div>

        <div class="divider"></div>

        <div class="music-selection-area">
            <div class="selection-preview">
                <h3 class="sub-title">선택된 음악</h3>
                <div v-if="createStore.selectedMusic" class="preview-card">
                    <div class="img-wrapper">
                        <img :src="createStore.selectedMusic.albumImg" class="preview-img" />
                    </div>
                    <div class="preview-info">
                        <p class="p-title">{{ createStore.selectedMusic.musicTitle }}</p>
                        <p class="p-artist">{{ createStore.selectedMusic.artist }}</p>
                    </div>
                        <a href="https://www.spotify.com" target="_blank" class="spotify-link">
                            <div class="logo-wrapper">
                                <img src="@/assets/spotifyLogo/spotify_logo_white.png" alt="SpotifyLogo" class="spotify-logo white"/>
                                <img src="@/assets/spotifyLogo/spotify_logo_green.png" alt="SpotifyLogo" class="spotify-logo green"/>
                            </div>
                        </a>
                </div>
                <div v-else class="empty-msg">
                    <p>검색 결과에서<br/>곡을 선택해주세요</p>
                </div>
            </div>

            <div class="search-results">
                <h3 class="sub-title">검색 결과</h3>
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
                <div v-else class="empty-msg">검색 결과가 없습니다.</div>
            </div>
        </div>

        <div class="divider"></div>

        <div class="nav-area">
            <button @click="prev" class="prev-btn">이전</button>
            <button 
                @click="next" 
                :disabled="!createStore.selectedMusic" 
                class="next-btn"
            >
                다음
            </button>
        </div>
    </div>
</template>

<script setup>
import MusicCardSearch from '../post/MusicCardSearch.vue';
import MusicPlayer from '../post/MusicPlayer.vue';
import { useCreateStore } from '@/stores/create';
import { searchSpotify } from '@/api/music';
import { ref, onMounted } from 'vue';

const createStore = useCreateStore();
const searchQuery = ref('');
const searchResults = ref([]);
const isSearching = ref(false);
const spotifyToken = ref('');

const handleSearch = async () => {
    if (!searchQuery.value.trim()) return;

    if (!spotifyToken.value) {
        await getTemporaryToken();
    }
    isSearching.value = true;

    try {
        const response = await searchSpotify(searchQuery.value, spotifyToken.value);
        searchResults.value = response.data.tracks.items.map(track => ({
            spotifyTrackId: track.id,
            musicTitle: track.name,
            artist: track.artists[0].name,
            albumImg: track.album.images[0]?.url,
            duration: track.duration_ms,
            previewUrl: track.preview_url
        }));
    } catch (error) {
        console.error("Spotify 검색 실패:", error);
        searchResults.value = [];
    } finally {
        isSearching.value = false;
    }
};

const confirmSelection = (music) => {
    createStore.selectedMusic = { ...music };
};

const prev = () => {
    createStore.setStep(2);
};

const next = () => {
    createStore.setStep(4);
};

const getTemporaryToken = async () => {
    // 기존 토큰 발급 로직 유지
    const clientId = '41753e8381d14ace9eaf71a3e0c01870';
    const clientSecret = 'f72dcaae55184cba9828075e5629032d';
    const params = new URLSearchParams();
    params.append('grant_type', 'client_credentials');

    try {
        const response = await fetch('https://accounts.spotify.com/api/token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic ' + btoa(clientId + ':' + clientSecret)
            },
            body: params
        });
        const data = await response.json();
        spotifyToken.value = data.access_token;
    } catch (e) {
        console.error("토큰 발급 실패:", e);
    }
};

onMounted(() => {
    getTemporaryToken();
});
</script>

<style scoped>
.step-container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
}

.step-title {
    font-size: 1.3rem; /* 1.6rem → 1.3rem (Step1, Step2와 동일) */
    font-weight: 700;
    margin-bottom: 20px;
}

.spotify-link {
    margin-top: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    text-decoration: none;
    z-index: 5;
}

.logo-wrapper {
    position: relative;
    width: 30px;
    height: 30px;
    cursor: pointer;
    /* 호버 시 커지는 효과를 wrapper에 부여 */
    transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.spotify-logo {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    transition: opacity 0.3s ease;
}

/* 초기 상태: 녹색 로고는 숨김 */
.spotify-logo.green {
    opacity: 0;
}

/* 호버 상태 정의 */
.logo-wrapper:hover {
    transform: scale(1.3);
}

.logo-wrapper:hover .white {
    opacity: 0;
}

.logo-wrapper:hover .green {
    opacity: 1;
}

.logo-wrapper:active {
    transform: scale(1.1);
}


.divider {
    width: 90%;
    height: 2px;
    background: linear-gradient(
        90deg, 
        rgba(255, 255, 255, 0) 0%, 
        rgba(255, 255, 255, 0.6) 50%, 
        rgba(255, 255, 255, 0) 100%
    );
    margin: 15px 0;
}

/* --- 검색바 스타일 --- */
.search-section {
    width: 100%;
    padding: 0 40px;
}

.search-bar {
    display: flex;
    gap: 10px;

}

.search-bar input {
    flex: 1;
    padding: 12px 20px;
    background: rgba(255, 255, 255, 0.2);
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 25px;
    font-size: 1rem;
    color: #333;
    outline: none;
}

.search-btn {
    padding: 0 25px;
    background: #333;
    color: white;
    border: none;
    border-radius: 25px;
    font-weight: 600;
    cursor: pointer;
}

/* --- 메인 콘텐츠 영역 --- */
.music-selection-area {
    flex: 1;
    display: flex;
    width: 100%;
    gap: 20px;
    padding: 10px 40px;
    box-sizing: border-box;
    min-height: 0;
    overflow: hidden; 
}

.selection-preview, .search-results {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    padding: 20px;
    box-sizing: border-box;
}

.search-results {
    flex: 1.2;
    background: rgba(255, 255, 255, 0.15);
}

.sub-title {
    font-size: 1rem;
    color: #555;
    margin-bottom: 15px;
    font-weight: 600;
}

.selection-preview {
    flex: 1;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.preview-card {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.img-wrapper {
    width: 180px;
    height: 180px;
    border-radius: 15px;
    overflow: hidden;
    box-shadow: 0 10px 20px rgba(0,0,0,0.15);
    margin-bottom: 15px;
}

.preview-img { width: 100%; height: 100%; object-fit: cover; }
.p-title { font-weight: 700; font-size: 1.1rem; margin-bottom: 5px; color: #333; }
.p-artist { font-size: 0.9rem; color: #666; margin-bottom: 15px; }

.results-scroll {
    flex: 1;
    overflow-y: auto;
    padding-right: 5px;
}

/* --- 스크롤바 커스텀 --- */
.results-scroll::-webkit-scrollbar { width: 5px; }
.results-scroll::-webkit-scrollbar-track { background: transparent; }
.results-scroll::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.3);
    border-radius: 10px;
}

.empty-msg {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    color: rgba(0,0,0,0.4);
    line-height: 1.5;
}

/* --- 내비게이션 영역 (Step1, Step2와 동일) --- */
.nav-area {
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 12px; /* 20px → 12px */
    padding-top: 20px; /* 10px → 20px (Step1, Step2와 동일) */
}

.prev-btn, .next-btn {
    min-width: 100px; /* width: 140px → min-width: 100px */
    padding: 10px 16px; /* 15px → 10px 16px */
    border-radius: 30px;
    font-size: 1rem; /* 1.1rem → 1rem */
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s;
}

.prev-btn {
    background: rgba(255, 255, 255, 0.2);
    color: #333;
    border: 1.5px solid rgba(0, 0, 0, 0.1); /* 1px → 1.5px */
}

.prev-btn:hover {
    background: rgba(255, 255, 255, 0.35);
}

.next-btn {
    background: #333;
    color: white;
    border: none;
}

.next-btn:disabled {
    background: rgba(0, 0, 0, 0.2);
    cursor: not-allowed;
}

.next-btn:active:not(:disabled), .prev-btn:active {
    transform: scale(0.95);
}
</style>