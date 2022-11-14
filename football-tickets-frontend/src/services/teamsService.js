import axios from 'axios';


const teamsURL = 'http://localhost:8080/teams';

const getTeams = () => {
    try {
        return axios.get(`${teamsURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const saveTeam = (team) => {
    return axios.post(`${teamsURL}/add`, team);
}

const updateTeam = (team) => {
    return axios.put(`${teamsURL}/update`, team);
}

const deleteTeam = (id) => {
    return axios.delete(`${teamsURL}/${id}`);
}

export default {
    getTeams,
    saveTeam,
    deleteTeam,
    updateTeam
}