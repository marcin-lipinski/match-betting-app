package pl.marcinlipinski.matchbettingapp.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.repositor.BetRepository;
import pl.marcinlipinski.matchbettingapp.model.Bet;
import pl.marcinlipinski.matchbettingapp.repositor.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BetService {
    private final BetRepository betRepository;
    private final MatchService matchService;
    private final ObservableList<Bet> listOfBets;
    private final ObservableList<Match> listOfMatches;

    public BetService(BetRepository betRepository, MatchService matchService) {
        this.betRepository = betRepository;
        this.matchService = matchService;
        listOfBets = FXCollections.observableArrayList();
        listOfMatches = FXCollections.observableArrayList();
    }

    public void deleteAll(){
        betRepository.deleteAll();
    }
    public void createBet(double inputValue, double oddValue, double possibleWinValue, List<Match> matches){
        var endDate = matches.stream().map(m -> m.getStartTime().plusMinutes(150)).max(LocalDateTime::compareTo).orElse(LocalDateTime.now());
        long betId = betRepository.findAll().size();

        Bet bet = Bet.builder()
                .id(betId)
                .endDate(endDate)
                .possibleWinValue(possibleWinValue)
                .oddValue(oddValue)
                .inputValue(inputValue)
                .betDate(LocalDateTime.now()).build();
        betRepository.save(bet);
        matches.forEach(match -> match.setBetId(betId));
        matches.forEach(matchService::save);

    }

    public ObservableList<Bet> getAll(){
        listOfBets.clear();
        listOfBets.addAll(betRepository.findAll());
        return listOfBets;
    }

    public ObservableList<Match> getMatchesByBetId(long betId) {
        listOfMatches.clear();
        listOfMatches.addAll(matchService.getAllByBetId(betId));
        return listOfMatches;
    }
}
