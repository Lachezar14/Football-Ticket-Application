import axios from 'axios';


const teamsURL = 'http://localhost:8080/teams';

const getTeams = () => {
    try {
        return axios.get(`${teamsURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers service ', error);
    }
}

const saveTeam = async (team) => {
    try{
    return await axios.post(`${teamsURL}/add`, team);
    }catch (error) {
        console.log('Error while calling saveTeam service ', error);
    }
}

export default {
    getTeams,
    saveTeam
}