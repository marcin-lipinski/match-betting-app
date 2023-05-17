package pl.marcinlipinski.matchbettingapp.service;

import io.joshworks.restclient.http.Json;
import io.joshworks.restclient.http.Unirest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.repository.MatchRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Service
public class MatchService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ObservableList<Match> listOfMatches;
    final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        listOfMatches = FXCollections.observableArrayList();
        this.matchRepository = matchRepository;
    }

    public ObservableList<Match> searchByLeagueId(int leagueId) {
        listOfMatches.clear();
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);

        String url = "https://sportscore1.p.rapidapi.com/events/search?sport_id=1&date_end=" + nextWeek + "&date_start=" + today + "&league_id=" + leagueId;
        var response = Unirest.post(url)
                .header("X-RapidAPI-Key", "5087cf9cb7mshe93bc99293ba390p127156jsnc0c58e47f3f4")
                .header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com").asJson().body();

        var jsonArray = responseToDataArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            listOfMatches.add(parseJSON(jsonArray.getJSONObject(i)));
        }

        return listOfMatches;
    }

    private JSONArray responseToDataArray(Json response) {
        try {
            return new JSONObject(response).getJSONArray("array").getJSONObject(0).getJSONArray("data");
        } catch (Exception ex) {
            System.out.println("API access expired.");
            return new JSONArray();
        }
    }

    public void deleteAll() {
        matchRepository.deleteAll();
    }

    private Match parseJSON(JSONObject json) {
        var match = Match.builder()
                .id(json.getLong("id"))
                .status(json.getString("status"))
                .homeTeam(json.getJSONObject("home_team").getString("name"))
                .awayTeam(json.getJSONObject("away_team").getString("name"))
                .homeTeamLogo(json.getJSONObject("home_team").getString("logo"))
                .awayTeamLogo(json.getJSONObject("away_team").getString("logo"))
                .startTime(LocalDateTime.parse(json.getString("start_at"), formatter))
                .homeTeamScore(0)
                .awayTeamScore(0)
                .winnerCode(json.getInt("winner_code"))
                .homeTeamOdd(1.00)
                .drawTeamOdd(1.00)
                .awayTeamOdd(1.00)
                .leagueName(json.getJSONObject("league").getString("name"))
                .leagueName(json.getJSONObject("league").getString("logo"))
                .bets(new HashSet<>()).build();

        if (!json.isNull("main_odds")) {
            match.setHomeTeamOdd(json.getJSONObject("main_odds").getJSONObject("outcome_1").getDouble("value"));
            match.setAwayTeamOdd(json.getJSONObject("main_odds").getJSONObject("outcome_2").getDouble("value"));
            match.setDrawTeamOdd(json.getJSONObject("main_odds").getJSONObject("outcome_X").getDouble("value"));
        }
        if (json.getString("status").equals("finished")) {
            match.setHomeTeamScore(json.getJSONObject("home_score").getInt("normal_time"));
            match.setAwayTeamScore(json.getJSONObject("home_score").getInt("normal_time"));
        }

        return match;
    }
}
