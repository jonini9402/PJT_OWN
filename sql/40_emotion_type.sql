DROP TABLE IF EXISTS emotion_type;

CREATE TABLE IF NOT EXISTS emotion_type (
emotion_type_id INT PRIMARY KEY AUTO_INCREMENT,
emotion_name VARCHAR(50) NOT NULL UNIQUE
);
-- 40_emotion_type_dummy.sql
-- 감정 타입 더미 데이터

INSERT INTO emotion_type (emotion_name) 
VALUES
    ('행복'),
    ('뿌듯함'),
    ('자신감'),
    ('열정'),
    ('평온'),
    ('활기참'),
    ('집중'),
    ('성취감'),
    ('힐링'),
    ('도전적');