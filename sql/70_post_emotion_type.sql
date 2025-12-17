DROP TABLE IF EXISTS post_emotion_type;

CREATE TABLE post_emotion_type (
    post_id INT NOT NULL,
    emotion_type_id INT NOT NULL,
    PRIMARY KEY (post_id, emotion_type_id),
    CONSTRAINT fk_post_emotion_post FOREIGN KEY (post_id) 
        REFERENCES post(post_id) ON DELETE CASCADE,
    CONSTRAINT fk_post_emotion_type FOREIGN KEY (emotion_type_id) 
        REFERENCES emotion_type(emotion_type_id) ON DELETE CASCADE
);

-- 70_post_emotion_type_dummy.sql
-- 게시물-감정타입 연결 더미 데이터

INSERT INTO post_emotion_type (post_id, emotion_type_id) 
VALUES
    -- 게시물 1: 뿌듯함, 성취감
    (1, 2),
    (1, 8),
    -- 게시물 2: 평온, 힐링
    (2, 5),
    (2, 9),
    -- 게시물 3: 활기찬, 열정
    (3, 6),
    (3, 4),
    -- 게시물 4: 성취감, 자신감
    (4, 8),
    (4, 3),
    -- 게시물 5: 도전적, 뿌듯함
    (5, 10),
    (5, 2),
    -- 게시물 6: 활기찬, 열정
    (6, 6),
    (6, 4),
    -- 게시물 7: 행복, 활기찬
    (7, 1),
    (7, 6),
    -- 게시물 8: 평온, 행복
    (8, 5),
    (8, 1),
    -- 게시물 9: 성취감, 행복
    (9, 8),
    (9, 1),
    -- 게시물 10: 뿌듯함, 평온
    (10, 2),
    (10, 5),
    -- 게시물 11: 힐링, 평온
    (11, 9),
    (11, 5),
    -- 게시물 12: 도전적, 자신감
    (12, 10),
    (12, 3),
    -- 게시물 13: 힐링, 평온
    (13, 9),
    (13, 5),
    -- 게시물 14: 뿌듯함, 활기찬
    (14, 2),
    (14, 6),
    -- 게시물 15: 성취감, 뿌듯함
    (15, 8),
    (15, 2),
    -- 게시물 16: 행복, 열정
    (16, 1),
    (16, 4),
    -- 게시물 17: 성취감, 도전적
    (17, 8),
    (17, 10),
    -- 게시물 18: 평온, 행복
    (18, 5),
    (18, 1),
    -- 게시물 19: 힐링, 평온
    (19, 9),
    (19, 5),
    -- 게시물 20: 자신감, 성취감
    (20, 3),
    (20, 8);