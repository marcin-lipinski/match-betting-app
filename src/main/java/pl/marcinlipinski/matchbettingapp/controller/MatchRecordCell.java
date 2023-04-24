package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.io.IOException;
import java.util.HashMap;

@Controller
@FxmlView
public class MatchRecordCell extends ListCell<Match> {
    @FXML
    private Text homeTeamName;
    @FXML
    private Text awayTeamName;
    @FXML
    private Text matchResultText;
    @FXML
    private Text homeTeamOddText;
    @FXML
    private Text awayTeamOddText;
    @FXML
    private Text drawOddText;
    @FXML
    private Text matchTimeText;
    @FXML
    private Button drawOddButton;
    @FXML
    private Button awayTeamOddButton;
    @FXML
    private Button homeTeamOddButton;
    @FXML
    HashMap<Button, Text> buttons;
    @FXML
    private ImageView homeTeamLogo;
    @FXML
    private ImageView awayTeamLogo;
    private Button selected;
    String cstyle = String.format("-fx-background-color: %s;", "#007acc");
    String ustyle = String.format("-fx-background-color: %s;", "#ffffff");

    private FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;

    public MatchRecordCell(FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController) {
        this.summaryPaneController = summaryPaneController;
        loadFXML();
        buttons = new HashMap<>();
        buttons.put(homeTeamOddButton, homeTeamOddText);
        buttons.put(awayTeamOddButton, awayTeamOddText);
        buttons.put(drawOddButton, drawOddText);
        initializeButtons();
    }

    private void initializeButtons(){
        for(var button : buttons.keySet()){
            button.setOnAction(actionEvent -> {
                button.setStyle(ustyle);
                if(selected != button) addOdd(button);
                else removeOdd();
            });
        }
    }

    private void addOdd(Button button){
        summaryPaneController.getController().addMatch(this.itemProperty().get().getId(), Double.parseDouble(buttons.get(selected).getText()));
        selected.setStyle(cstyle);
        selected = button;
    }

    private void removeOdd(){
        selected = null;
        summaryPaneController.getController().removeMatch(this.itemProperty().get().getId());
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchRecord.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    protected void updateItem(Match item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            homeTeamName.setText(item.getHomeTeam());
            awayTeamName.setText(item.getAwayTeam());
            matchResultText.setText(item.getHomeTeamScore() + ":" + item.getAwayTeamScore());
            matchTimeText.setText(item.getStartTime().toString());
            homeTeamOddText.setText(item.getHomeTeamOdd().toString());
            awayTeamOddText.setText(item.getAwayTeamOdd().toString());
            drawOddText.setText(item.getDrawTeamOdd().toString());

            homeTeamLogo.setImage(new Image(item.getHomeTeamLogo()));
            awayTeamLogo.setImage(new Image(item.getAwayTeamLogo()));


            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
