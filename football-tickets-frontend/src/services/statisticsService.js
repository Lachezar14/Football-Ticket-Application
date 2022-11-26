import axios from "axios";

const matchesURL = 'http://localhost:8080/matches';
const ticketsURL = "http://localhost:8080/tickets";

const getNumberOfMatchesByTeam = async (id) => {
    try {
        return await axios.get(`${matchesURL}/team/${id}`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const getNumberOfTicketsByTeam = async (id) => {
    try {
        return await axios.get(`${ticketsURL}/team/${id}`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const getAVGSalesOfTicketsPerTeam = async (id) => {
    try {
        return await axios.get(`${ticketsURL}/team/${id}`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

export default {
    getNumberOfMatchesByTeam,
    getNumberOfTicketsByTeam,
    getAVGSalesOfTicketsPerTeam
}