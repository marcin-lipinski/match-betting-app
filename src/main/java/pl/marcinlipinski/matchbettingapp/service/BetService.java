package pl.marcinlipinski.matchbettingapp.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.repositor.BetRepository;
import pl.marcinlipinski.matchbettingapp.model.Bet;
import pl.marcinlipinski.matchbettingapp.repositor.MatchRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class BetService {
    private final BetRepository betRepository;
    private final MatchRepository matchRepository;
    private ObservableList<Bet> listOfBets;

    public BetService(BetRepository betRepository, MatchRepository matchRepository) {
        this.betRepository = betRepository;
        this.matchRepository = matchRepository;
        listOfBets = FXCollections.observableArrayList();
    }

    public void deleteAll(){
        betRepository.deleteAll();
    }
    public List<Match> createBet(double betValue, double potentialWinValue, List<Match> matches){
        var endDate = matches.stream().map(m -> m.getStartTime().plusMinutes(150)).max(LocalDateTime::compareTo).get();
        long betId = betRepository.findAll().size() + 1;

        Bet bet = new Bet();
        bet.setBetValue(betValue);
        bet.setEndDate(endDate);
        bet.setPotentialWinValue(potentialWinValue);
        matches.forEach(match -> match.setBetId(betId));
        betRepository.save(bet);

        return matches;
    }

    public ObservableList<Bet> getAll(){
        listOfBets.clear();
        listOfBets.addAll(betRepository.findAll());
        return listOfBets;
    }

    public ObservableList<Match> getMatchesByBetId(long betId) {
        var result = matchRepository.findAll().stream().filter(m -> m.getBetId()==betId).toList();
        return FXCollections.observableArrayList(result);
    }
}
