import axios from 'axios';


const matchesURL = 'http://localhost:8080/matches';


const getMatches = async () => {
    try {
        return await axios.get(`${matchesURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers service ', error);
    }
}

export default {
    getMatches
}