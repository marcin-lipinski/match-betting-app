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
import pl.marcinlipinski.matchbettingapp.service.MatchService;
import java.util.HashMap;

@Component
@Controller
@FxmlView
public class SummaryPaneController {
    @FXML
    public Text possibleWinValueText;

    @FXML
    private TextField bidTextField;
    @FXML
    private Text oddValueText;
    @FXML
    private Button createBetButton;
    private double oddValue;
    private double inputValue;
    private double possibleWinValue;
    private HashMap<Match, Double> matches;
    private final BetService betService;
    private final FxControllerAndView<MainWindowController, AnchorPane> mainWindowControler;
    private final MatchService matchSerivce;
    private Text inputValueText;

    public SummaryPaneController(BetService betService, FxControllerAndView<MainWindowController, AnchorPane> mainWindowControler, MatchService matchSerivce) {
        this.betService = betService;
        this.mainWindowControler = mainWindowControler;
        this.matchSerivce = matchSerivce;
    }


    @FXML
    public void initialize() {
        matches = new HashMap<>();
        oddValue = 0.00;
        createBetButton.setOnMouseClicked(mouseEvent -> createBet());
        bidTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0123456789.]*")) {
                bidTextField.setText(newValue.replaceAll("\\D", ""));
            }
            if(oldValue.contains(".") && newValue.chars().filter(ch -> ch=='.').count() > 1){
                bidTextField.setText(oldValue);
            }
            if(oldValue.contains(".") && newValue.length() >= oldValue.length()){

                if(oldValue.indexOf(".") == oldValue.length() - 3) bidTextField.setText(oldValue);
            }
            if(oldValue.isEmpty()) return;
            inputValue = Double.parseDouble(bidTextField.getText());
            possibleWinValue = oddValue * inputValue;
            setTexts(possibleWinValue, oddValue, inputValue);
        });
    }
    
    private void setTexts(double pW, double oV, double iV){
        possibleWinValueText.setText(String.valueOf(pW));
        oddValueText.setText(String.valueOf(oV));
    }

    private void createBet(){
        var prevBalance = Double.parseDouble(mainWindowControler.getController().accountBalanceLabel.getText());
        if(prevBalance >= inputValue) return;
        if(matches.size() > 0){
            mainWindowControler.getController().accountBalanceLabel.setText(String.valueOf(prevBalance - inputValue));
            var result = betService.createBet(inputValue, oddValue, matches.keySet().stream().toList());
            matchSerivce.saveAll(result);
        }
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
        oddValue = 0.00;
        for(var matchId : matches.keySet()){
            if(oddValue == 0.00) oddValue += 0.9 * matches.get(matchId);
            else oddValue *= 0.9 * matches.get(matchId);
        }
        oddValueText.setText(String.valueOf(oddValue));
    }

}
