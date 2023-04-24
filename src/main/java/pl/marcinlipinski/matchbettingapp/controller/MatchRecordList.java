package pl.marcinlipinski.matchbettingapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import pl.marcinlipinski.matchbettingapp.service.MatchSerivce;

@Component
@Controller
@FxmlView
public class MatchRecordList {
    @FXML
    public ComboBox leaguesComboBox;
    @FXML
    private ListView<Match> lista;
    private final MatchSerivce matchSerivce;
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;
    private final LeagueService leagueService;
    private League selectedLeague;

    public MatchRecordList(MatchSerivce matchSerivce, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController, LeagueService leagueService) {
        this.matchSerivce = matchSerivce;
        this.summaryPaneController = summaryPaneController;
        this.leagueService = leagueService;
    }

    @FXML
    public void initialize() {
        lista.setItems(matchSerivce.post(8911));
        lista.setCellFactory(listView -> new MatchRecordCell(summaryPaneController));
        leaguesComboBox.setButtonCell(new LeaguesCell(this));
////        leaguesComboBox.setOnAction(actionEvent -> {
////            var selected = (League)leaguesComboBox.getSelectionModel().getSelectedItem();
////            searchForMatches(selected.getId());
////        });
//        leaguesComboBox.setItems(leagueService.getAllLeagues());
//        leaguesComboBox.getSelectionModel().selectFirst();
//        leaguesComboBox.setCellFactory(comboBox -> new LeaguesCell(this));
//        searchForMatches(leagueService.getAllLeagues().get(0).getId());
    }

    @FXML
    public void searchForMatches(int leagueId){
        ObservableList<Match> data = matchSerivce.post(leagueId);
        lista.setItems(data);
    }
}
