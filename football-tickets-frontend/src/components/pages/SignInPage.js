
import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import api from "../../services/api";
import userService from "../../services/userService";
import jwt_decode from "jwt-decode";
import {FormHelperText} from "@mui/material";



const theme = createTheme();

export default function SignIn() {

    const [errorMessage, setErrorMessage] = useState("");
    const [Username, setUsername] = useState({username: "",});
    const [Password, setPassword] = useState({password: "",})
    let navigate = useNavigate();
    
    const onChangeUsername = e => {
        setUsername({
            ...Username,
            [e.target.name]: e.target.value,
        })
    }

    const onChangePassword = e => {
        setPassword({
            ...Password,
            [e.target.name]: e.target.value,
        })
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        api.login(data)
            .then(token => {
                const decoded = jwt_decode(token);
                console.log(decoded);
                if (decoded.role.some(role => role === 'USER')) {
                window.location.href = "/profile";
                }else if (decoded.role.some(role => role === 'ADMIN'))  {
                window.location.href = "/admin";
                }
            })
            .catch((err) => {
                console.error(err);
                setErrorMessage(err.response.data.error_message);
            });
        setUsername({username: ""});
        setPassword({password: ""});
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Email Address"
                            name="username"
                            autoComplete="username"
                            autoFocus
                            onChange={onChangeUsername}
                            value={Username.username}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            onChange={onChangePassword}
                            value={Password.password}
                        />
                        <Box sx={{mt: 3, display: 'flex', justifyContent: 'center'}}>
                            <FormHelperText error>
                                {errorMessage}
                            </FormHelperText>
                        </Box>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign In
                        </Button>
                        <Grid container justifyContent='flex-end'>
                            <Grid item >
                                <Link href="/register" variant="body2">
                                    {"Don't have an account? Sign Up"}
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}


{/*
    const [errorMessage, setErrorMessage] = useState("");
    const [inputUsername, setInputUsername] = useState({username: "",});
    const [inputPassword, setInputPassword] = useState({password: "",})
    let navigate = useNavigate();

    const onUsernameChange = e => {
        setInputUsername({
            ...inputUsername,
            [e.target.name]: e.target.value,
        })
    }

    const onPasswordChange = e => {
        setInputPassword({
            ...inputPassword,
            [e.target.name]: e.target.value,
        })
    }

    useEffect(() => {
        let user = Api.getCurrentUser();
        if (user) {
            if (!Api.sessionExpired()) {
                if (user.roles.some(role => role.name === 'ROLE_USER')) {
                    navigate('/profile');
                } 
                else if (user.roles.some(role => role.name === 'ROLE_ADMIN')) {
                    navigate('/admin');
                }
            }
        }

    }, [navigate, errorMessage])

        const handleSubmit = (event) => {
            event.preventDefault();
            const data = new FormData(event.currentTarget);
            Api.login(data)
                .then(user => {
                        if (user.tokens) {
                            if (user.roles.some(role => role.name === 'ROLE_ADMIN')) {
                                navigate('/admin');
                            } else if (user.roles.some(role => role.name === 'ROLE_BOOKER')) {
                                navigate('/booker');
                            } else if (user.roles.some(role => role.name === 'ROLE_HOTEL_MANAGER')) {
                                navigate('/hotelmanager');
                            }
                        }
                    }
                )
                .catch((err) => {
                    setErrorMessage(err.response.data.error_message);
                });
            setInputUsername({username: ""});
            setInputPassword({password: ""});
        };*/}