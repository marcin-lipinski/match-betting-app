package pl.marcinlipinski.matchbettingapp.service;

import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.repository.BetRepository;
import pl.marcinlipinski.matchbettingapp.model.Bet;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Transactional
    public void createBet(double inputValue, double oddValue, double possibleWinValue, List<Match> matches) {
        var endDate = matches.stream().map(m -> m.getStartTime().plusMinutes(150)).max(LocalDateTime::compareTo).orElse(LocalDateTime.now());

        Bet bet = Bet.builder()
                .matches(new HashSet<>())
                .endDate(endDate)
                .possibleWinValue(possibleWinValue)
                .oddValue(oddValue)
                .inputValue(inputValue)
                .betDate(LocalDateTime.now()).build();

        bet = betRepository.save(bet);

        for (var match : matches) {
            match.addBet(bet);
            matchService.matchRepository.save(match);
        }
    }

    public ObservableList<Bet> getAll() {
        listOfBets.clear();
        listOfBets.addAll(betRepository.findAll());
        return listOfBets;
    }

    public ObservableList<Match> getMatchesByBetId(Long betId) {
        listOfMatches.clear();
        listOfMatches.addAll(betRepository.findById(betId).get().getMatches());
        return listOfMatches;
    }
}
