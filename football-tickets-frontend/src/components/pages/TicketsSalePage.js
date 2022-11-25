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
import Alert from '@mui/material/Alert';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import {Link as RouterLink} from "react-router-dom";
import Typography from '@mui/material/Typography';
import TextField from "@mui/material/TextField";
import {useLocation, useNavigate} from "react-router-dom";
import Api from "../../services/api";

import {useState} from "react";
import ticketService from "../../services/ticketService";
import {FormHelperText} from "@mui/material";

export default function Checkout() {
    
    const {state} = useLocation();
    const match = state;
    console.log(match);
    
    let navigate = useNavigate();
    const user = Api.getCurrentUser();
    console.log(user);

    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        
        const ticket = {
            ticketAmount: data.get('tickets_number'),
            price: match.match.ticket_price,
            buyer: user,
            match: match.match,
        };
        if(user != null){
            if (ticket.ticketAmount > 0) {
                ticketService.buyTickets(ticket).then((res) => {
                    console.log(ticket);
                    if (res.status === 201) {
                        setAlert(true);
                        setAlertContent('Tickets bought successfully!');
                        setTimeout(() => {
                            navigate('/matches');
                        }, 2000);
                    }else{
                        setErrorMessage(res.data.message);
                    }}).catch((error) => {
                    setErrorMessage(error.response.data.message);
                });
            } else{
                setErrorMessage("You must buy at least one ticket!");
            }
        }else{
            navigate('/login');
        }
    };
    
    return (
        <>
        {alert ? <Alert severity='success'>{alertContent}</Alert> : <></> }
            <Container component="main" maxWidth="md" sx={{ mb: 4 }}>
                <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
                    <Box component="form" onSubmit={handleSubmit}>
                        <Typography component="h1" variant="h3" align="center" sx={{mb: 12}}>
                            Checkout
                        </Typography>
                        <Typography component="h1" variant="h4" align="center">
                            {match.match.home_team.name} vs {match.match.away_team.name}
                        </Typography>
                        <Typography component="h1" variant="h6" align="center">
                            {new Date(match.match.date).toLocaleDateString()}
                        </Typography>
                        <Typography component="h1" variant="h6" align="center">
                            {new Date(match.match.date).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
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
                        <Typography component="h1" variant="h6" align="center" color={'darkBlue'}>
                            {match.match.ticket_number !== 0 ? `Available Tickets: ${match.match.ticket_number} tickets` : "Sold Out"}
                        </Typography>
                        <Typography component="h1" variant="h5" align="center" sx={{mt: 7}}>
                            Total Price: ${match.match.ticket_price}
                        </Typography>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 1,mb: 2}}
                        >
                            Buy Tickets
                        </Button>
                        <Button 
                            component={RouterLink}
                            to= {"/matches"} 
                            fullWidth 
                            variant="contained" 
                            sx={{mt: 1,mb: 7 }}
                        >
                            Go Back
                        </Button>
                        <Box sx={{display: 'flex', justifyContent: 'center'}}>
                            <FormHelperText error sx={{ fontSize: '25px'}}>
                                {errorMessage}
                            </FormHelperText>
                        </Box>
                    </Box>
                </Paper>
            </Container>
        </>
    );
}