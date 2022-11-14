import axios from "axios";

const ticketsURL = "http://localhost:8080/tickets";

const getTicketsByBuyer = (buyer) => {
  try {
    return axios.get(`${ticketsURL}/user/${buyer.id}`);
  } catch (error) {
    console.log('Error while calling getTicketsByBuyer services ', error);
  }
}

const buyTickets = async (ticket) => {
  try {
    return await axios.post(`${ticketsURL}/add`, ticket);
  } catch (error) {
    console.log("Error while calling buyTickets services ", error);
  }
}

export default {
    buyTickets,
    getTicketsByBuyer,
};