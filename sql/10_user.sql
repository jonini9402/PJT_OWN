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
    ('김도현', '근손실오기전에', 'dohyun.kim@email.com', '$2a$10$abcdefghijklmnopqrstuv', '/src/assets/images/avatar1.png', 2, 15, true),
    ('이지아', '숨쉬기운동만20년', 'jiah.lee@email.com', '$2a$10$bcdefghijklmnopqrstuvw', '/src/assets/images/avatar5.png', 1, 8, true),
    ('박민수', '한강러닝크루회장', 'minsu.park@email.com', '$2a$10$cdefghijklmnopqrstuvwx', '/src/assets/images/avatar3.png', 3, 45, true),
    ('정수진', '볼더링꿈나무', 'sujin.jung@email.com', '$2a$10$defghijklmnopqrstuvwxy', '/src/assets/images/avatar4.png', 1, 3, true),
    ('최동현', '언더아머단속반', 'donghyun.choi@email.com', '$2a$10$efghijklmnopqrstuvwxyz', '/src/assets/images/avatar6.png', 3, 32, true),
    ('강민지', '스트릿댄서', 'minji.kang@email.com', '$2a$10$fghijklmnopqrstuvwxyza', '/src/assets/images/avatar5.png', 2, 12, true),
    ('윤서준', '따릉이폭주족', 'seojun.yoon@email.com', '$2a$10$ghijklmnopqrstuvwxyzab', '/src/assets/images/avatar1.png', 2, 18, true),
    ('임하은', '벽타는고양이', 'haeun.lim@email.com', '$2a$10$hijklmnopqrstuvwxyzabc', '/src/assets/images/avatar2.png', 1, 5, true),
    ('송지훈', '헬스장지박령', 'jihoon.song@email.com', '$2a$10$ijklmnopqrstuvwxyzabcd', '/src/assets/images/avatar3.png', 3, 50, true),
    ('한소희', '바른자세', 'sohee.han@email.com', '$2a$10$jklmnopqrstuvwxyzabcde','/src/assets/images/avatar5.png', 2, 10, true),
    ('정재훈', '득근득근거려', 'jh.jung@email.com', '$2a$10$abcdefghijklmnopqrstuv', '/src/assets/images/avatar1.png', 3, 42, true),
    ('김지영', '거북목교정중', 'jy.kim@email.com', '$2a$10$bcdefghijklmnopqrstuvw', '/src/assets/images/avatar2.png', 2, 14, true),
    ('이현우', '이제막런린이', 'hw.lee@email.com', '$2a$10$cdefghijklmnopqrstuvwx', '/src/assets/images/avatar3.png', 1, 4, true),
    ('박수연', '핵주먹수연', 'sy.park@email.com', '$2a$10$defghijklmnopqrstuvwxy', '/src/assets/images/avatar4.png', 2, 19, true),
    ('최준호', '산신령', 'jh.choi@email.com', '$2a$10$efghijklmnopqrstuvwxyz', '/src/assets/images/avatar6.png', 3, 55, true);
