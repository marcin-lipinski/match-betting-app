package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.service.BetService;

import java.util.HashMap;

@Component
@Controller
@FxmlView
public class SummaryPaneController {
    @FXML
    protected TextField bidTextField;
    @FXML
    private Text currentOddText;
    @FXML
    private Text potentialWinText;
    @FXML
    private Text potentialWinValueText;
    @FXML
    private Button createBetButton;
    private double odd;
    private HashMap<Match, Double> matches;
    private BetService betService;
    private FxControllerAndView<MainCoverControler, AnchorPane> mainCoverControler;

    public SummaryPaneController(BetService betService, FxControllerAndView<MainCoverControler, AnchorPane> mainCoverControler) {
        this.betService = betService;
        this.mainCoverControler = mainCoverControler;
    }


    @FXML
    public void initialize() {
        matches = new HashMap<>();
        odd = 0.00;
        createBetButton.setOnMouseClicked(mouseEvent -> createBet());
    }

    private void createBet(){
        odd = 0.00;
        var value = Double.parseDouble(bidTextField.getText());
        if(matches.size() > 0) betService.createBet(value, odd, matches.keySet().stream().toList());
    }

    public void addMatch(Match match, double value){
        matches.remove(match);
        matches.put(match, value);
        calculateOdd();
    }

    public void removeMatch(Match match){
        matches.remove(match);
        calculateOdd();
    }

    private void calculateOdd(){
        odd = 0.00;
        for(var matchId : matches.keySet()){
            if(odd == 0.00) odd += 0.9 * matches.get(matchId);
            else odd *= 0.9 * matches.get(matchId);
        }
        currentOddText.setText(String.valueOf(odd));
    }

}
