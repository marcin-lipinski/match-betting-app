package pl.marcinlipinski.matchbettingapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import pl.marcinlipinski.matchbettingapp.service.UserService;

import java.util.HashMap;

@Component
@Controller
@FxmlView
public class SummaryPaneController {
    private final FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog;
    private final FxControllerAndView<MainWindowController, AnchorPane> mainWindowController;
    @FXML
    public Text possibleWinValueText;
    @FXML
    public TextField bidTextField;
    @FXML
    private Text oddValueText;
    @FXML
    public Button createBetButton;
    private double oddValue;
    private double inputValue;
    private double possibleWinValue;
    private HashMap<Match, String> matches;
    private final BetService betService;
    private final UserService userService;
    @FXML
    public Button openResetAccountDialogButton;
    @FXML
    private Text accountBalanceText;
    private FxControllerAndView<MatchListController, Node> matchListController;

    public SummaryPaneController(FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog, FxControllerAndView<MatchListController, AnchorPane> matchListController, FxControllerAndView<MainWindowController, AnchorPane> mainWindowController, BetService betService, UserService userService, FxControllerAndView<MatchListController, Node> matchListController1) {
        this.mainWindowController = mainWindowController;
        this.betService = betService;
        this.userService = userService;
        this.accountResetDialog = accountResetDialog;
        this.matchListController = matchListController1;
    }

    @FXML
    public void initialize() {
        createBetButton.setDisable(true);
        openResetAccountDialogButton.setOnAction(this::handle);
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
            setTexts();
        });
        refreshAccountBalanceText();
    }
    
    private void setTexts(){
        var inp = bidTextField.getText();
        inputValue = Double.parseDouble(inp.isEmpty() ? "0.0" : inp);
        possibleWinValue = oddValue * inputValue;

        if(inputValue > 0) createBetButton.setDisable(false);
        possibleWinValueText.setText(format(possibleWinValue));
        oddValueText.setText(format(oddValue));

        System.out.println(oddValue + " " + possibleWinValue + " " + inputValue + " " + matches.size());
    }

    private void createBet(){
        var prevBalance = userService.getAccountValue();
        if(prevBalance < inputValue) return;
        if(matches.size() > 0){
            userService.decreaseAccountBalance(inputValue);
            refreshAccountBalanceText();
            betService.createBet(inputValue, oddValue, possibleWinValue, matches.keySet().stream().toList());
        }
    }

    public void addMatch(Match match, String type){
        matches.remove(match);
        matches.put(match, type);
        calculateOdd();
        setTexts();
    }

    public boolean doContain(Match match, String c){
        return matches.containsKey(match) && matches.get(match).equals(c);
    }

    public void removeMatch(Match match){
        matches.remove(match);
        calculateOdd();
        setTexts();
    }

    private void calculateOdd(){
        oddValue = 0.00;
        for(var match : matches.keySet()){
            if(oddValue == 0.00) oddValue += 0.9 * getOddByType(match, matches.get(match));
            else oddValue *= 0.9 * getOddByType(match, matches.get(match));
        }
        setTexts();
    }

    private double getOddByType(Match match, String type){
        return switch (type) {
            case "1" -> match.getHomeTeamOdd();
            case "2" -> match.getAwayTeamOdd();
            case "X" -> match.getDrawTeamOdd();
            default -> 1.0;
        };
    }

    @FXML
    public void refreshAccountBalanceText(){
        Thread thread = new Thread(() ->{
            var value = userService.getAccountValue();
            Platform.runLater(() -> accountBalanceText.setText(String.valueOf(value)));
        });
        thread.start();
    }

    private void handle(ActionEvent actionEvent) {
        accountResetDialog.getController().show();
    }

    private String format(double v){
        int fv = (int) (v * 100);
        if(fv <= 0) return "0.0";
        double fd = (double)fv/100;
        return String.valueOf(fd);
    }
}
