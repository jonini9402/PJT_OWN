import axios from 'axios';

const spotifyApi = axios.create({
    baseURL: 'https://api.spotify.com/v1',                                                                                       
});

export const searchSpotify = (query, token) => {
    const cleanToken = token ? token.trim() : '';

    return spotifyApi.get('/search', {
        params: {
            q: query,
            type: 'track',
            limit: 10
        },
        headers: {
            Authorization: `Bearer ${cleanToken}`
        }
    });
};

import instance from './index';
export const saveMusicToDb = (musicData) => {
    return instance.post('/music/save', musicData);
}