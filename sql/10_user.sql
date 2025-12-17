DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
user_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL,
nickname VARCHAR(20) UNIQUE NOT NULL,
email VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
profile_img VARCHAR(255),
tier_level INT NOT NULL DEFAULT 1,
user_post_count INT NOT NULL DEFAULT 0,
deleted_at TIMESTAMP NULL, 
status BOOLEAN NOT NULL DEFAULT true
);
-- 10_user_dummy.sql
-- 사용자 더미 데이터

INSERT INTO users (name, nickname, email, password, profile_img, tier_level, user_post_count, status) 
VALUES
    ('김철수', 'ironman123', 'chulsoo.kim@email.com', '$2a$10$abcdefghijklmnopqrstuv', 'https://example.com/profile1.jpg', 3, 15, true),
    ('이영희', 'yogaqueen', 'younghee.lee@email.com', '$2a$10$bcdefghijklmnopqrstuvw', 'https://example.com/profile2.jpg', 2, 8, true),
    ('박민수', 'runner_park', 'minsu.park@email.com', '$2a$10$cdefghijklmnopqrstuvwx', 'https://example.com/profile3.jpg', 4, 25, true),
    ('정수진', 'climb_sujin', 'sujin.jung@email.com', '$2a$10$defghijklmnopqrstuvwxy', 'https://example.com/profile4.jpg', 1, 3, true),
    ('최동현', 'boxer_choi', 'donghyun.choi@email.com', '$2a$10$efghijklmnopqrstuvwxyz', 'https://example.com/profile5.jpg', 5, 42, true),
    ('강민지', 'dance_minji', 'minji.kang@email.com', '$2a$10$fghijklmnopqrstuvwxyza', 'https://example.com/profile6.jpg', 2, 12, true),
    ('윤서준', 'cycle_yoon', 'seojun.yoon@email.com', '$2a$10$ghijklmnopqrstuvwxyzab', 'https://example.com/profile7.jpg', 3, 18, true),
    ('임하은', 'mountain_haeun', 'haeun.lim@email.com', '$2a$10$hijklmnopqrstuvwxyzabc', 'https://example.com/profile8.jpg', 1, 5, true),
    ('송지훈', 'fitness_song', 'jihoon.song@email.com', '$2a$10$ijklmnopqrstuvwxyzabcd', 'https://example.com/profile9.jpg', 4, 30, true),
    ('한소희', 'yoga_sohee', 'sohee.han@email.com', '$2a$10$jklmnopqrstuvwxyzabcde', 'https://example.com/profile10.jpg', 2, 10, true);

