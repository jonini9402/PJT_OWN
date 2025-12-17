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
    -- 게시물 1: 헬스
    (1, 1),
    -- 게시물 2: 요가
    (2, 6),
    -- 게시물 3: 러닝
    (3, 2),
    -- 게시물 4: 헬스
    (4, 1),
    -- 게시물 5: 클라이밍
    (5, 4),
    -- 게시물 6: 복싱
    (6, 7),
    -- 게시물 7: 댄스
    (7, 8),
    -- 게시물 8: 사이클
    (8, 3),
    -- 게시물 9: 등산
    (9, 5),
    -- 게시물 10: 헬스
    (10, 1),
    -- 게시물 11: 요가
    (11, 6),
    -- 게시물 12: 러닝
    (12, 2),
    -- 게시물 13: 요가
    (13, 6),
    -- 게시물 14: 복싱
    (14, 7),
    -- 게시물 15: 헬스
    (15, 1),
    -- 게시물 16: 댄스
    (16, 8),
    -- 게시물 17: 클라이밍
    (17, 4),
    -- 게시물 18: 사이클
    (18, 3),
    -- 게시물 19: 등산
    (19, 5),
    -- 게시물 20: 헬스
    (20, 1),
    -- 복합 운동 예시 (한 게시물에 여러 운동 태그)
    (1, 7),  -- 게시물 1에 복싱도 추가
    (3, 6),  -- 게시물 3에 요가도 추가
    (10, 2); -- 게시물 10에 러닝도 추가