import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import {useEffect, useState} from "react";
import ticketService from "../../services/ticketService";
import userService from "../../services/userService";
import Api from "../../services/api";
import {FormHelperText} from "@mui/material";
import Alert from "@mui/material/Alert";


export default function ProfilePage() {
    
    const user = Api.getCurrentUser();
    console.log(user);
    
    const [tickets, setTickets] = useState([]);
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const [updateProfileErrorMessage, setUpdateProfileErrorMessage] = useState('');
    const [updatePasswordErrorMessage, setUpdatePasswordErrorMessage] = useState('');
    
    
    useEffect(() => {
        ticketService.getTicketsByBuyer(user).then((res) => {
            setTickets(res.data);
        });
    }, []);
    console.log(tickets);
    
    
    const handleUpdateProfileSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const updateRequest = {
            id: user.id,
            first_name: data.get('first_name'),
            last_name: data.get('last_name'),
            phone_number: data.get('phone_number'),
            email: data.get('username'),
        }
        
        userService.updateUser(updateRequest).then((response) => {
            console.log("User updated successfully", response.data);
            sessionStorage.setItem("user", JSON.stringify(response.data));
            setAlert(true);
            setAlertContent('Profile updated successfully!');
            setTimeout(window.location.reload.bind(window.location), 1000);
        }).catch((error) => {
            setUpdateProfileErrorMessage(error.response.data.message);
        });
    };
    
    const handleLogOutSubmit = () => {
        sessionStorage.removeItem("user");
        sessionStorage.removeItem("token");
        window.location.href = "/";
    };

    const handleUpdatePasswordSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const object = {
            id: user.id,
            current_password: data.get("current_password"),
            new_password: data.get("new_password")
        }
        userService.updatePassword(object).then((res) => {
            setAlert(true);
            setAlertContent('Password changed successfully!');
            setTimeout(window.location.reload.bind(window.location), 1000);
        }).catch((error) => {
            setUpdatePasswordErrorMessage(error.response.data.message);
        })
    };
    
    return (
        <>
            {alert ? <Alert severity='success'>{alertContent}</Alert> : <></> }    
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
                                {user.first_name} {user.last_name}
                            </Typography>
                            <Box component="form" onSubmit={handleUpdateProfileSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="First Name"
                                    name="first_name"
                                    defaultValue={user.first_name}
                                    //value={user.first_name}
                                    autoComplete="given-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Last Name"
                                    name="last_name"
                                    defaultValue={user.last_name}
                                    //value={user.last_name}
                                    autoComplete="family-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Phone Number"
                                    name="phone_number"
                                    defaultValue={user.phone_number}
                                    //value={user.phone_number}
                                    autoComplete="phone-number"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Email Address"
                                    name="username"
                                    defaultValue={user.email}
                                    //value={user.email}
                                    autoComplete="username"
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 2}}
                                >
                                    Update Profile
                                </Button>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={handleLogOutSubmit}
                                    sx={{mb: 3}}
                                >
                                    Log Out
                                </Button>
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{mb: 4,fontSize: '20px'}}>
                                        {updateProfileErrorMessage}
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
                            <Box sx={{
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}>
                                <Typography component="h1" variant="h4" sx={{mt: 5,mb: 2}}>
                                    Change Password
                                </Typography>
                                <Box component="form" onSubmit={handleUpdatePasswordSubmit} sx={{ mt: 1,mr: 25,ml: 25 }}>
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
                                    <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                        <FormHelperText error sx={{mb: 4,fontSize: '20px'}}>
                                            {updatePasswordErrorMessage}
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
                        <Box sx={{
                            display:'flex',
                            flexDirection:'column',
                            alignItems:'center',
                        }}>
                            <Typography component="h1" variant="h4" sx={{mb: 5,mt: 5}}>
                                Purchased Tickets
                            </Typography>
                            {tickets?.map((ticket) => (
                                <Card
                                    key={ticket.id}
                                    sx={{width: 600, borderRadius: '10px', height: 300, mb:3}}
                                    variant="outlined"
                                >
                                    <CardContent>
                                        <Typography
                                            sx={{fontSize: 18}}
                                            color="text.secondary"
                                            gutterBottom
                                        >
                                            {new Date(ticket.match.date).toLocaleDateString()}
                                        </Typography>
                                        <Typography variant="h4" component="div">
                                            {ticket.match.home_team.name} vs {ticket.match.away_team.name}
                                        </Typography>
                                        <Typography sx={{mb: 1.5}} color="text.secondary">
                                            {new Date(ticket.match.date).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                                        </Typography>
                                        <Typography sx={{mb: 1.5}} color="text.secondary">
                                            Stadium : {ticket.match.home_team.stadium.name}
                                        </Typography>
                                    </CardContent>
                                </Card>
                            ))}
                        </Box>
                    </Card>
                </Grid>
            </Grid>
        </Box>
        </>
    );
}


{/* <Box sx={{ flexGrow: 1,mt: 15,ml: 5,mr: 5 }}>
                    <Box sx={{
                        display:'flex',
                        flexDirection:'column',
                        alignItems:'flex-start',
                    }}>
                        <AccountCircleIcon sx={{ 
                            fontSize: 170,
                        }}/>
                        <Typography component="h1" variant="h4">
                            Charles Leclair
                        </Typography>
                    </Box>
            
            
            
            
            
                    <Box sx={{
                        display:'flex',
                        flexDirection:'column',
                        alignItems:'flex-end',
                    }}>
                        <Typography component="h1" variant="h4">
                            Purchased Tickets
                        </Typography>
                        {Array.from(Array(12)).map((_, index) => (
                                <Card
                                    sx={{width: 600, borderRadius: '10px', height: 300}}
                                    variant="outlined"
                                >
                                    <CardContent>
                                        <Typography
                                            sx={{fontSize: 18}}
                                            color="text.secondary"
                                            gutterBottom
                                        >
                                            26.09.2022
                                        </Typography>
                                        <Typography variant="h4" component="div">
                                            Manchester United vs Arsenal
                                        </Typography>
                                        <Typography sx={{mb: 1.5}} color="text.secondary">
                                            4:30 PM
                                        </Typography>
                                    </CardContent>
                                    <CardActions sx={{display: 'flex', alignItems: 'flex-end', minWidth: 200}}>
                                        <Button variant="contained" sx={{fontSize: 22, borderRadius: '10px'}}>
                                            Buy tickets
                                        </Button>
                                    </CardActions>
                                </Card>
                        ))}
                    </Box>
        </Box>*/}
