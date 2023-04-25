package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Bet;
import pl.marcinlipinski.matchbettingapp.model.League;
import pl.marcinlipinski.matchbettingapp.service.BetService;
import pl.marcinlipinski.matchbettingapp.service.LeagueService;
import pl.marcinlipinski.matchbettingapp.service.MatchSerivce;

@Component
@Controller
@FxmlView
public class BetListController {
    @FXML
    private ListView<Bet> betListView;
    private final BetService betSerivce;
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;

    public BetListController(BetService betService, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController, LeagueService leagueService) {
        this.summaryPaneController = summaryPaneController;
        this.betSerivce = betService;
    }

    @FXML
    public void initialize() {
        betListView.setCellFactory(listView -> new BetCell(summaryPaneController));
        searchForBets();
    }

    public void searchForBets(){
        betListView.getItems().clear();
        betListView.setItems(betSerivce.getAll());
        betListView.refresh();
    }
}
