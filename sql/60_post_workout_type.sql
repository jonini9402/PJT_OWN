DROP TABLE IF EXISTS post_workout_type;

CREATE TABLE post_workout_type (
    post_id INT NOT NULL,
    workout_type_id INT NOT NULL,
    PRIMARY KEY (post_id, workout_type_id),
    CONSTRAINT fk_post_workout_post FOREIGN KEY (post_id) 
        REFERENCES post(post_id) ON DELETE CASCADE,
    CONSTRAINT fk_post_workout_type FOREIGN KEY (workout_type_id) 
        REFERENCES workout_type(workout_type_id) ON DELETE CASCADE
);

-- 60_post_workout_type_dummy.sql
-- 게시물-운동타입 연결 더미 데이터

INSERT INTO post_workout_type (post_id, workout_type_id) 
VALUES
    -- 1. 12월 초 (1주차)
    (1, 1),  -- 헬스 (개인 기록 갱신)
    (2, 6),  -- 요가 (아침 루틴)
    (3, 2),  -- 러닝 (10km, 에미넴)
    (4, 1),  -- 헬스 (데드리프트)
    (5, 4),  -- 클라이밍 (벽타기)

    -- 2. 12월 중순 (2주차)
    (6, 7),  -- 복싱 (샌드백)
    (7, 8),  -- 댄스 (거울 속 움직임)
    (8, 3),  -- 사이클 (50km)
    (9, 5),  -- 등산 (정상)
    (10, 1), -- 헬스/홈트 (매트 위 운동)

    -- 3. 12월 말 (최근)
    (11, 6), -- 요가 (어깨, 목 풀기)
    (12, 2), -- 러닝 (인터벌)
    (13, 6), -- 요가/필라테스 (기구)
    (14, 7), -- 복싱 (체력 훈련)
    (15, 1), -- 헬스 (스쿼트)
    (16, 8), -- 댄스 (안무 마스터)
    (17, 4), -- 클라이밍 (볼더링 V5)
    (18, 3), -- 사이클 (한강 노을)
    (19, 5), -- 등산 (겨울 북한산)

    -- 4. 오늘 (12월 23일)
    (20, 1); -- 헬스 (벤치프레스)