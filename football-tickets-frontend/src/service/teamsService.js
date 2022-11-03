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

const updateTeam = async (team) => {
    try{
        return await axios.put(`${teamsURL}/${team.id}`, team);
    }catch (error) {
        console.log('Error while calling updateTeam service ', error);
    }
}

const deleteTeam = async (id) => {
    try{
        return await axios.delete(`${teamsURL}/${id}`);
    }catch (error) {
        console.log('Error while calling deleteTeam service ', error);
    }
}

export default {
    getTeams,
    saveTeam,
    deleteTeam,
    updateTeam
}