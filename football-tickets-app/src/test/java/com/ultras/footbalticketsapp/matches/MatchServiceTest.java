package com.ultras.footbalticketsapp.matches;


import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.mapper.MatchMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.repository.MatchRepository;
import com.ultras.footbalticketsapp.service.MatchServiceImpl;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class  MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchMapper matchMapper;

    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchServiceImpl(matchRepository, matchMapper);
    }

    @Test
    void testSaveMatch() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 100,10.0);
        NewMatchRequest newMatchRequest = new NewMatchRequest(footballTeam, footballTeam1, now, 100,10.0);
        MatchResponse matchResponse = new MatchResponse(1,footballTeam, footballTeam1, now, 100,10.0);

        //when
        when(matchMapper.newMatchRequestToMatch(any())).thenReturn(match);
        when(matchRepository.save(any(Match.class))).thenReturn(null);
        when(matchMapper.matchToMatchResponse(any(Match.class))).thenReturn(matchResponse);
        MatchResponse savedMatch = matchService.saveMatch(newMatchRequest);

        //then
        assertThat(savedMatch).isEqualTo(matchResponse);
        verify(matchMapper).newMatchRequestToMatch((NewMatchRequest) any());
        verify(matchRepository).save((Match) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
    }

    @Test
    void testSaveMatch_throwsRuntimeException_whenMatchAlreadyExists(){
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 100,10.0);
        NewMatchRequest newMatchRequest = new NewMatchRequest(footballTeam, footballTeam1, now, 100,10.0);

        //when
        when(matchMapper.newMatchRequestToMatch(any())).thenReturn(match);
        when(matchRepository.findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), any(Date.class))).thenReturn(match);

        //then
        assertThatThrownBy(() -> matchService.saveMatch(newMatchRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Match already exists");
    }

    @Test
    void testSaveMatch_throwsRuntimeException_whenMatchHomeTeamAndAwayTeamAreSame(){
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam, now, 100,10.0);
        NewMatchRequest newMatchRequest = new NewMatchRequest(footballTeam, footballTeam, now, 100,10.0);

        //when
        when(matchMapper.newMatchRequestToMatch(any())).thenReturn(match);
        when(matchRepository.findByHomeTeamAndAwayTeamAndDate(anyInt(), anyInt(), any(Date.class))).thenReturn(match);

        //then
        assertThatThrownBy(() -> matchService.saveMatch(newMatchRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Home team and away team cannot be the same");
    }

    @Test
    void testGetMatchById() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 100,10.0);
        MatchResponse matchResponse = new MatchResponse();

        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.of(match));
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);

        //then
        assertSame(matchResponse, matchService.getMatchById(1));
        verify(matchRepository).findById((Integer) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
    }

    @Test
    void testGetAllMatches(){
        //when
        matchService.getAllMatches();
        //then
        verify(matchRepository).findAll();
    }

    @Test
    void testUpdateMatch() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match matchToUpdate = new Match(1,footballTeam, footballTeam1, now, 100,10.0);

        Stadium stadium2 = new Stadium(1, "SomeName", 11);
        FootballTeam footballTeam2 = new FootballTeam(1, "SomeName", stadium2);
        Stadium stadium3 = new Stadium(2, "OtherSomeName", 12);
        FootballTeam footballTeam3 = new FootballTeam(2, "OtherSomeName", stadium3);
        Date now1 = new Date(); //return current date and time
        Match updatedMatch = new Match(2,footballTeam2, footballTeam3, now1, 110,12.0);
        MatchResponse updatedMatchResponse = new MatchResponse();

        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.of(matchToUpdate));
        when(matchRepository.save((Match) any())).thenReturn(updatedMatch);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(updatedMatchResponse);
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(updatedMatch);

        //then
        assertSame(updatedMatchResponse, matchService.updateMatch(updatedMatchResponse));
        verify(matchRepository).findById((Integer) any());
        verify(matchRepository).save((Match) any());
        verify(matchMapper).matchToMatchResponse((Match) any());
        verify(matchMapper).matchResponseToMatch((MatchResponse) any());
    }

    @Test
    void testUpdateMatch_throwsRuntimeException_whenMatchIsNull() {
        //given
        MatchResponse matchResponse = new MatchResponse();

        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> matchService.updateMatch(matchResponse))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Match not found");
        verify(matchRepository).findById((Integer) any());
    }

    @Test
    void testUpdateMatch_throwsRuntimeException_whenMatchHomeTeamAndAwayTeamAreSame(){
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam, now, 100,10.0);
        MatchResponse matchResponse = new MatchResponse(1,footballTeam, footballTeam, now, 100,10.0);

        //when
        when(matchMapper.matchResponseToMatch(any())).thenReturn(match);
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.of(match));

        //then
        assertThatThrownBy(() -> matchService.updateMatch(matchResponse))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Home team and away team cannot be the same");
    }

    @Test
    void testDeleteMatchById() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 100,10.0);

        //when
        when(matchRepository.findById((Integer) any()))
                .thenReturn(Optional.of(match))
                .thenReturn(Optional.empty());

        matchService.deleteMatchById(1);

        //then
        assertThat(matchRepository.findById(1)).isEmpty();
        verify(matchRepository,times(2)).findById((Integer) any());
    }

    @Test
    void testDeleteMatchById_throwsRuntimeException_whenMatchIsNull() {
        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> matchService.deleteMatchById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Match not found");
        verify(matchRepository).findById((Integer) any());
    }

    @Test
    void testTicketBought() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 100,10.0);

        //when
        when(matchRepository.save((Match) any())).thenReturn(match);
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.of(match));
        matchService.TicketBought(1, 3);

        //then
        assertSame(97, match.getTicket_number());
        verify(matchRepository).save((Match) any());
        verify(matchRepository).findById((Integer) any());
    }

    @Test
    void testTicketBought_throwsRuntimeException_whenMatchIsNull() {
        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> matchService.TicketBought(1, 3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Match not found");
        verify(matchRepository).findById((Integer) any());
    }

    @Test
    void testTicketBought_throwsRuntimeException_whenTicketNumberIsZero() {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        Match match = new Match(1,footballTeam, footballTeam1, now, 0,10.0);

        //when
        when(matchRepository.findById((Integer) any())).thenReturn(Optional.of(match));

        //then
        assertThatThrownBy(() -> matchService.TicketBought(1, 3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No tickets left");
        verify(matchRepository).findById((Integer) any());
    }
}
