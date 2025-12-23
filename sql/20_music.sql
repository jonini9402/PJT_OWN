DROP TABLE IF EXISTS music;

CREATE TABLE IF NOT EXISTS music (
music_id INT PRIMARY KEY AUTO_INCREMENT,
spotify_track_id VARCHAR(100) NOT NULL,
title VARCHAR(100) NOT NULL,
artist VARCHAR(100) NOT NULL,
album_img VARCHAR(255) NOT NULL,
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
    ('3bidbhpOYeV4knp8AIu8Xn', 'Can\'t Hold Us', 'Macklemore', 'https://i.scdn.co/image/ab67616d0000b273266850d2446d3e74e402095f', 258, NULL),
    ('0j2T0R9dR9qdJYsB7ciXhf', 'Stronger', 'Kanye West', 'https://i.scdn.co/image/ab67616d0000b27326f7f19c7f0381e56156c94a', 311, NULL),
    ('32OlwWuMpZ6b0aN2RZOeMS', 'Uptown Funk', 'Mark Ronson', 'https://i.scdn.co/image/ab67616d0000b273f7692484c2db1c5e9336d8d9', 269, NULL),
    ('7qiZfU4dY1lWllzX7mPBI', 'Shape of You', 'Ed Sheeran', 'https://i.scdn.co/image/ab67616d0000b273ba5db46f4b838ef6027e6f96', 233, NULL),
    ('1zi7xx7UVEFkmKfv06H8x0', 'One Dance', 'Drake', 'https://i.scdn.co/image/ab67616d0000b27393aeec22f9602e3b2e987114', 173, NULL),
    ('5UqCQaDshqbIk3pkhy4Pjg', 'Levels', 'Avicii', 'https://i.scdn.co/image/ab67616d0000b273347f8796839353982794eb09', 203, NULL),
    ('7KXjTSCq5nL1LoYtL7XAwS', 'HUMBLE.', 'Kendrick Lamar', 'https://i.scdn.co/image/ab67616d0000b273cdb645498cd3d9a2163344af', 177, NULL),
    ('2Fxmhks0bxGSBdJ92vM42m', 'bad guy', 'Billie Eilish', 'https://i.scdn.co/image/ab67616d0000b27350a3147b4edd7701a439184b', 194, NULL),
    ('3hlksXnvbKogFdPbpO9vel', 'Super Bass', 'Nicki Minaj', 'https://i.scdn.co/image/ab67616d0000b273aa7d2641af0fa4c1f76fafbf', 200, NULL),
    ('7MXVkk9YMctZqd1Srtv4MB', 'Starboy', 'The Weeknd', 'https://i.scdn.co/image/ab67616d0000b2734718e28d24227b9dc7491763', 230, NULL);