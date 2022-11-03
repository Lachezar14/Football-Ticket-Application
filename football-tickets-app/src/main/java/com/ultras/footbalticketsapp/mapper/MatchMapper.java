package com.ultras.footbalticketsapp.mapper;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.model.Match;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    Match newMatchRequestToMatch(NewMatchRequest match);
    Match matchResponseToMatch(MatchResponse match);
    MatchResponse matchToMatchResponse(Match match);
    List<MatchResponse> matchesToMatchesResponse(List<Match> matches);
}
