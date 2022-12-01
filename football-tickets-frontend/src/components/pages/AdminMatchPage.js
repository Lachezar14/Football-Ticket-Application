import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import dayjs from 'dayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import MenuItem from "@mui/material/MenuItem";
import {useEffect, useState} from "react";
import teamsService from "../../services/teamsService";
import matchService from "../../services/matchService";
import {Link, useNavigate} from "react-router-dom";
import {Autocomplete, FormHelperText} from "@mui/material";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";


export default function AdminMatchPage() {

    const [teams, setTeams] = React.useState([]);
    useEffect(() => {
        teamsService.getTeams().then(res => setTeams(res.data));
    },  []);

    const [matches, setMatches] = React.useState([]);
    useEffect(() => {
        matchService.getMatches().then(res => setMatches(res.data));
    },  []);


    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const [homeTeam, setHomeTeam] = React.useState('');
    const [awayTeam, setAwayTeam] = React.useState('');
    const [date, setDate] = React.useState(dayjs());
    const [newMatch, setNewMatch] = React.useState([]);
    const [updateMatch, setUpdateMatch] = React.useState([]);
    const [updateDate, setUpdateDate] = React.useState();
    const [updateHomeTeam, setUpdateHomeTeam] = React.useState('');
    const [updateAwayTeam, setUpdateAwayTeam] = React.useState('');
    const [createMatchErrorMessage, setCreateMatchErrorMessage] = useState('');
    const [updateMatchErrorMessage, setUpdateMatchErrorMessage] = useState('');
    const [deleteMatchErrorMessage, setDeleteMatchErrorMessage] = useState('');

    let navigate = useNavigate();

    const matchHandleChange = (event) => {
        setNewMatch(event.target.value);
    };

    const updateMatchHandleChange = (event) => {
        setUpdateMatch(event.target.value);
        setUpdateHomeTeam(event.target.value.home_team.id);
        setUpdateAwayTeam(event.target.value.away_team.id);
        setUpdateDate(event.target.value.date);
    }

    const homeTeamHandleChange = (event) => {
        setHomeTeam(event.target.value);
    };

    const awayTeamHandleChange = (event) => {
        setAwayTeam(event.target.value);
    };

    const updateHomeTeamHandleChange = (event) => {
        setUpdateHomeTeam(event.target.value);
    };

    const updateAwayTeamHandleChange = (event) => {
        setUpdateAwayTeam(event.target.value);
    };
    
    const dateHandleChange = (newValue) => {
        setDate(newValue);
    };

    const updateDateHandleChange = (newValue) => {
        setUpdateDate(newValue);
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
            console.log("MATCH created successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent('Match created successfully');
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch((error) => {
            setCreateMatchErrorMessage(error.response.data.message);
        });
    };

    const handleUpdateMatchSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const match = {
            date: date,
            home_team: teams.find(team => team.id === updateHomeTeam),
            away_team: teams.find(team => team.id === updateAwayTeam),
            ticket_number: data.get('tickets_number'),
            ticket_price: data.get('ticket_price'),

        };
        console.log(match);
        matchService.updateMatch(match).then((response) => {
            console.log("MATCH updated successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent('Match updated successfully');
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch((error) => {
            setUpdateMatchErrorMessage(error.response.data.message);
        });
    };

    const handleDeleteMatchSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const ID = newMatch.id;
        console.log(ID);
        matchService.deleteMatch(ID).then((response) => {
            console.log("Match deleted successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent('Match deleted successfully');
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch((error) => {
            setDeleteMatchErrorMessage(error.response.data.message);
        });
    };


    return (
        <>
            {alert ? <Alert severity='success'>{alertContent}</Alert> : <></> }
            
            {/* TODO work on it after ux feedback
            <IconButton component={Link} to="/admin">
                <Box sx={{display:'flex',flexDirection:'column'}}>
                    <ArrowBackIosIcon />
                    <Typography>Go Back</Typography>
                </Box>
            </IconButton>
            */}
            <Box sx={{ flexGrow: 1,mt: 7,ml: 5,mr: 5 }}>
            <Box
                sx={{
                    mb: 7,
                    display: 'flex',
                    justifyContent: 'center',
                }}
            >
                <Button component={Link}
                        to={"/admin"}
                        variant="contained"
                        size='large'
                        style={{width: '200px'}}
                >
                    Go Back
                </Button>
            </Box>
            <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                <Grid item xs={6}>
                    <Card sx={{
                        borderRadius: '10px',
                        boxShadow: 3,
                    }}>
                        {/* CREATING NEW MATCH*/}
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
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {createMatchErrorMessage}
                                    </FormHelperText>
                                </Box>
                            </Box>
                        </Box>
                    </Card>
                    <Grid item sx={{mt: 5}}>
                        <Card sx={{
                            borderRadius: '10px',
                            boxShadow: 3,
                        }}>
                            {/* DELETING A MATCH*/}
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
                                        value={newMatch}
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
                                    <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                        <FormHelperText error sx={{ fontSize: '25px'}}>
                                            {deleteMatchErrorMessage}
                                        </FormHelperText>
                                    </Box>
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
                        {/* UPDATING A MATCH*/}
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
                                    //defaultValue={updateMatch}
                                    value={updateMatch}
                                    onChange={updateMatchHandleChange}
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
                                        value={updateDate}
                                        onChange={updateDateHandleChange}
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
                                    value={updateHomeTeam}
                                    onChange={updateHomeTeamHandleChange}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team.id}>
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
                                    value={updateAwayTeam}
                                    onChange={updateAwayTeamHandleChange}
                                >
                                    {teams.map((team) => (
                                        <MenuItem key={team.id} value={team.id}>
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
                                    key={updateMatch.ticket_number}
                                    defaultValue={updateMatch.ticket_number}
                                    autoComplete="tickets-number"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Ticket Price"
                                    name="ticket_price"
                                    key={updateMatch.ticket_price}
                                    defaultValue={updateMatch.ticket_price}
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
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {updateMatchErrorMessage}
                                    </FormHelperText>
                                </Box>
                            </Box>
                        </Box>
                    </Card>
                </Grid>
            </Grid>
        </Box>
        </>
    );
}