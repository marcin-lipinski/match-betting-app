package pl.marcinlipinski.matchbettingapp.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.repositor.BetRepository;
import pl.marcinlipinski.matchbettingapp.model.Bet;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BetService {
    private final BetRepository betRepository;
    private ObservableList<Bet> listOfBets;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
        listOfBets = FXCollections.observableArrayList();
    }

    public void deleteAll(){
        betRepository.deleteAll();
    }
    public void createBet(double betValue, double potentialWinValue, List<Match> matches){
        var endDate = matches.stream().map(m -> m.getStartTime().plusMinutes(150)).max(LocalDateTime::compareTo).get();
        var matchIds = matches.stream().mapToInt(Match::getId).toArray();
        Bet bet = Bet.builder().betValue(betValue).endDate(endDate).matchesList(matchIds).potentialWinValue(potentialWinValue).build();

        betRepository.save(bet);

        betRepository.findAll().forEach(b -> System.out.println(b.getId() + " " + b.getEndDate() + " " + b.getPotentialWinValue() + " " + b.getBetValue()  + " " +  Arrays.stream(b.getMatchesList()).asDoubleStream().toString()));
    }

    public ObservableList<Bet> getAll(){
        listOfBets.clear();
        listOfBets.addAll(betRepository.findAll());
        return listOfBets;
    }
}
