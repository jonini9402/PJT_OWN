DROP TABLE IF EXISTS emotion_type;

CREATE TABLE IF NOT EXISTS emotion_type (
emotion_type_id INT PRIMARY KEY AUTO_INCREMENT,
emotion_name VARCHAR(50) NOT NULL UNIQUE
);
-- 40_emotion_type_dummy.sql
-- 감정 타입 더미 데이터

INSERT INTO emotion_type (emotion_name) 
VALUES
    ('고요한 근육'),
    ('릴렉스 러닝'),
    ('스트레칭 바이브'),
    ('여유로운 페이스'),
    ('고민 정리'),
    ('슬로우 그루브'),
    ('안정된 심박'),
    ('잔잔한 성취'),
    ('파워업'),
    ('새로운 시작'),
    ('스트레스 해소'),
    ('한계 돌파'),
    ('번개 템포'),
    ('불타는 세트'),
    ('비트 싱크 120%'),
    ('러시 아워 운동'),
    ('최강 플레이리스트'),
    ('터지는 엔도르핀'),
    ('체중목표 달성'),
    ('부드럽게 강함'),
    ('완벽한 플로우'),
    ('딱 맞는 텐션'),
    ('플레이리스트 굿 매치'),
    ('땀나는 행복'),
    ('템포 미쳤다(긍정)'),
    ('만족 100%'),
    ('리듬 타는 운동'),
    ('조금 지친 날'),
    ('살짝 루즈한 페이스'),
    ('실제 < 기대'),
    ('텐션 올리고 싶어'),
    ('마지막 세트 실패'),
    ('워밍업 부족'),
    ('플레이리스트 무한 스킵'),
    ('다음엔 더!');