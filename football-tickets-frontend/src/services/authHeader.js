const authHeader = () => {

    const access_token = JSON.parse(sessionStorage.getItem('token'));
    if (access_token) {
        return {
            Authorization: 'Bearer ' + access_token,
            'Content-Type': 'application/json',
        };
    }
    return {};
}

export default authHeader;