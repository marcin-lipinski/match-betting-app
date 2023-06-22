package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    private final BetService betService;
    @FXML
    public Text possibleWinValueText, oddValueText;
    @FXML
    public TextField bidTextField;
    @FXML
    public Button createBetButton;
    private double oddValue, inputValue, possibleWinValue;
    private HashMap<Match, String> matches;

    public SummaryPaneController(BetService betService) {
        this.betService = betService;
    }

    @FXML
    public void initialize() {
        createBetButton.setDisable(true);
        matches = new HashMap<>();
        oddValue = 0.00;
        createBetButton.setOnMouseClicked(mouseEvent -> createBet());
        bidTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0123456789.]*")) {
                bidTextField.setText(newValue.replaceAll("\\D", ""));
            }
            if (oldValue.contains(".") && newValue.chars().filter(ch -> ch == '.').count() > 1) {
                bidTextField.setText(oldValue);
            }
            if (oldValue.contains(".") && newValue.length() >= oldValue.length()) {
                if (oldValue.indexOf(".") == oldValue.length() - 3) bidTextField.setText(oldValue);
            }
            setTexts();
        });
    }

    private void setTexts() {
        var inp = bidTextField.getText();
        inputValue = Double.parseDouble(inp.isEmpty() ? "0.0" : inp);
        possibleWinValue = oddValue * inputValue;

        if (inputValue > 0) createBetButton.setDisable(false);
        possibleWinValueText.setText(format(possibleWinValue));
        oddValueText.setText(format(oddValue));
    }

    private void createBet() {
        if (matches.size() > 0) {
            betService.createBet(inputValue, oddValue, possibleWinValue, matches.keySet().stream().toList());
            createBetButton.setDisable(true);
            bidTextField.setText("Your input");
        }
    }

    public void addMatch(Match match, String type) {
        matches.remove(match);
        matches.put(match, type);
        calculateOdd();
        setTexts();
    }

    public boolean doContain(Match match, String c) {
        return matches.containsKey(match) && matches.get(match).equals(c);
    }

    public void removeMatch(Match match) {
        matches.remove(match);
        calculateOdd();
        setTexts();
    }

    private void calculateOdd() {
        oddValue = 0.00;
        for (var match : matches.keySet()) {
            if (oddValue == 0.00) oddValue += 0.9 * getOddByType(match, matches.get(match));
            else oddValue *= 0.9 * getOddByType(match, matches.get(match));
        }
        setTexts();
    }

    private double getOddByType(Match match, String type) {
        return switch (type) {
            case "1" -> match.getHomeTeamOdd();
            case "2" -> match.getAwayTeamOdd();
            case "X" -> match.getDrawTeamOdd();
            default -> 1.0;
        };
    }

    private String format(double v) {
        int fv = (int) (v * 100);
        if (fv <= 0) return "0.0";
        double fd = (double) fv / 100;
        return String.valueOf(fd);
    }
}
