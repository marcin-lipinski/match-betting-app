package pl.marcinlipinski.matchbettingapp.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.League;
import pl.marcinlipinski.matchbettingapp.repositor.LeagueRepository;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public ObservableList<League> getAllLeagues(){
        return FXCollections.observableArrayList(leagueRepository.findAll());
    }
}
