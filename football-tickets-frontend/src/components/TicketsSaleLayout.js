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
import {render} from "react-dom";
import {useLocation} from "react-router-dom";

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



const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
        email: data.get('email'),
        password: data.get('password'),
    });
};

const theme = createTheme();

export default function Checkout() {

    const {state} = useLocation();
    const match = state;
    console.log(match);
    
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
                            {match.homeTeam.name} vs {match.awayTeam.name}
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
                            Available Tickets: 50143 tickets
                        </Typography>
                        <Typography component="h1" variant="h5" align="center" sx={{mt: 7}}>
                            Total Price: {match.ticket_price} $
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