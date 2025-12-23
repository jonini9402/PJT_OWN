import instance from './index';

// 게시글 생성
export const createPost = (postData) => {
    return instance.post('/post',postData);
}

// 게시글 전체 조회 (무한스크롤 적용) / params에 마지막포스트 아이디, 크기를 보내서 무한스크롤에 사용
export const getPosts = (params) => {
    return instance.get('/post', { params });
}

// 특정 유저의 게시글 조회
export const getUserPosts = (userId, params) => {
    return instance.get(`/post/users/${userId}`, { params });
}

// 특정 유저가 좋아요한 게시글 조회
export const getLikedPosts = (userId, params) => {
    return instance.get(`/post/likes/${userId}`, { params });
}

// 특정 유저가 북마크한 게시글 조회
export const getBookmarkedPosts = (userId, params) => {
    return instance.get(`/post/bookmarks/${userId}`, { params });
}

// 게시글 수정
export const updatePost = (postId, postData) => {
    return instance.put(`/post/${postId}`, postData);
}

// 게시글 삭제
export const deletePost = (postId) => {
    return instance.delete(`/post/${postId}`);
}

// 노래 랭킹 함수 호출
export const getMusicRank = () => {
    return instance.get('/post/rank');
}

// 이번주 운동횟수 함수 호출
export const getWeeklyCount = () => {
    return instance.get('/post/week-count');
}