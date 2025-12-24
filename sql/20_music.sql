DROP TABLE IF EXISTS music;

CREATE TABLE IF NOT EXISTS music (
music_id INT PRIMARY KEY AUTO_INCREMENT,
spotify_track_id VARCHAR(100) NOT NULL,
title VARCHAR(100) NOT NULL,
artist VARCHAR(100) NOT NULL,
album_img VARCHAR(500) NOT NULL,
duration int NOT NULL,
preview_url VARCHAR(255) NULL
);

-- 20_music_dummy.sql
-- 음악 더미 데이터

INSERT INTO music (spotify_track_id, title, artist, album_img, duration, preview_url) 
VALUES
    ('2KH16WveTQWT6KOG9Rg6e2', 'Eye of the Tiger', 'Survivor', 'https://i.scdn.co/image/ab67616d0000b273f4a2ccbe20d6d52f16816812', 246, NULL),
    ('5ChkMS8OtdzJeqyybCc9R5', 'Lose Yourself', 'Eminem', 'https://i.scdn.co/image/ab67616d0000b2736ca5c90113b30c3c43ffb8f4', 326, NULL),
    ('4xkOaSrkexMciUUogZKVTS', 'Till I Collapse', 'Eminem', 'https://i.scdn.co/image/ab67616d0000b2736ca5c90113b30c3c43ffb8f4', 297, NULL),
    ('3bidbhpOYeV4knp8AIu8Xn', 'Can\'t Hold Us', 'Macklemore', 'https://i.scdn.co/image/ab67616d0000b273938f1c0cc7d36a671ed226a7', 258, NULL),
    ('0j2T0R9dR9qdJYsB7ciXhf', 'Stronger', 'Kanye West', 'https://i.scdn.co/image/ab67616d0000b27326f7f19c7f0381e56156c94a', 311, NULL),
    ('32OlwWuMpZ6b0aN2RZOeMS', 'Uptown Funk', 'Mark Ronson', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBJCCn1oHBNtb7JiVbDv0aTuDSKuXb5m6Gyg&s', 269, NULL),
    ('7qiZfU4dY1lWllzX7mPBI', 'Shape of You', 'Ed Sheeran', 'https://i.scdn.co/image/ab67616d0000b273ba5db46f4b838ef6027e6f96', 233, NULL),
    ('1zi7xx7UVEFkmKfv06H8x0', 'One Dance', 'Drake', 'https://i.scdn.co/image/ab67616d0000b2739416ed64daf84936d89e671c', 173, NULL),
    ('5UqCQaDshqbIk3pkhy4Pjg', 'Levels', 'Avicii', 'https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/67/38/43/67384338-9ed7-fc68-5927-93f1fcf4705d/11UMGIM36900.rgb.jpg/600x600bf-60.jpg', 203, NULL),
    ('7KXjTSCq5nL1LoYtL7XAwS', 'HUMBLE.', 'Kendrick Lamar', 'https://cdn-images.dzcdn.net/images/cover/7ce6b8452fae425557067db6e6a1cad5/1900x1900-000000-80-0-0.jpg', 177, NULL),
    ('2Fxmhks0bxGSBdJ92vM42m', 'bad guy', 'Billie Eilish', 'https://cdn-images.dzcdn.net/images/cover/6630083f454d48eadb6a9b53f035d734/500x500.jpg', 194, NULL),
    ('3hlksXnvbKogFdPbpO9vel', 'Super Bass', 'Nicki Minaj', 'https://i.scdn.co/image/ab67616d0000b273aa7d2641af0fa4c1f76fafbf', 200, NULL),
    ('7MXVkk9YMctZqd1Srtv4MB', 'Starboy', 'The Weeknd', 'https://i.scdn.co/image/ab67616d0000b2734718e2b124f79258be7bc452', 230, NULL);