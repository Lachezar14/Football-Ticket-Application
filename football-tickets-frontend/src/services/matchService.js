import axios from 'axios';


const matchesURL = 'http://localhost:8080/matches';


const getMatches = async () => {
    try {
        return await axios.get(`${matchesURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const saveMatch = (match) => {
    return axios.post(`${matchesURL}/add`, match);
}

const updateMatch = (match) => {
    return axios.put(`${matchesURL}/${match.id}`, match);

}

const deleteMatch = (id) => {
    return axios.delete(`${matchesURL}/${id}`);
}

export default {
    getMatches,
    saveMatch,
    deleteMatch,
    updateMatch
}