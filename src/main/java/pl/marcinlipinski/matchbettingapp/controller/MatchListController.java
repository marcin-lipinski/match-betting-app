package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.League;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.service.LeagueService;
import pl.marcinlipinski.matchbettingapp.service.MatchService;

@Component
@Controller
@FxmlView("MatchCellList.fxml")
public class MatchListController {
    @FXML
    public ComboBox leaguesComboBox;
    @FXML
    private ListView<Match> lista;
    private final MatchService matchSerivce;
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;
    private final LeagueService leagueService;

    public MatchListController(MatchService matchSerivce, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController, LeagueService leagueService) {
        this.matchSerivce = matchSerivce;
        this.summaryPaneController = summaryPaneController;
        this.leagueService = leagueService;
    }

    @FXML
    public void initialize() {
        lista.setCellFactory(listView -> new MatchCell(summaryPaneController));

        leaguesComboBox.setItems(leagueService.getAllLeagues());
        leaguesComboBox.setCellFactory(comboBox -> new LeaguesCell(this));
        leaguesComboBox.setButtonCell(new LeaguesCell(this));


        leaguesComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            searchForMatches(((League)newValue).getId());
        });

        leaguesComboBox.getSelectionModel().selectFirst();
        searchForMatches(((League)leaguesComboBox.getValue()).getId());
    }

    public void searchForMatches(int leagueId){
        lista.getItems().clear();
        lista.setItems(matchSerivce.post(leagueId));
        lista.refresh();
    }
}
