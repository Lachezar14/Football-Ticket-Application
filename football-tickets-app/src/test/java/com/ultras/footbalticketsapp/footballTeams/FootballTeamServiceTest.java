package com.ultras.footbalticketsapp.footballTeams;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.mapper.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.repository.FootballTeamRepository;
import com.ultras.footbalticketsapp.repository.StadiumRepository;
import com.ultras.footbalticketsapp.service.FootballTeamServiceImpl;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FootballTeamServiceTest {
    @Mock
    private FootballTeamRepository teamRepository;
    @Mock
    private StadiumRepository stadiumRepository;
    @Mock
    private FootballTeamMapper teamMapper;
    private FootballTeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new FootballTeamServiceImpl(teamRepository, stadiumRepository, teamMapper);
    }

    @Test
    void testSaveFootballTeam() {
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        NewFootballTeamRequest request = new NewFootballTeamRequest("Name",stadium);
        FootballTeam toSaveFootballTeam = new FootballTeam(1, "Name", stadium);
        FootballTeamResponse savedTeamResponse = new FootballTeamResponse(1, "Name", stadium);

        //when
        when(teamMapper.newFootballTeamRequestToFootballTeam((request))).thenReturn(toSaveFootballTeam);
        when(teamRepository.findByName((String) any())).thenReturn(null);
        when(stadiumRepository.save((stadium))).thenReturn(stadium);
        when(teamRepository.save((toSaveFootballTeam))).thenReturn(toSaveFootballTeam);
        when(teamMapper.footballTeamToFootballTeamResponse((toSaveFootballTeam))).thenReturn(savedTeamResponse);

        teamService.saveFootballTeam(request);

        //then
        assertThat(toSaveFootballTeam.getId()).isEqualTo(savedTeamResponse.getId());
        assertThat(toSaveFootballTeam.getName()).isEqualTo(savedTeamResponse.getName());
        assertThat(toSaveFootballTeam.getStadium().getId()).isEqualTo(savedTeamResponse.getStadium().getId());
        assertThat(toSaveFootballTeam.getStadium().getName()).isEqualTo(savedTeamResponse.getStadium().getName());
        assertThat(toSaveFootballTeam.getStadium().getCapacity()).isEqualTo(savedTeamResponse.getStadium().getCapacity());

        verify(teamMapper).newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any());
        verify(teamRepository).findByName((String) any());
        verify(stadiumRepository).save((Stadium) any());
        verify(teamRepository).save((FootballTeam) any());
        verify(teamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
    }

    @Test
    void saveFootballTeam_throwsIllegalStateException_whenFootballTeamAlreadyExists() {
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        NewFootballTeamRequest request = new NewFootballTeamRequest("Name",stadium);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);

        //when
        when(teamMapper.newFootballTeamRequestToFootballTeam((request))).thenReturn(footballTeam);
        when(teamRepository.findByName("Name")).thenReturn(footballTeam);

        //then
        assertThatThrownBy(() -> teamService.saveFootballTeam(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Team already exists");
    }

    @Test
    void testGetTeamById(){
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Name", stadium);

        //when
        when(teamRepository.findById((Integer) any())).thenReturn(Optional.of(footballTeam));
        when(teamMapper.footballTeamToFootballTeamResponse((footballTeam))).thenReturn(footballTeamResponse);

        //then
        assertSame(footballTeamResponse, teamService.getFootballTeamById(1));
        verify(teamRepository).findById((Integer) any());
        verify(teamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
    }

    @Test
    void testGetAllTeams(){
        //when
        teamService.getAllFootballTeams();
        //then
        verify(teamRepository).findAll();
    }

    @Test
    void testUpdateFootballTeam(){
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium updatedStadium = new Stadium(1, "stadium1", 120);
        FootballTeam updatedTeam = new FootballTeam(1, "Name1", updatedStadium);
        FootballTeamResponse toUpdate = new FootballTeamResponse(1, "Name", stadium);
        FootballTeamResponse updated = new FootballTeamResponse(1, "Name1", updatedStadium);


        //when
        when(teamRepository.findById((Integer) any())).thenReturn(Optional.of(footballTeam));
        when(teamRepository.save((FootballTeam) any())).thenReturn(updatedTeam);
        when(stadiumRepository.save((Stadium) any())).thenReturn(updatedStadium);
        when(stadiumRepository.findById((Integer) any())).thenReturn(Optional.of(stadium));
        when(teamMapper.footballTeamToFootballTeamResponse((FootballTeam) any())).thenReturn(updated);
        when(teamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any())).thenReturn(updatedTeam);

        //then
        assertSame(updated, teamService.updateFootballTeam(toUpdate));
        verify(teamRepository).save((FootballTeam) any());
        verify(teamRepository).findById((Integer) any());
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository).findById((Integer) any());
        verify(teamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
        verify(teamMapper).footballTeamResponseToFootballTeam((FootballTeamResponse) any());
    }

    @Test
    void testUpdateFootballTeam_throwsIllegalStateException_whenTeamIsNull(){
        //given
        Stadium stadium = new Stadium();
        FootballTeamResponse toUpdate = new FootballTeamResponse(1, "Name", stadium);

        //when
        when(teamRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> teamService.updateFootballTeam(toUpdate))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Team not found");
    }

   @Test
    void testDeleteFootballTeam(){
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);

        //when
        when(teamRepository.findById((Integer) any()))
                .thenReturn(Optional.of(footballTeam))
                .thenReturn(Optional.empty());

       teamService.deleteFootballTeam(1);

        //then
       assertThat(teamRepository.findById(1)).isEmpty();
       verify(teamRepository,times(2)).findById((Integer) any());
    }

    @Test
    void testDeleteFootballTeam_throwsIllegalStateException_whenTeamIsNull(){
        //given
        Stadium stadium = new Stadium();
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);

        //when
        when(teamRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> teamService.deleteFootballTeam(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Team not found");
    }

    @Test
    void testUpdateStadium(){
        //given
        Stadium stadium = new Stadium(1, "stadium", 100);
        Stadium updatedStadium = new Stadium(1, "stadium1", 120);

        //when
        when(stadiumRepository.save((Stadium) any())).thenReturn(updatedStadium);
        when(stadiumRepository.findById((Integer) any()))
                .thenReturn(Optional.of(stadium))
                .thenReturn(Optional.of(updatedStadium));

        teamService.updateStadium(stadium);

        //then
        assertThat(stadiumRepository.findById(1).get()).isEqualTo(updatedStadium);
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository,times(2)).findById((Integer) any());
    }

    @Test
    void testUpdateStadium_throwsIllegalStateException_whenStadiumIsNull(){
        //given
        Stadium stadium = new Stadium();

        //when
        when(stadiumRepository.findById((Integer) any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> teamService.updateStadium(stadium))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Stadium not found");
    }

}
