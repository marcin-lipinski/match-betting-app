package pl.marcinlipinski.matchbettingapp.service;


import io.joshworks.restclient.http.Unirest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchSerivce {
    String apiURL = "https://sportscore1.p.rapidapi.com/leagues/search";

    public ObservableList<Match> post(){
        ObservableList<Match> result = FXCollections.observableArrayList();
        var response = Unirest.post("https://sportscore1.p.rapidapi.com/events/search?sport_id=1&date_end=2023-04-21&date_start=2023-04-20&league_id=8911")
                .header("X-RapidAPI-Key", "c59b703d6emsh4913a0e90fbfc7bp15ef3bjsnd80a5991c64e")
                .header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com").asJson().body();



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try{
            var json = new JSONObject(response).getJSONArray("array").getJSONObject(0).getJSONArray("data");

            for(int i = 0 ; i < json.length(); i++){
                var j = json.getJSONObject(i);
                result.add(Match.builder()
                        .id(j.getInt("id"))
                        .status(j.getString("status"))
                        .homeTeam(j.getJSONObject("home_team").getString("name"))
                        .awayTeam(j.getJSONObject("away_team").getString("name"))
                        .homeTeamLogo(j.getJSONObject("home_team").getString("logo"))
                        .awayTeamLogo(j.getJSONObject("away_team").getString("logo"))
                        .startTime(LocalDate.parse(j.getString("start_at"), formatter))
                        .homeTeamScore(j.getJSONObject("home_score").getInt("normal_time"))
                        .awayTeamScore(j.getJSONObject("away_score").getInt("normal_time"))
                        .winnerCode(j.getInt("winner_code"))
                        .homeTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_1").getDouble("value"))
                        .drawTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_X").getDouble("value"))
                        .awayTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_2").getDouble("value"))
                        .leagueName(j.getJSONObject("league").getString("name"))
                        .leagueName(j.getJSONObject("league").getString("logo")).build());
            }
        }catch(Exception e){
            return result;
        }

        return result;
    }
}
