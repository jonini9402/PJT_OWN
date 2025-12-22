import instance from './index';

// 북마크 추가
export const addBookmark = (postId, userId) => {
  return instance.post(`/posts/${postId}/bookmarks`, null, { params: { userId } });
};

// 북마크 취소
export const removeBookmark = (postId, userId) => {
  return instance.delete(`/posts/${postId}/bookmarks`, { params: { userId } });
};

// 북마크 개수 조회
export const getBookmarkCount = (postId) => {
  return instance.get(`/posts/${postId}/bookmarks/count`);
};

// 북마크 여부 확인
export const checkBookmarked = (postId, userId) => {
  return instance.get(`/posts/${postId}/bookmarks/exists`, { params: { userId } });
};