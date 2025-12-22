// import axios from 'axios'

// const BASE_URL = 'http://localhost:8080'

import instance from './index'; // index.js 에 공통적으로 base_url, credential 등 정의해둔 인스턴스

//로그인
export function login(email, password) {
  return instance.post(`/user/login`, {
    email: email,
    password: password
  });
}

export function signup(userData){
    return instance.post(`/user/signup`, userData);
}
// 이메일 중복 체크
export function checkEmailDuplicate(email) {
  return instance.get(`/user/check-email`, {
    params: { email}
  });
}

// 닉네임 중복 체크 
export function checkNicknameDuplicate(nickname) {
  return instance.get(`/user/check-nickname`, {
    params: { nickname }
  });
}

// 프로필 조회
export function getUserProfile(userId) {
  return instance.get(`/user/${userId}`);
}

// 내 정보 조회 (테스트용? 백엔드에 있어서 일단 넣어놓음)
export function getMyProfile() {
  return instance.get(`/user/me`);
}
// 프로필 수정
export function updateUserProfile(userId, userData) {
  return instance.put(`/user/${userId}`, userData);
}

// 회원 탈퇴
export function deleteUser(userId) {
  return instance.delete(`/user/${userId}`);
}

// 로그아웃
export function logout() {
  return instance.post(`/user/logout`);
}