import axios from 'axios';


const matchesURL = 'http://localhost:8080/matches';


const getMatches = async () => {
    try {
        return axios.get(`${matchesURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers service ', error);
    }
}

const saveMatch = async (match) => {
    try{
        return await axios.post(`${matchesURL}/add`, match);
    }catch (error) {
        console.log('Error while calling saveMatch service ', error);
    }
}

const updateMatch = async (match) => {
    try{
        return await axios.put(`${matchesURL}/${match.id}`, match);
    }catch (error) {
        console.log('Error while calling updateMatch service ', error);
    }
}

const deleteMatch = async (id) => {
    try{
        return await axios.delete(`${matchesURL}/${id}`);
    }catch (error) {
        console.log('Error while calling deleteMatch service ', error);
    }
}

export default {
    getMatches,
    saveMatch,
    deleteMatch,
    updateMatch
}