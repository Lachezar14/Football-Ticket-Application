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


export default function AdminPageLayout() {

    const teams = [
        {
            value: 'USD',
            label: '$',
        },
        {
            value: 'EUR',
            label: '€',
        },
        {
            value: 'BTC',
            label: '฿',
        },
        {
            value: 'JPY',
            label: '¥',
        },
    ];

    const [homeTeam, setHomeTeam] = React.useState('EUR');
    const [awayTeam, setAwayTeam] = React.useState('EUR');
    const [value, setValue] = React.useState(dayjs('2014-08-18T21:11:54'));
    const [match, setMatch] = React.useState(null);
    
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
        setValue(newValue);
    };
    
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            email: data.get('email'),
            password: data.get('password'),
        });
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
                            <AccountCircleIcon sx={{
                                fontSize: 170,
                            }}/>
                            <Typography component="h1" variant="h4" sx={{mb:5}}>
                                Charles LeClair
                            </Typography>
                            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="First Name"
                                    name="first_name"
                                    autoComplete="given-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Last Name"
                                    name="last_name"
                                    autoComplete="family-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Phone Number"
                                    name="phone_number"
                                    autoComplete="phone-number"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Email Address"
                                    name="username"
                                    autoComplete="username"
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 7 }}
                                >
                                    Update Profile
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
                                    Change Password
                                </Typography>
                                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                    <TextField
                                        margin="normal"
                                        required
                                        fullWidth
                                        name="current_password"
                                        label="Current Password"
                                        type="password"
                                        id="current_password"
                                        autoComplete="current-password"
                                    />
                                    <TextField
                                        margin="normal"
                                        required
                                        fullWidth
                                        name="new_password"
                                        label="New Password"
                                        type="password"
                                        id="new_password"
                                        autoComplete="new-password"
                                    />
                                    <Button
                                        type="submit"
                                        fullWidth
                                        variant="contained"
                                        sx={{ mt: 3, mb: 7}}
                                    >
                                        Update password
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
                                Add New Match
                            </Typography>
                            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                    <DateTimePicker
                                        margin="normal"
                                        required
                                        fullWidth
                                        label="Date and Time"
                                        value={value}
                                        onChange={dateHandleChange}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    fullWidth
                                    select
                                    label="Home Team"
                                    value={homeTeam}
                                    onChange={homeTeamHandleChange}
                                >
                                    {teams.map((option) => (
                                        <MenuItem key={option.value} value={option.value}>
                                            {option.label}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    fullWidth
                                    select
                                    label="Away Team"
                                    value={awayTeam}
                                    onChange={awayTeamHandleChange}
                                >
                                    {teams.map((option) => (
                                        <MenuItem key={option.value} value={option.value}>
                                            {option.label}
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
                                    name="price"
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
                                    Add Tickets to Match
                                </Typography>
                                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                    <TextField
                                        margin="normal"
                                        id="outlined-select-currency"
                                        fullWidth
                                        select
                                        label="Match"
                                        value={match}
                                        onChange={matchHandleChange}
                                    >
                                        {teams.map((option) => (
                                            <MenuItem key={option.value} value={option.value}>
                                                {option.label}
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
                                        name="price"
                                        autoComplete="price"
                                        autoFocus
                                    />
                                    <Button
                                        type="submit"
                                        fullWidth
                                        variant="contained"
                                        sx={{ mt: 3, mb: 7}}
                                    >
                                        Add Tickets
                                    </Button>
                                </Box>
                            </Box>
                        </Card>
                    </Grid>
                    <Grid item sx={{mt: 5}}>
                        <Card sx={{
                                borderRadius: '10px',
                                boxShadow: 3
                        }}>
                            <Box sx={{
                                display:'flex',
                                flexDirection:'column',
                                alignItems:'center',
                            }}>
                                <Typography component="h1" variant="h4" sx={{mb: 5,mt: 5}}>
                                    Add New Football Team
                                </Typography>
                                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                    <TextField
                                        margin="normal"
                                        required
                                        fullWidth
                                        label="Team Name"
                                        name="name"
                                        autoComplete="team-name"
                                        autoFocus
                                    />
                                    <TextField
                                        margin="normal"
                                        required
                                        fullWidth
                                        label="Stadium Name"
                                        name="stadium"
                                        autoComplete="stadium-name"
                                        autoFocus
                                    />
                                    <TextField
                                        margin="normal"
                                        required
                                        fullWidth
                                        label="Stadium Capacity"
                                        name="capacity"
                                        autoComplete="stadium-capacity"
                                        autoFocus
                                    />
                                    <Button
                                        type="submit"
                                        fullWidth
                                        variant="contained"
                                        sx={{ mt: 3, mb: 7 }}
                                    >
                                        Add Team
                                    </Button>
                                </Box>
                            </Box>
                        </Card>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    );
}