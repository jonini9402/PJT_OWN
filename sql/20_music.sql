DROP TABLE IF EXISTS music;

CREATE TABLE IF NOT EXISTS music (
music_id INT PRIMARY KEY AUTO_INCREMENT,
spotify_track_id VARCHAR(100) NOT NULL,
title VARCHAR(100) NOT NULL,
artist VARCHAR(100) NOT NULL,
album_img VARCHAR(255) NOT NULL,
duration int NOT NULL,
preview_url VARCHAR(255) NOT NULL
);

-- 20_music_dummy.sql
-- 음악 더미 데이터

INSERT INTO music (spotify_track_id, title, artist, album_img, duration, preview_url) 
VALUES
    ('3n3Ppam7vgaVa1iaRUc9Lp', 'Eye of the Tiger', 'Survivor', 'https://i.scdn.co/image/ab67616d0000b273001.jpg', 246, 'https://p.scdn.co/mp3-preview/001'),
    ('0VjIjW4GlUZAMYd2vXMi3b', 'Blinding Lights', 'The Weeknd', 'https://i.scdn.co/image/ab67616d0000b273002.jpg', 200, 'https://p.scdn.co/mp3-preview/002'),
    ('5ChkMS8OtdzJeqyybCc9R5', 'Lose Yourself', 'Eminem', 'https://i.scdn.co/image/ab67616d0000b273003.jpg', 326, 'https://p.scdn.co/mp3-preview/003'),
    ('2takcwOaAZWiXQijPHIx7B', 'Till I Collapse', 'Eminem', 'https://i.scdn.co/image/ab67616d0000b273004.jpg', 297, 'https://p.scdn.co/mp3-preview/004'),
    ('1x5sYLZiu9r5E43kMlt9f8', "Can't Hold Us", 'Macklemore', 'https://i.scdn.co/image/ab67616d0000b273005.jpg', 258, 'https://p.scdn.co/mp3-preview/005'),
    ('60nZcImufyMA1MKQY3dcCH', 'Stronger', 'Kanye West', 'https://i.scdn.co/image/ab67616d0000b273006.jpg', 311, 'https://p.scdn.co/mp3-preview/006'),
    ('4VqPOruhp5EdPBeR92t6lQ', 'Uptown Funk', 'Mark Ronson', 'https://i.scdn.co/image/ab67616d0000b273007.jpg', 269, 'https://p.scdn.co/mp3-preview/007'),
    ('7qiZfU4dY1lWllzX7mPBI', 'Shape of You', 'Ed Sheeran', 'https://i.scdn.co/image/ab67616d0000b273008.jpg', 233, 'https://p.scdn.co/mp3-preview/008'),
    ('0DiWol3AO6WpXZgp0goxAV', 'One Dance', 'Drake', 'https://i.scdn.co/image/ab67616d0000b273009.jpg', 173, 'https://p.scdn.co/mp3-preview/009'),
    ('3qiyyUfYe7CRYLucrPmulD', 'Levels', 'Avicii', 'https://i.scdn.co/image/ab67616d0000b273010.jpg', 203, 'https://p.scdn.co/mp3-preview/010'),
    ('1TfqLAPs4K3s2rJMoCokcS', 'HUMBLE.', 'Kendrick Lamar', 'https://i.scdn.co/image/ab67616d0000b273011.jpg', 177, 'https://p.scdn.co/mp3-preview/011'),
    ('2Fxmhks0bxGSBdJ92vM42m', 'bad guy', 'Billie Eilish', 'https://i.scdn.co/image/ab67616d0000b273012.jpg', 194, 'https://p.scdn.co/mp3-preview/012'),
    ('6habFhsOp2NvshLv26DqMb', 'Super Bass', 'Nicki Minaj', 'https://i.scdn.co/image/ab67616d0000b273013.jpg', 200, 'https://p.scdn.co/mp3-preview/013'),
    ('7MXVkk9YMctZqd1Srtv4MB', 'Starboy', 'The Weeknd', 'https://i.scdn.co/image/ab67616d0000b273014.jpg', 230, 'https://p.scdn.co/mp3-preview/014'),
    ('0sf0qMayYFAu2aKvlfSeG', 'Rockstar', 'Post Malone', 'https://i.scdn.co/image/ab67616d0000b273015.jpg', 218, 'https://p.scdn.co/mp3-preview/015');