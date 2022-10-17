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

export default {
    getMatches,
    saveMatch
}