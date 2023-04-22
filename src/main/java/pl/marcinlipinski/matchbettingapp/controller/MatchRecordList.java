package pl.marcinlipinski.matchbettingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;

@Component
@Controller
@FxmlView
public class MatchRecordList {
    @FXML
    private ListView<Match> lista;
    @FXML
    public void initialize() {
        ObservableList<Match> data = FXCollections.observableArrayList();
        data.addAll(new Match("Cheese"), new Match("Horse"), new Match("Jam"));

        lista.setItems(data);
        lista.setCellFactory(new Callback<ListView<Match>, ListCell<Match>>() {
            @Override
            public ListCell<Match> call(ListView<Match> listView) {
                return new MatchRecordCell();
            }
        });
    }


}
