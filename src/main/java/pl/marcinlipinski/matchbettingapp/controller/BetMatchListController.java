package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.service.BetService;

@Component
@Controller
@FxmlView("BetMatchList.fxml")
public class BetMatchListController {
    @FXML
    public ListView<Match> betMatchListView;
    private Stage stage;

    @FXML
    private AnchorPane dialog;
    private BetService betService;

    public BetMatchListController(BetService betService) {
        this.betService = betService;
    }

    public void loadData(long betId){
        betMatchListView.getItems().clear();
        betMatchListView.setItems(betService.getMatchesByBetId(betId));
        betMatchListView.refresh();
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        betMatchListView.setCellFactory(listView -> new MatchInBetList());
    }

    public void show() {
        stage.show();
    }
}
