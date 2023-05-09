package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Bet;
import pl.marcinlipinski.matchbettingapp.service.BetService;

@Component
@Controller
@FxmlView("BetList.fxml")
public class BetListController {
    protected final BetService betSerivce;
    FxControllerAndView<BetMatchListController, AnchorPane> betMatchList;
    @FXML
    private ListView<Bet> betListView;

    public BetListController(BetService betService, FxControllerAndView<BetMatchListController, AnchorPane> betMatchList) {
        this.betMatchList = betMatchList;
        this.betSerivce = betService;
    }

    @FXML
    public void initialize() {
        betListView.setCellFactory(listView -> new BetCell(this));
        searchForBets();
    }

    public void searchForBets(){
        betListView.getItems().clear();
        betListView.setItems(betSerivce.getAll());
        betListView.refresh();
    }
}
