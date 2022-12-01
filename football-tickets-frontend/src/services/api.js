import axios from 'axios';
import jwt_decode from "jwt-decode";
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

const login = async (data) => {
    let response = await axios.post(`${usersUrl}/login` , data);
    
    if (response.data.access_token) {
        sessionStorage.setItem("token", JSON.stringify(response.data.access_token));
        const info = jwt_decode(response.data.access_token);
        let userResponse = await userService.getUserByEmail(info.sub);
        sessionStorage.setItem("user", JSON.stringify(userResponse.data));
    } else {
        console.warn("No tokens in response");
    }
    
    return response.data.access_token;
}

/**
 * @returns {UserLoginDto}
 */
const getCurrentUser = () => {
    return JSON.parse(sessionStorage.getItem("user")) || null;
};

const isAuthenticated = () => {
    if (getCurrentUser() === null) {
        return false;
    }
    return true;
}

{/*
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
*/}

export default{
    register,
    login,
    getCurrentUser,
    isAuthenticated,
}