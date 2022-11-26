import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Typography from "@mui/material/Typography";
import {Box} from "@mui/system";
import statisticsService from "../../services/statisticsService";
import teamsService from "../../services/teamsService";
import {useEffect, useState} from "react";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";



export default function StatisticsPage() {
    
    const [teams, setTeams] = useState([]);
    const [rerender, setRerender] = useState(false);
    
    useEffect(() => {
        teamsService.getTeams().then((res) => {
            setTeams(res.data);
        });
      setRerender(false);
    }, [rerender]);

    useEffect(() => {
        teams.map((team) => (
            statisticsService.getAVGSalesOfTicketsPerTeam(team.id).then((res) => {
                team.avg = res.data;
                setTeams(teams);
            })
        ))
        setRerender(true);
    }, [teams]);

    {/*useEffect(() => {
        teams.map((team) => (
            statisticsService.getNumberOfMatchesByTeam(team.id).then((res) => {
                team.matchesNr = res.data;
                setTeams(teams);
            })
        ))
        setRerender(true);
    }, [teams]);

    useEffect(() => {
        teams.map((team) => (
            statisticsService.getNumberOfTicketsByTeam(team.id).then((res) => {
                team.ticketNr = res.data;
                setTeams(teams);
            })
        ))
        setRerender(true);
    }, [teams]);
    */}
    
    return (
        <>
            <Box
                sx={{
                    mb: 7,
                    mt:7,
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
            <Typography variant="h4" align="center" sx={{mt:4}}>Statistics Page</Typography>
        <Typography component="h1" variant="h5" align="center" sx={{mt:10}}>Statistics about average sales of tickets per team</Typography>
        <TableContainer component={Paper} sx={{mt:2}}>
            <Table  aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell sx={{fontWeight: 'bold'}}>Football Team</TableCell>
                        <TableCell sx={{fontWeight: 'bold'}}>AVG ticket sales per matches</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {teams.map((team) => (
                            <TableRow
                                key={team.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">{team.name}</TableCell>
                                <TableCell>{team.avg}</TableCell>
                            </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        </>
    );
}
