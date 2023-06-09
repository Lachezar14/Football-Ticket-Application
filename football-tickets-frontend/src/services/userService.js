﻿import axios from "axios";
import authHeader from "./authHeader";

const usersURL = "http://localhost:8080/users";

//these methods that do not catch errors here, catch them in the pages and are displayed to the user
//this is made so the user knows what is the error and can correct it
const makeAdmin = (object) => {
    return axios.put(`${usersURL}/admin`, object)
}

const getAllUsers = () => {
    try {
        return axios.get(`${usersURL}/`);
    } catch (error) {
        console.log('Error while calling getUsers services ', error);
    }
}

const getUserByEmail = async (email) => {
    try {
       return await axios.get(`${usersURL}/email/${email}`, { headers: authHeader() });
    } catch (error) {
        console.log('Error while calling getUser services ', error);
    }
}

const updateUser = (updateRequest) => {
    return axios.put(`${usersURL}/${updateRequest.id}`, updateRequest, { headers: authHeader() });
}

const updatePassword = (object) => {
    return axios.put(`${usersURL}/new-password`, object, { headers: authHeader() });
}

export default {
    makeAdmin,
    getUserByEmail,
    updateUser,
    updatePassword,
    getAllUsers
}