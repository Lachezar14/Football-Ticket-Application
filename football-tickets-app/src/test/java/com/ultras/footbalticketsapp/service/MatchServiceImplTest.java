package com.ultras.footbalticketsapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.mapper.MatchMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.repository.MatchRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MatchServiceImpl.class})
@ExtendWith(SpringExtension.class)
@Disabled
class MatchServiceImplTest {
    @MockBean
    private MatchMapper matchMapper;

    @MockBean
    private MatchRepository matchRepository;

    @Autowired
    private MatchServiceImpl matchServiceImpl;

    /**
     * Method under test: {@link MatchServiceImpl#saveMatch(NewMatchRequest)}
     */
    @Test
    void testSaveMatch() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchRepository.findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), (Date) any())).thenReturn(match);
        when(matchRepository.save((Match) any())).thenReturn(match1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        Match match2 = new Match();
        match2.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        match2.setHome_team(footballTeam5);
        match2.setId(123);
        match2.setTicket_number(10);
        match2.setTicket_price(10.0d);
        when(matchMapper.newMatchRequestToMatch((NewMatchRequest) any())).thenReturn(match2);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.saveMatch(new NewMatchRequest()));
        verify(matchRepository).findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), (Date) any());
        verify(matchMapper).newMatchRequestToMatch((NewMatchRequest) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#saveMatch(NewMatchRequest)}
     */
    @Test
    void testSaveMatch2() {
        when(matchRepository.findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), (Date) any()))
                .thenThrow(new IllegalStateException());
        when(matchRepository.save((Match) any())).thenThrow(new IllegalStateException());

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        when(matchMapper.newMatchRequestToMatch((NewMatchRequest) any())).thenReturn(match);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.saveMatch(new NewMatchRequest()));
        verify(matchRepository).findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), (Date) any());
        verify(matchMapper).newMatchRequestToMatch((NewMatchRequest) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#saveMatch(NewMatchRequest)}
     */
    @Test
    void testSaveMatch3() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchRepository.findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), (Date) any())).thenReturn(match);
        when(matchRepository.save((Match) any())).thenReturn(match1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        Stadium stadium6 = new Stadium();
        stadium6.setCapacity(3);
        stadium6.setId(123);
        stadium6.setName("Name");

        FootballTeam footballTeam6 = new FootballTeam();
        footballTeam6.setId(123);
        footballTeam6.setName("Name");
        footballTeam6.setStadium(stadium6);

        Stadium stadium7 = new Stadium();
        stadium7.setCapacity(3);
        stadium7.setId(123);
        stadium7.setName("Name");

        FootballTeam footballTeam7 = new FootballTeam();
        footballTeam7.setId(123);
        footballTeam7.setName("Name");
        footballTeam7.setStadium(stadium7);
        Match match2 = mock(Match.class);
        when(match2.getDate()).thenThrow(new IllegalStateException());
        when(match2.getAway_team()).thenReturn(footballTeam7);
        when(match2.getHome_team()).thenReturn(footballTeam6);
        doNothing().when(match2).setAway_team((FootballTeam) any());
        doNothing().when(match2).setDate((Date) any());
        doNothing().when(match2).setHome_team((FootballTeam) any());
        doNothing().when(match2).setId(anyInt());
        doNothing().when(match2).setTicket_number(anyInt());
        doNothing().when(match2).setTicket_price(anyDouble());
        match2.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        match2.setHome_team(footballTeam5);
        match2.setId(123);
        match2.setTicket_number(10);
        match2.setTicket_price(10.0d);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(new MatchResponse());
        when(matchMapper.newMatchRequestToMatch((NewMatchRequest) any())).thenReturn(match2);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.saveMatch(new NewMatchRequest()));
        verify(matchMapper).newMatchRequestToMatch((NewMatchRequest) any());
        verify(match2).getAway_team();
        verify(match2).getHome_team();
        verify(match2).getDate();
        verify(match2).setAway_team((FootballTeam) any());
        verify(match2).setDate((Date) any());
        verify(match2).setHome_team((FootballTeam) any());
        verify(match2).setId(anyInt());
        verify(match2).setTicket_number(anyInt());
        verify(match2).setTicket_price(anyDouble());
    }

    /**
     * Method under test: {@link MatchServiceImpl#getMatchById(int)}
     */
    @Test
    void testGetMatchById() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        MatchResponse matchResponse = new MatchResponse();
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);
        assertSame(matchResponse, matchServiceImpl.getMatchById(123));
        verify(matchRepository).findById((Integer) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#getMatchById(int)}
     */
    @Test
    void testGetMatchById2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        when(matchMapper.matchToMatchResponse((Match) any())).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.getMatchById(123));
        verify(matchRepository).findById((Integer) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#getAllMatches()}
     */
    @Test
    void testGetAllMatches() {
        when(matchRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<MatchResponse> matchResponseList = new ArrayList<>();
        when(matchMapper.matchesToMatchesResponse((List<Match>) any())).thenReturn(matchResponseList);
        List<MatchResponse> actualAllMatches = matchServiceImpl.getAllMatches();
        assertSame(matchResponseList, actualAllMatches);
        assertTrue(actualAllMatches.isEmpty());
        verify(matchRepository).findAll();
        verify(matchMapper).matchesToMatchesResponse((List<Match>) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#getAllMatches()}
     */
    @Test
    void testGetAllMatches2() {
        when(matchRepository.findAll()).thenReturn(new ArrayList<>());
        when(matchMapper.matchesToMatchesResponse((List<Match>) any())).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.getAllMatches());
        verify(matchRepository).findAll();
        verify(matchMapper).matchesToMatchesResponse((List<Match>) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#updateMatch(MatchResponse)}
     */
    @Test
    void testUpdateMatch() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchRepository.save((Match) any())).thenReturn(match1);
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        Match match2 = new Match();
        match2.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        match2.setHome_team(footballTeam5);
        match2.setId(123);
        match2.setTicket_number(10);
        match2.setTicket_price(10.0d);
        MatchResponse matchResponse = new MatchResponse();
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(match2);
        assertSame(matchResponse, matchServiceImpl.updateMatch(new MatchResponse()));
        verify(matchRepository).save((Match) any());
        verify(matchRepository).findById((Integer) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
        verify(matchMapper).matchResponseToMatch((MatchResponse) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#updateMatch(MatchResponse)}
     */
    @Test
    void testUpdateMatch2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchRepository.save((Match) any())).thenReturn(match1);
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        Match match2 = new Match();
        match2.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        match2.setHome_team(footballTeam5);
        match2.setId(123);
        match2.setTicket_number(10);
        match2.setTicket_price(10.0d);
        when(matchMapper.matchToMatchResponse((Match) any())).thenThrow(new IllegalStateException());
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(match2);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.updateMatch(new MatchResponse()));
        verify(matchRepository).save((Match) any());
        verify(matchRepository).findById((Integer) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
        verify(matchMapper).matchResponseToMatch((MatchResponse) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#updateMatch(MatchResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateMatch3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.MatchRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.MatchServiceImpl.updateMatch(MatchServiceImpl.java:53)
        //   See https://diff.blue/R013 to resolve this issue.

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        when(matchRepository.save((Match) any())).thenReturn(match);
        when(matchRepository.findById((Integer) any())).thenReturn(null);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(new MatchResponse());
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(match1);
        matchServiceImpl.updateMatch(new MatchResponse());
    }

    /**
     * Method under test: {@link MatchServiceImpl#deleteMatchById(int)}
     */
    @Test
    void testDeleteMatchById() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);
        doNothing().when(matchRepository).delete((Match) any());
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        matchServiceImpl.deleteMatchById(123);
        verify(matchRepository).findById((Integer) any());
        verify(matchRepository).delete((Match) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#deleteMatchById(int)}
     */
    @Test
    void testDeleteMatchById2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);
        doThrow(new IllegalStateException()).when(matchRepository).delete((Match) any());
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.deleteMatchById(123));
        verify(matchRepository).findById((Integer) any());
        verify(matchRepository).delete((Match) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#deleteMatchById(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteMatchById3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.MatchRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.MatchServiceImpl.deleteMatchById(MatchServiceImpl.java:69)
        //   See https://diff.blue/R013 to resolve this issue.

        doNothing().when(matchRepository).delete((Match) any());
        when(matchRepository.findById((Integer) any())).thenReturn(null);
        matchServiceImpl.deleteMatchById(123);
    }

    /**
     * Method under test: {@link MatchServiceImpl#deleteMatchById(int)}
     */
    @Test
    void testDeleteMatchById4() {
        doNothing().when(matchRepository).delete((Match) any());
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.deleteMatchById(123));
        verify(matchRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#TicketBought(int, int)}
     */
    @Test
    void testTicketBought() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchRepository.save((Match) any())).thenReturn(match1);
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        matchServiceImpl.TicketBought(123, 1);
        verify(matchRepository).save((Match) any());
        verify(matchRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link MatchServiceImpl#TicketBought(int, int)}
     */
    @Test
    void testTicketBought2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        Optional<Match> ofResult = Optional.of(match);
        when(matchRepository.save((Match) any())).thenThrow(new IllegalStateException());
        when(matchRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> matchServiceImpl.TicketBought(123, 1));
        verify(matchRepository).save((Match) any());
        verify(matchRepository).findById((Integer) any());
    }
}

