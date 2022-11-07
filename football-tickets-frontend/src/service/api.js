import axios from 'axios';
import authHeader from "./authHeader";
import jwt_decode from "jwt-decode";
import {useState} from "react";
import userService from "./userService";

const usersUrl = 'http://localhost:8080/api';

const register = async (data) => {
    return axios.post(`${usersUrl}/register`, data, {headers: {'Content-Type': 'application/json'}});
}

/**
 * @typedef {Object} UserLoginDto
 * @property {string} access_token
 * @property {string} refresh_token
 */

const login = (data) => {
    return axios
        .post(`${usersUrl}/login` , data)
        .then((response) => {
            //console.log(response);
            if (response.data.access_token) {
                const info = jwt_decode(response.data.access_token);
                userService.getUserByEmail(info.sub).then((response) => {
                    //localStorage.setItem("user", JSON.stringify(response.data));
                    sessionStorage.setItem("user", JSON.stringify(response.data));
                    //console.log(response.data);})
            })} else {
                console.warn("No tokens in response");
            }
            return response.data.access_token;
        });
}

{/*
const login = (data) => {
    return axios
        .post(`${usersUrl}/login` , data)
        .then((response) => {
            console.log(response);
            if (response.data.access_token) {
                //console.log(response.data);
                //localStorage.setItem("user", JSON.stringify(response.data));
            } else {
                console.warn("No tokens in response");
            }
            return response.data;
        });
};*/}

/**
 * @returns {UserLoginDto}
 */
const getCurrentUser = () => {
    //return JSON.parse(localStorage.getItem("user")) || null;
    return JSON.parse(sessionStorage.getItem("user")) || null;
};

const isAuthenticated = () => {
    if (getCurrentUser() === null) {
        return false;
    }
    return true;
}

const sessionExpired = () => {

    let user = getCurrentUser();
    if (user !== "") {
        let tokens = user.tokens;
        let decodedRefresh = jwt_decode(tokens.refresh_token);
        if (decodedRefresh.exp * 1000 < Date.now()) {
            localStorage.clear();
            return true;
        }
    }
    return false;
}

{/*const getUserByEmail = async (email) => {
    email = email || '';
    try {
        return await axios.get(`${usersUrl}/email/${email}`);
    } catch (error) {
        console.log('Error while calling getUser service ', error);
    }*/}

export const getUsers = async (id) => {
    id = id || '';
    try {
        return await axios.get(`${usersUrl}`);
    } catch (error) {
        console.log('Error while calling getUsers service ', error);
    }
}

export const addUser = async (user) => {
    return await axios.post(`${usersUrl}`, user);
}

export const deleteUser = async (id) => {
    return await axios.delete(`${usersUrl}/${id}`);
}

export const editUser = async (id, user) => {
    return await axios.put(`${usersUrl}/${id}`, user)
}

export default{
    register,
    login,
    getCurrentUser,
    isAuthenticated,
    sessionExpired
}