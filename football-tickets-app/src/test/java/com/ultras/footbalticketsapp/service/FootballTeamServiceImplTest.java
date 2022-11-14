package com.ultras.footbalticketsapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.mapper.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.repository.FootballTeamRepository;
import com.ultras.footbalticketsapp.repository.StadiumRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FootballTeamServiceImpl.class})
@ExtendWith(SpringExtension.class)
@Disabled
class FootballTeamServiceImplTest {
    @MockBean
    private FootballTeamMapper footballTeamMapper;

    @MockBean
    private FootballTeamRepository footballTeamRepository;

    @Autowired
    private FootballTeamServiceImpl footballTeamServiceImpl;

    @MockBean
    private StadiumRepository stadiumRepository;

    /**
     * Method under test: {@link FootballTeamServiceImpl#saveFootballTeam(NewFootballTeamRequest)}
     */
    @Test
    void testSaveFootballTeam() {
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
        when(footballTeamRepository.findByName((String) any())).thenReturn(footballTeam);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam1);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);
        when(footballTeamMapper.newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any()))
                .thenReturn(footballTeam2);
        assertThrows(IllegalStateException.class,
                () -> footballTeamServiceImpl.saveFootballTeam(new NewFootballTeamRequest()));
        verify(footballTeamRepository).findByName((String) any());
        verify(footballTeamMapper).newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#saveFootballTeam(NewFootballTeamRequest)}
     */
    @Test
    void testSaveFootballTeam2() {
        when(footballTeamRepository.findByName((String) any())).thenThrow(new IllegalStateException());
        when(footballTeamRepository.save((FootballTeam) any())).thenThrow(new IllegalStateException());

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamMapper.newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any()))
                .thenReturn(footballTeam);
        assertThrows(IllegalStateException.class,
                () -> footballTeamServiceImpl.saveFootballTeam(new NewFootballTeamRequest()));
        verify(footballTeamRepository).findByName((String) any());
        verify(footballTeamMapper).newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#getFootballTeamById(int)}
     */
    @Test
    void testGetFootballTeamById() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse();
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(footballTeamResponse);
        assertSame(footballTeamResponse, footballTeamServiceImpl.getFootballTeamById(1));
        verify(footballTeamRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#getFootballTeamById(int)}
     */
    @Test
    void testGetFootballTeamById2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> footballTeamServiceImpl.getFootballTeamById(1));
        verify(footballTeamRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#getAllFootballTeams()}
     */
    @Test
    void testGetAllFootballTeams() {
        when(footballTeamRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<FootballTeamResponse> footballTeamResponseList = new ArrayList<>();
        when(footballTeamMapper.footballTeamsToFootballTeamsResponse((List<FootballTeam>) any()))
                .thenReturn(footballTeamResponseList);
        List<FootballTeamResponse> actualAllFootballTeams = footballTeamServiceImpl.getAllFootballTeams();
        assertSame(footballTeamResponseList, actualAllFootballTeams);
        assertTrue(actualAllFootballTeams.isEmpty());
        verify(footballTeamRepository).findAll();
        verify(footballTeamMapper).footballTeamsToFootballTeamsResponse((List<FootballTeam>) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#getAllFootballTeams()}
     */
    @Test
    void testGetAllFootballTeams2() {
        when(footballTeamRepository.findAll()).thenReturn(new ArrayList<>());
        when(footballTeamMapper.footballTeamsToFootballTeamsResponse((List<FootballTeam>) any()))
                .thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> footballTeamServiceImpl.getAllFootballTeams());
        verify(footballTeamRepository).findAll();
        verify(footballTeamMapper).footballTeamsToFootballTeamsResponse((List<FootballTeam>) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#getAllFootballTeams()}
     */
    @Test
    void testGetAllFootballTeams3() {
        when(footballTeamRepository.findAll()).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> footballTeamServiceImpl.getAllFootballTeams());
        verify(footballTeamRepository).findAll();
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    void testUpdateFootballTeam() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam1);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        Optional<Stadium> ofResult1 = Optional.of(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium3);
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium4);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse();
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(footballTeamResponse);
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam2);
        assertSame(footballTeamResponse, footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse()));
        verify(footballTeamRepository).save((FootballTeam) any());
        verify(footballTeamRepository).findById((Integer) any());
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
        verify(footballTeamMapper).footballTeamResponseToFootballTeam((FootballTeamResponse) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    void testUpdateFootballTeam2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam1);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        Optional<Stadium> ofResult1 = Optional.of(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium3);
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium4);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenThrow(new IllegalStateException());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam2);
        assertThrows(IllegalStateException.class,
                () -> footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse()));
        verify(footballTeamRepository).save((FootballTeam) any());
        verify(footballTeamRepository).findById((Integer) any());
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamToFootballTeamResponse((FootballTeam) any());
        verify(footballTeamMapper).footballTeamResponseToFootballTeam((FootballTeamResponse) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateFootballTeam3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.FootballTeamRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.FootballTeamServiceImpl.updateFootballTeam(FootballTeamServiceImpl.java:54)
        //   See https://diff.blue/R013 to resolve this issue.

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(null);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        Optional<Stadium> ofResult = Optional.of(stadium1);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium2);
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium3);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam1);
        footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    void testUpdateFootballTeam4() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(Optional.empty());

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        Optional<Stadium> ofResult = Optional.of(stadium1);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium2);
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium3);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam1);
        assertThrows(IllegalStateException.class,
                () -> footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse()));
        verify(footballTeamRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamResponseToFootballTeam((FootballTeamResponse) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateFootballTeam5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.StadiumRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.FootballTeamServiceImpl.updateStadium(FootballTeamServiceImpl.java:76)
        //       at com.ultras.footbalticketsapp.service.FootballTeamServiceImpl.updateFootballTeam(FootballTeamServiceImpl.java:60)
        //   See https://diff.blue/R013 to resolve this issue.

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam1);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium2);
        when(stadiumRepository.findById((Integer) any())).thenReturn(null);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium3);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam2);
        footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    void testUpdateFootballTeam6() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        Optional<FootballTeam> ofResult = Optional.of(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamRepository.save((FootballTeam) any())).thenReturn(footballTeam1);
        when(footballTeamRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium2);
        when(stadiumRepository.findById((Integer) any())).thenReturn(Optional.empty());

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium3);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam2);
        assertThrows(IllegalStateException.class,
                () -> footballTeamServiceImpl.updateFootballTeam(new FootballTeamResponse()));
        verify(footballTeamRepository).findById((Integer) any());
        verify(stadiumRepository).findById((Integer) any());
        verify(footballTeamMapper).footballTeamResponseToFootballTeam((FootballTeamResponse) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateStadium(Stadium)}
     */
    @Test
    void testUpdateStadium() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");
        Optional<Stadium> ofResult = Optional.of(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium1);
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");
        footballTeamServiceImpl.updateStadium(stadium2);
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateStadium(Stadium)}
     */
    @Test
    void testUpdateStadium2() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");
        Optional<Stadium> ofResult = Optional.of(stadium);
        when(stadiumRepository.save((Stadium) any())).thenThrow(new IllegalStateException());
        when(stadiumRepository.findById((Integer) any())).thenReturn(ofResult);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        assertThrows(IllegalStateException.class, () -> footballTeamServiceImpl.updateStadium(stadium1));
        verify(stadiumRepository).save((Stadium) any());
        verify(stadiumRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateStadium(Stadium)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateStadium3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.StadiumRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.FootballTeamServiceImpl.updateStadium(FootballTeamServiceImpl.java:76)
        //   See https://diff.blue/R013 to resolve this issue.

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium);
        when(stadiumRepository.findById((Integer) any())).thenReturn(null);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        footballTeamServiceImpl.updateStadium(stadium1);
    }

    /**
     * Method under test: {@link FootballTeamServiceImpl#updateStadium(Stadium)}
     */
    @Test
    void testUpdateStadium4() {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");
        when(stadiumRepository.save((Stadium) any())).thenReturn(stadium);
        when(stadiumRepository.findById((Integer) any())).thenReturn(Optional.empty());

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");
        assertThrows(IllegalStateException.class, () -> footballTeamServiceImpl.updateStadium(stadium1));
        verify(stadiumRepository).findById((Integer) any());
    }
}

