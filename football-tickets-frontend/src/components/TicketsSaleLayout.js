import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Paper from '@mui/material/Paper';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import TextField from "@mui/material/TextField";
import {useLocation, useNavigate} from "react-router-dom";
import Api from "../service/api";
import jwt_decode from "jwt-decode";
import userService from "../service/userService";
import {useCallback, useEffect, useRef} from "react";
import ticketService from "../service/ticketService";

function Copyright() {
    return (
        <Typography variant="body2" color="text.secondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const theme = createTheme();

export default function Checkout() {
    
    const {state} = useLocation();
    const match = state;
    console.log(match);
    
    //const [user, setUser] = React.useState([]);
    let navigate = useNavigate();
    const user = Api.getCurrentUser();
    console.log(user);

    {/*useEffect(() => {
    userService.getUserByEmail(info.sub).then((res) => {
        setUser(res.data);
    });
    }, []);
    
    console.log(user);*/}

    
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        let ticket_number = data.get('tickets_number');
        console.log(ticket_number);
        
        const ticket = {
            price: match.match.ticket_price,
            buyer: user,
            match: match.match,
        };
        //console.log(ticket);
        if(user != null){
         for (let i = 0; i < ticket_number; i++) {
            ticketService.buyTickets(ticket).then((res) => {
                console.log(ticket);
            });
         }
        }else{
            navigate('/login');
        }
    };
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
                <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                    <Box component="form" onSubmit={handleSubmit}>
                        <Typography component="h1" variant="h3" align="center" sx={{mb: 12}}>
                            Checkout
                        </Typography>
                        <Typography component="h1" variant="h4" align="center">
                            {match.match.home_team.name} vs {match.match.away_team.name}
                        </Typography>
                        <Typography component="h1" variant="h6" align="center">
                            {match.date}
                        </Typography>
                        <Typography component="h1" variant="h6" align="center">
                            4:30 PM
                        </Typography>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Tickets Amount"
                            name="tickets_number"
                            autoComplete="tickets-number"
                            autoFocus
                        />
                        <Typography component="h1" variant="h6" align="center" color={'red'}>
                            Available Tickets: {match.match.ticket_number} tickets
                        </Typography>
                        <Typography component="h1" variant="h5" align="center" sx={{mt: 7}}>
                            Total Price: {match.match.ticket_price} $
                        </Typography>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 1,mb: 7 }}
                        >
                            Buy Tickets
                        </Button>
                    </Box>
                </Paper>
                <Copyright />
            </Container>
        </ThemeProvider>
    );
}