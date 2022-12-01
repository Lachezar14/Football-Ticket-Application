import axios from "axios";
import authHeader from "./authHeader";

const usersURL = "http://localhost:8080/api";

//these methods that do not catch errors here, catch them in the pages and are displayed to the user
//this is made so the user knows what is the error and can correct it
const makeAdmin = (object) => {
    return axios.put(`${usersURL}/admin`, object)
}

const getAllUsers = () => {
    try {
        return axios.get(`${usersURL}/users`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const getUserByEmail = (email) => {
    try {
        return axios.get(`${usersURL}/email/${email}`, { headers: authHeader() });
    } catch (error) {
        console.log('Error while calling getUser services ', error);
    }
}

{/* TODO uncomment in the authenticate issue
const getUserByEmail = (email) => {
    try {
        return axios.get(`${usersURL}/email/${email}`, { headers: authHeader() });
    } catch (error) {
        console.log('Error while calling getUser services ', error);
    }
}*/}

const updateUser = (updateRequest) => {
    return axios.put(`${usersURL}/update`, updateRequest)
}

const updatePassword = (object) => {
    return axios.put(`${usersURL}/new-password`, object)
}

export default {
    makeAdmin,
    getUserByEmail,
    updateUser,
    updatePassword,
    getAllUsers
}