import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import {useEffect, useState} from "react";
import userService from "../../services/userService";
import {Link} from "react-router-dom";
import {FormHelperText} from "@mui/material";
import Alert from "@mui/material/Alert";
import Api from "../../services/api";


export default function AdminPage() {

    const user = Api.getCurrentUser();
    console.log(user);

    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const [users, setUsers] = React.useState([]);
    const [userToAdmin, setUserToAdmin] = React.useState('');
    const[updateUserErrorMessage, setUpdateUserErrorMessage] = React.useState('');
    const[updatePasswordErrorMessage, setUpdatePasswordErrorMessage] = React.useState('');
    const[makeUserAdminErrorMessage, setMakeUserAdminErrorMessage] = React.useState('');

    useEffect(() => {
        userService.getAllUsers().then(res => setUsers(res.data));
    },  []);

    const userHandleChange = (event) => {
        setUserToAdmin(event.target.value);
    };

    const handleLogOutSubmit = () => {
        sessionStorage.removeItem("user");
        sessionStorage.removeItem("token");
        window.location.href = "/";
    };

    const handleUpdateProfileSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const updateRequest = {
            id: userToAdmin.id,
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
            setUpdateUserErrorMessage(error.response.data.message);
        });
    };

    const handleMakeAdminSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        userService.makeAdmin(userToAdmin).then((response) => {
            console.log("USER made admin successfully", response.data);
            setAlert(true);
            window.scroll(0, 0);
            setAlertContent('User made admin successfully!');
        }).catch((error) => {
            setMakeUserAdminErrorMessage(error.response.data.message);
        });
    };

    const handleUpdatePasswordSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const object = {
            id: userToAdmin.id,
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
            <Box sx={{ flexGrow: 1,mt: 7,ml: 5,mr: 5 }}>
            <Box sx={{
                mb: 7,
                display: 'flex',
                justifyContent: 'space-evenly',
            }}>
                <Button component={Link}
                    to={"/admin/matches"}
                    variant="contained"
                    size='large'
                >
                    Match Management
                </Button>
                <Button component={Link}
                        to={"/admin/statistics"}
                        variant="contained"
                        size='large'
                >
                    Statistics
                </Button>
                <Button component={Link}
                        to={"/admin/teams"}
                        variant="contained"
                        size='large'
                >
                    Team Management
                </Button>
            </Box>
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
                                    autoComplete="given-name"
                                    defaultValue={user.first_name}
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Last Name"
                                    name="last_name"
                                    autoComplete="family-name"
                                    defaultValue={user.last_name}
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Phone Number"
                                    name="phone_number"
                                    autoComplete="phone-number"
                                    defaultValue={user.phone_number}
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Email Address"
                                    name="username"
                                    autoComplete="username"
                                    defaultValue={user.email}
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 2 }}
                                >
                                    Update Profile
                                </Button>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={handleLogOutSubmit}
                                    sx={{mb: 7}}
                                >
                                    Log Out
                                </Button>
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {updateUserErrorMessage}
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
                                <Box component="form" onSubmit={handleUpdatePasswordSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
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
                                        <FormHelperText error sx={{ fontSize: '25px'}}>
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
                                Users List
                            </Typography>
                            <Box component="form" onSubmit={handleMakeAdminSubmit} noValidate sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <TextField
                                    sx={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        width: '300px',
                                    }}
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="User"
                                    value={userToAdmin}
                                    onChange={userHandleChange}
                                >
                                    {users.map((user) => (
                                        <MenuItem key={user.id} value={user}>
                                            {user.first_name} {user.last_name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 7}}
                                >
                                    Make Admin
                                </Button>
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {makeUserAdminErrorMessage}
                                    </FormHelperText>
                                </Box>
                            </Box>
                        </Box>
                    </Card>
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
                            </Box>
                        </Card>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
        </>
    );
}