import axios from "axios";

const usersURL = "http://localhost:8080/api";

const getUserByEmail = (email) => {
    try {
        return axios.get(`${usersURL}/email/${email}`);
    } catch (error) {
        console.log('Error while calling getUser service ', error);
    }
}

const updatePassword = (object) => {
    try{
        return axios.put(`${usersURL}/new-password`, object)
    } catch (error) {
        console.log('Error while calling newPassword service ', error);
    }
}

export default {
    getUserByEmail,
    updatePassword
}