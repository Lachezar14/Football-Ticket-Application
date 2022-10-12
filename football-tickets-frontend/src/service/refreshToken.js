import axios from "axios";
import jwt_decode from "jwt-decode";

const API_URL = "http://localhost:8080/api";

function refreshToken() {
    let user = JSON.parse(localStorage.getItem('user')) || "";
    if (user !== "") {
        let tokens = user.tokens;
        let decodedAccess = jwt_decode(tokens.access_token);
        let decodedRefresh = jwt_decode(tokens.refresh_token);

        if (decodedAccess.exp * 1000 < Date.now()) {
            if (decodedRefresh.exp * 1000 < Date.now()) {
                console.log("expired refresh");
                return null;
            } else {
                return axios
                    .get(API_URL + "token/refresh", {headers: {Authorization: 'Bearer ' + tokens.refresh_token}})
                    .then(
                        (response) => {
                            user.tokens = response.data;
                            localStorage.setItem("user", JSON.stringify(user));
                            return response.data.access_token;
                        }
                    );
            }
        }
    }
    return null;

}

export default refreshToken;