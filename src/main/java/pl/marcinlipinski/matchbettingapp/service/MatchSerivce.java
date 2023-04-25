package pl.marcinlipinski.matchbettingapp.service;


import io.joshworks.restclient.http.Unirest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchSerivce {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ObservableList<Match> listOfMatches;

    public MatchSerivce(){
        listOfMatches = FXCollections.observableArrayList();
    }

    public ObservableList<Match> post(int leagueId){
        listOfMatches.clear();
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);

        String url = "https://sportscore1.p.rapidapi.com/events/search?sport_id=1&date_end=" + nextWeek + "&date_start=" + today + "&league_id=" + leagueId;
        var response = Unirest.post(url)
                .header("X-RapidAPI-Key", "5f1ba88bacmsha7c19bc0fb48590p1da4aajsn85ad845d8314")
                .header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com").asJson().body();

        try{
            var json = new JSONObject(response).getJSONArray("array").getJSONObject(0).getJSONArray("data");

            for(int i = 0 ; i < json.length(); i++){
                var j = json.getJSONObject(i);
                listOfMatches.add(Match.builder()
                        .id(j.getInt("id"))
                        .status(j.getString("status"))
                        .homeTeam(j.getJSONObject("home_team").getString("name"))
                        .awayTeam(j.getJSONObject("away_team").getString("name"))
                        .homeTeamLogo(j.getJSONObject("home_team").getString("logo"))
                        .awayTeamLogo(j.getJSONObject("away_team").getString("logo"))
                        .startTime(LocalDateTime.parse(j.getString("start_at"), formatter))
                        .homeTeamScore(0)
                        .awayTeamScore(0)
                        .winnerCode(j.getInt("winner_code"))
                        .homeTeamOdd(1.00)
                        .drawTeamOdd(1.00)
                        .awayTeamOdd(1.00)
                        .leagueName(j.getJSONObject("league").getString("name"))
                        .leagueName(j.getJSONObject("league").getString("logo")).build());

                if(!j.isNull("main_odds")){
                    listOfMatches.get(i).setHomeTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_1").getDouble("value"));
                    listOfMatches.get(i).setAwayTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_2").getDouble("value"));
                    listOfMatches.get(i).setDrawTeamOdd(j.getJSONObject("main_odds").getJSONObject("outcome_X").getDouble("value"));
                }
            }
        }catch(Exception e){
            System.out.println(e);
            return listOfMatches;
        }
        System.out.println(listOfMatches.size());
        return listOfMatches;
    }
}
