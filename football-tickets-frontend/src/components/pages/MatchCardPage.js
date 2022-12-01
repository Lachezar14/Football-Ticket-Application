import * as React from 'react';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useState, useEffect} from "react";
import matchService from "../../services/matchService";
import {Link} from "react-router-dom";
import TextField from "@mui/material/TextField";
import Alert from "@mui/material/Alert";

export default function MatchCardPage() {

    const [matches, setMatches] = useState([]);
    const [search, setSearch] = useState("");
    
    useEffect(() => {
        matchService.getMatches().then(res => setMatches(res.data))
        console.log(matches)
    }, []);{/*[matches]*/}
 
    const teamNames =["home_team", "away_team"];
    const searchByName = (data) => {
        return data.filter((value) => {
            teamNames.some((teamName) => { value[teamName].toLowerCase().includes(search)})
        })
    }
    
    
    return (
            <div>
            {/* Hero unit */}
            <Box
                sx={{
                    bgcolor: 'background.paper',
                    pt: 8,
                    pb: 6,
                }}
            >
                <Container maxWidth="sm">
                    <Typography
                        component="h1"
                        variant="h2"
                        align="center"
                        color="text.primary"
                        gutterBottom
                    >
                        Available Matches
                    </Typography>
                    <Typography variant="h5" align="center" color="text.secondary" paragraph>
                        Here are all available matches for which you can buy tickets.
                    </Typography>
                    <Stack
                        sx={{pt: 4}}
                        direction="row"
                        spacing={2}
                        justifyContent="center"
                    >
                        <TextField
                            id="search"
                            label="Search field"
                            type="search"
                            variant="outlined"
                            onChange={(e) => setSearch(e.target.value)}
                        />
                    </Stack>
                </Container>
            </Box>
            {/* End hero unit */}
            <Grid 
                container spacing={{xs: 2, md: 3}} 
                columns={{xs: 4, sm: 8, md: 12}} 
                padding={5}
            >
                { matches?.filter((val) => {
                    if (search === "") {
                        return val
                    } else if (val.home_team.name.toLowerCase().includes(search) || val.away_team.name.toLowerCase().includes(search)) {
                        return val
                    }})
                   .map((match) => (
                    <Grid 
                        item xs={2} sm={4} md={4} 
                        key={match.id}>
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
                                    {new Date(match.date).toLocaleDateString()}
                                </Typography>
                                <Typography variant="h4" component="div">
                                    {match.home_team.name} vs {match.away_team.name}
                                </Typography>
                                <Typography sx={{mb: 1.5}} color="text.secondary">
                                    {new Date(match.date).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                                </Typography>
                            </CardContent>
                            <CardActions sx={{display: 'flex', alignItems: 'flex-end', minWidth: 200}}>
                                <Button variant="contained"
                                        component={Link}
                                        to={`/matches/ticketSale`}
                                        state={{match: match}}
                                        sx={{fontSize: 22, borderRadius: '10px'}}>
                                        Buy tickets
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
}
