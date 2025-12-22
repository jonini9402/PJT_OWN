import instance from './index';

// 좋아요 추가
export const addLike = (postId, userId) => {
  return instance.post(`/posts/${postId}/likes`, null, { params: { userId } });
};

// 좋아요 취소
export const removeLike = (postId, userId) => {
  return instance.delete(`/posts/${postId}/likes`, { params: { userId } });
};

// 좋아요 개수 조회
export const getLikeCount = (postId) => {
  return instance.get(`/posts/${postId}/likes/count`);
};

// 좋아요 여부 확인
export const checkLiked = (postId, userId) => {
  return instance.get(`/posts/${postId}/likes/exists`, { params: { userId } });
};