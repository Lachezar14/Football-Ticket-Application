import axios from "axios";

const usersURL = "http://localhost:8080/api";

const makeAdmin = (object) => {
    try{
        return axios.put(`${usersURL}/admin`, object)
    } catch (error) {
        console.log('Error while calling makeAdmin service ', error);
    }
}

const getAllUsers = () => {
    try {
        return axios.get(`${usersURL}/users`);
    } catch (error) {
        console.log('Error while calling getUsers service ', error);
    }
}

const getUserByEmail = (email) => {
    try {
        return axios.get(`${usersURL}/email/${email}`);
    } catch (error) {
        console.log('Error while calling getUser service ', error);
    }
}

const updateUser = (updateRequest) => {
    try{
        return axios.put(`${usersURL}/update`, updateRequest)
    } catch (error) {
        console.log('Error while calling updateUser service ', error);
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
    makeAdmin,
    getUserByEmail,
    updateUser,
    updatePassword,
    getAllUsers
}