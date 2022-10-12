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
import matchService from "../service/matchService";
import {Link} from "react-router-dom";


export default function MatchCardLayout() {

    const [matches, setMatches] = useState([]);
    
    useEffect(() => {
        matchService.getMatches().then(res => setMatches(res.data))
        console.log(matches)
    }, []);{/*[matches]*/}
    
    
    
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
                        <Button variant="contained">Main call to action</Button>
                        <Button variant="outlined">Secondary action</Button>
                    </Stack>
                </Container>
            </Box>
            {/* End hero unit */}
            <Grid 
                container spacing={{xs: 2, md: 3}} 
                columns={{xs: 4, sm: 8, md: 12}} 
                padding={5}
            >
                {/*{Array.from(Array(12)).map((_, index)*/}
                { matches?.map((match) => (
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
                                    {match.date}
                                </Typography>
                                <Typography variant="h4" component="div">
                                    {match.home_team.name} vs {match.away_team.name}
                                </Typography>
                                <Typography sx={{mb: 1.5}} color="text.secondary">
                                    4:30 PM
                                </Typography>
                            </CardContent>
                            <CardActions sx={{display: 'flex', alignItems: 'flex-end', minWidth: 200}}>
                                <Button variant="contained"
                                        component={Link}
                                        to={`/ticketSale`}
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
