package pl.marcinlipinski.matchbettingapp.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;

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
    private double odd;
    private HashMap<Integer, Double> matches;


    @FXML
    public void initialize() {
        matches = new HashMap<>();
        odd = 0.00;
    }

    public void addMatch(int id,double value){
        matches.remove(id);
        matches.put(id, value);
        calculateOdd();
    }

    public void removeMatch(int id){
        matches.remove(id);
    }

    private void calculateOdd(){
        odd = 0.00;
        for(var matchId : matches.keySet()){
            if(odd == 0) odd += 0.9 * matches.get(matchId);
            else odd *= 0.9 * matches.get(matchId);;
        }
        currentOddText.setText(String.valueOf(odd));
    }

}
