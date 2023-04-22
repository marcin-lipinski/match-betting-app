package pl.marcinlipinski.matchbettingapp.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pl.marcinlipinski.matchbettingapp.model.Match;

public class MatchCellFactory implements Callback<ListView<Match>, ListCell<Match>> {

    @Override
    public ListCell<Match> call(ListView<Match> param) {
        return new MatchRecordCell();
    }
}
