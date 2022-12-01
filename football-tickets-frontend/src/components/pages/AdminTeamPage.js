 import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import {useEffect, useState} from "react";
import teamsService from "../../services/teamsService";
import {Link, useNavigate} from "react-router-dom";
 import {FormHelperText} from "@mui/material";
 import Alert from "@mui/material/Alert";


export default function AdminTeamPage() {

    const [teams, setTeams] = React.useState([]);
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const [createTeamErrorMessage, setCreateTeamErrorMessage] = useState('');
    const [updateTeamErrorMessage, setUpdateTeamErrorMessage] = useState('');
    const [deleteTeamErrorMessage, setDeleteTeamErrorMessage] = useState('');

    let navigate = useNavigate();

    useEffect(() => {
        teamsService.getTeams().then(res => setTeams(res.data));
    },  []);

    const [team, setTeam] = React.useState('');
    console.log(team);

    const teamHandleChange = (event) => {
        setTeam(event.target.value);
    };

    const handleDeleteTeamSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const ID = team.id;
        console.log(team);
        teamsService.deleteTeam(ID).then((response) => {
            console.log("Team deleted successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent("Team deleted successfully");
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch((e) => {
            setDeleteTeamErrorMessage(e.response.data.message);
        });
    };

    const handleUpdateTeamSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const updatedTeam = {
            id: team.id,
            name : data.get("name"),
            stadium : {
                id: team.stadium.id,
                name : data.get("stadium"),
                capacity : data.get("capacity")
            }
        };
        console.log(updatedTeam);
        teamsService.updateTeam(updatedTeam).then((response) => {
            console.log("Team updated successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent("Team updated successfully");
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch ((e) => {
            setUpdateTeamErrorMessage(e.response.data.message);
        });
    };

    const handleNewTeamSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const team = {
            name : data.get("name"),
            stadium : {
                name : data.get("stadium"),
                capacity : data.get("capacity")
            }
        };
        console.log(team);
        teamsService.saveTeam(team).then((response) => {
            console.log("TEAM created successfully", response.data);
            setAlert(true);
            window.scroll(0,0);
            setAlertContent("Team created successfully");
            setTimeout(() => {
                navigate('/admin');
            }, 2000);
        }).catch((e) => {
            setCreateTeamErrorMessage(e.response.data.message);
        });
    };


    return (
        <>
            {alert ? <Alert severity='success'>{alertContent}</Alert> : <></> }
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
                        <Box sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}>
                            <Typography component="h1" variant="h4" sx={{mb: 5,mt: 5}}>
                                Add New Football Team
                            </Typography>
                            <Box component="form" onSubmit={handleNewTeamSubmit} sx={{ mt: 1,mr: 25,ml: 25 }}>
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
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {createTeamErrorMessage}
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
                                    Delete Football Team
                                </Typography>
                                <Box component="form" onSubmit={handleDeleteTeamSubmit} noValidate>
                                    <TextField
                                        sx={{display: 'flex', justifyContent: 'center', width: '300px'}}
                                        margin="normal"
                                        id="outlined-select-currency"
                                        required
                                        fullWidth
                                        select
                                        label="Football Teams"
                                        value={team}
                                        onChange={teamHandleChange}
                                    >
                                        {teams.map((team) => (
                                            <MenuItem key={team.id} value={team}>
                                                {team.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                    <Button
                                        type="submit"
                                        fullWidth
                                        variant="contained"
                                        sx={{ mt: 3, mb: 7}}
                                    >
                                        Delete Team
                                    </Button>
                                    <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                        <FormHelperText error sx={{ fontSize: '25px'}}>
                                            {deleteTeamErrorMessage}
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
                                Update Football Team
                            </Typography>
                            <Box component="form" onSubmit={handleUpdateTeamSubmit}  sx={{ mt: 1,mr: 25,ml: 25 }}>
                                <TextField
                                    margin="normal"
                                    id="outlined-select-currency"
                                    required
                                    fullWidth
                                    select
                                    label="Available Teams"
                                    value={team}
                                    onChange={teamHandleChange}
                                    sx={{mb: 10}}
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
                                    label="Team Name"
                                    //InputLabelProps={{ shrink: true }}
                                    name="name"
                                    //key={team ? team.name :""}
                                    key={team.name}
                                    defaultValue={team.name}
                                    //defaultValue={team ? team.name :""}
                                    //value={team.name}
                                    autoComplete="team-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Stadium Name"
                                    name="stadium"
                                    key={team ? team.stadium.name : 2}
                                    //key={team.stadium.name}
                                    //defaultValue={team.stadium.name}
                                    defaultValue={team ? team.stadium.name : ""}
                                    //value={team.stadium.name}
                                    autoComplete="stadium-name"
                                    autoFocus
                                />
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    label="Stadium Capacity"
                                    name="capacity"
                                    key={team ? team.stadium.capacity : 1}
                                    //key={team.stadium.capacity}
                                    //defaultValue={team.stadium.capacity}
                                    defaultValue={team ? team.stadium.capacity : ""}
                                    //value={team.stadium.capacity}
                                    autoComplete="stadium-capacity"
                                    autoFocus
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 3, mb: 7 }}
                                >
                                    Update Team
                                </Button>
                                <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <FormHelperText error sx={{ fontSize: '25px'}}>
                                        {updateTeamErrorMessage}
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