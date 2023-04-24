package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Cell;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.League;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@FxmlView
public class LeaguesCell extends ListCell<League> {
    MatchRecordList matchRecordList;
    @FXML
    private ImageView leagueLogo;
    @FXML
    private Text leagueName;
    @FXML
    private AnchorPane anch;
    public LeaguesCell(MatchRecordList matchRecordList) {
        this.matchRecordList = matchRecordList;
        loadFXML();
        anch.setOnMouseClicked(event -> matchRecordList.searchForMatches(this.itemProperty().get().getId()));
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LeagueCell.fxml"));
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
    protected void updateItem(League item, boolean empty) {
        super.updateItem(item, empty);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            leagueName.setText(item.getName());
            leagueLogo.setImage(new Image(item.getLogo()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
