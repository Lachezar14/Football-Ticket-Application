import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import MenuItem from "@mui/material/MenuItem";
import Api from "../service/api";
import {useEffect, useState} from "react";
import teamsService from "../service/teamsService";
import matchService from "../service/matchService";


export default function AdminMatchLayout() {

    const [teams, setTeams] = React.useState([]);
    useEffect(() => {
        teamsService.getTeams().then(res => setTeams(res.data));
    },  []);

    const [matches, setMatches] = React.useState([]);
    useEffect(() => {
        matchService.getMatches().then(res => setMatches(res.data));
    },  []);

    const [homeTeam, setHomeTeam] = React.useState('');
    const [awayTeam, setAwayTeam] = React.useState('');
    const [date, setDate] = React.useState(dayjs());
    const [match, setMatch] = React.useState([]);

    const matchHandleChange = (event) => {
        setMatch(event.target.value);
    };

    const homeTeamHandleChange = (event) => {
        setHomeTeam(event.target.value);
    };

    const awayTeamHandleChange = (event) => {
        setAwayTeam(event.target.value);
    };


    const dateHandleChange = (newValue) => {
        setDate(newValue);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            email: data.get('email'),
            password: data.get('password'),
        });
    };

    const handleNewMatchSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const match = {
            date: date,
            home_team: homeTeam,
            away_team: awayTeam,
            ticket_number: data.get('tickets_number'),
            ticket_price: data.get('ticket_price'),

        };
        console.log(match);
        matchService.saveMatch(match).then((response) => {
            console.log("MATCH created successfully", response.data);})

    };

    const handleUpdateMatchSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const match = {
            date: date,
            home_team: homeTeam,
            away_team: awayTeam,
            ticket_number: data.get('tickets_number'),
            ticket_price: data.get('ticket_price'),

        };
        console.log(match);
        matchService.updateMatch(match).then((response) => {
            console.log("MATCH updated successfully", response.data);})

    };

    const handleDeleteMatchSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const ID = match.id;
        console.log(ID);
        matchService.deleteMatch(ID).then((response) => {
            console.log("Match deleted successfully", response.data);
        })
    };


    return (
        <Box sx={{ flexGrow: 1,mt: 15,ml: 5,mr: 5 }}>
            <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                <Grid item xs={6}>
                    <Card sx={{
                        borderRadius: '10px',
                        boxShadow: 3,
                    }}>
                        <Box sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}>
                            <Typography component="h1" variant="h4" sx={{mb:5,mt:5}}>
                                Add New Match
                            </Typography>
                            <Box component="form" onSubmit={handleNewMatchSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                    <DateTimePicker
                                        margin="normal"
                                        required
                                        fullWidth
                                        name="date"
                                        label="Date and Time"
                                        value={date}
                                        onChange={dateHandleChange}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Home Team"
                                    value={homeTeam}
                                    onChange={homeTeamHandleChange}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team}>
                                            {team.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Away Team"
                                    value={awayTeam}
                                    onChange={awayTeamHandleChange}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team}>
                                            {team.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Tickets Amount"
                                    name="tickets_number"
                                    autoComplete="tickets-number"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Ticket Price"
                                    name="ticket_price"
                                    autoComplete="price"
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 7 }}
                                >
                                    Add Match
                                </Button>
                            </Box>
                        </Box>
                    </Card>
                    <Grid item sx={{mt: 5}}>
                        <Card sx={{
                            borderRadius: '10px',
                            boxShadow: 3,
                        }}>
                            <Box sx={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}>
                                <Typography component="h1" variant="h4" sx={{mt: 5,mb: 2}}>
                                    Delete Match
                                </Typography>
                                <Box component="form" onSubmit={handleDeleteMatchSubmit} noValidate>
                                    <TextField
                                        margin="normal"
                                        id="outlined-select-currency"
                                        required
                                        fullWidth
                                        select
                                        label="Available Matches"
                                        value={match}
                                        onChange={matchHandleChange}
                                    >
                                        {matches.map((match) => (
                                            <MenuItem key={match.id} value={match}>
                                                {match.home_team.name} vs {match.away_team.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                    <Button
                                        type="submit"
                                        fullWidth
                                        variant="contained"
                                        sx={{ mt: 3, mb: 7}}
                                    >
                                        Delete Match
                                    </Button>
                                </Box>
                            </Box>
                        </Card>
                    </Grid>
                </Grid>
                <Grid item xs={6}>
                    <Card sx={{
                        borderRadius: '10px',
                        boxShadow: 3,
                    }}>
                        <Box sx={{
                            display:'flex',
                            flexDirection:'column',
                            alignItems:'center',
                        }}>
                            <Typography component="h1" variant="h4" sx={{mb: 5,mt: 5}}>
                                Update Match Information
                            </Typography>
                            <Box component="form" onSubmit={handleUpdateMatchSubmit}  sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Available Matches"
                                    value={match}
                                    onChange={matchHandleChange}
                                    sx={{mb: 10}}
                                >
                                    {matches.map((match) => (
                                        <MenuItem key={match.id} value={match}>
                                            {match.home_team.name} vs {match.away_team.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                    <DateTimePicker
                                        margin="normal"
                                        required
                                        fullWidth
                                        name="date"
                                        label="Date and Time"
                                        value={date}
                                        onChange={dateHandleChange}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Home Team"
                                    value={homeTeam}
                                    onChange={homeTeamHandleChange}
                                    sx={{mt: 3}}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team}>
                                            {team.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Away Team"
                                    value={awayTeam}
                                    onChange={awayTeamHandleChange}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team}>
                                            {team.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Tickets Amount"
                                    name="tickets_number"
                                    autoComplete="tickets-number"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Ticket Price"
                                    name="ticket_price"
                                    autoComplete="price"
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 7 }}
                                >
                                    Update Match
                                </Button>
                            </Box>
                        </Box>
                    </Card>
                </Grid>
            </Grid>
        </Box>
    );
}