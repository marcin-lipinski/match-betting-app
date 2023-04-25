package pl.marcinlipinski.matchbettingapp.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.League;


import java.io.IOException;
import java.time.format.DateTimeFormatter;

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
        super();
        this.matchRecordList = matchRecordList;
        loadFXML();

        this.setOnMouseClicked(event -> {
            System.out.println("dupa 1");
            onActionProperty().get().handle(event);
        });
    }

    // notice we use MouseEvent here only because you call from onMouseEvent, you can substitute any type you need
    private final ObjectProperty<EventHandler<MouseEvent>> propertyOnAction = new SimpleObjectProperty<EventHandler<MouseEvent>>();

    public final ObjectProperty<EventHandler<MouseEvent>> onActionProperty() {
        System.out.println("dupa 2");
        return propertyOnAction;
    }

    public final void setOnAction(EventHandler<MouseEvent> handler) {
        System.out.println("dupa 3");
        propertyOnAction.set(handler);
    }

    public final EventHandler<MouseEvent> getOnAction() {
        System.out.println("dupa 4");
        return propertyOnAction.get();

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

    public void search(){

    }
}
