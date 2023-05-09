package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Controller
@FxmlView
public class BetMatchCell extends ListCell<Match> {
    @FXML
    private Label homeTeamName, awayTeamName, matchResultText, matchTimeText;
    @FXML
    private Text awayTeamOddText, drawOddText, homeTeamOddText;
    @FXML
    private ImageView homeTeamLogo, awayTeamLogo;

    public BetMatchCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BetMatchCell.fxml"));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            homeTeamName.setText(item.getHomeTeam());
            awayTeamName.setText(item.getAwayTeam());
            matchResultText.setText(item.getHomeTeamScore() + ":" + item.getAwayTeamScore());
            matchTimeText.setText(item.getStartTime().format(formatter));
            homeTeamOddText.setText(String.valueOf(item.getHomeTeamOdd()));
            awayTeamOddText.setText(item.getAwayTeamOdd().toString());
            drawOddText.setText(String.valueOf(item.getDrawTeamOdd()));
            homeTeamName.setTextAlignment(TextAlignment.CENTER);
            awayTeamName.setTextAlignment(TextAlignment.CENTER);
            matchResultText.setTextAlignment(TextAlignment.CENTER);
            matchTimeText.setTextAlignment(TextAlignment.CENTER);

            homeTeamLogo.setImage(new Image(item.getHomeTeamLogo()));
            awayTeamLogo.setImage(new Image(item.getAwayTeamLogo()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}

