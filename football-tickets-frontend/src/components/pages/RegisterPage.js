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
import Api from '../../services/api';
import {useState,useEffect} from "react";
import {FormHelperText} from "@mui/material";
import {useNavigate} from "react-router-dom";

const theme = createTheme();

export default function RegisterPage() {
    
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    let [regSuccess, setRegSuccess] = useState(false);
    let navigate = useNavigate();
    
    useEffect(() => {}, [errorMessage]);
    useEffect(() => {}, [successMessage]);

    useEffect(() => {
        if(regSuccess) {
            const timer = setTimeout(() => {
                navigate('/login')
            }, 3000);
            return () => clearTimeout(timer);
        }}, [navigate, regSuccess]);
    
    
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        
        const object = {
            first_name: data.get('first_name'),
            last_name: data.get('last_name'),
            phone_number: data.get('phone_number'),
            email: data.get('email'),
            password: data.get('password'),
            roleName: "USER"
        };
        
        Api.register(object).then((response) => {
            if (response.data.email) {
                setRegSuccess(true);
                setSuccessMessage("Registration successful! Redirecting to login page...");
                console.log("Registration successful", response.data);
            }
        })
            .catch((err) => {
                setRegSuccess(false);
                setErrorMessage(err.response.data.message);
        });
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
                        Register
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    autoComplete="given-name"
                                    name="first_name"
                                    required
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
                                    autoFocus
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    id="lastName"
                                    label="Last Name"
                                    name="last_name"
                                    autoComplete="family-name"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    id="phoneNumber"
                                    label="Phone Number"
                                    name="phone_number"
                                    autoComplete="phone-number"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    id="email"
                                    label="Email Address"
                                    name="email"
                                    autoComplete="email"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="new-password"
                                />
                            </Grid>
                        </Grid>
                        <Box sx={{mt: 3, display: 'flex', justifyContent: 'center'}}>
                            { regSuccess ?
                                <FormHelperText success sx={{color: 'green'}}>
                                    {successMessage} 
                                </FormHelperText>
                            :
                                <FormHelperText error>
                                    {errorMessage}
                                </FormHelperText>
                            }
                        </Box>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Register
                        </Button>
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link href="/login" variant="body2">
                                    Already have an account? Sign in
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}