package pl.marcinlipinski.matchbettingapp.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.service.MatchSerivce;

@Component
@Controller
@FxmlView
public class MatchRecordList {
    @FXML
    private ListView<Match> lista;
    private final MatchSerivce matchSerivce;
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;

    public MatchRecordList(MatchSerivce matchSerivce, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController) {
        this.matchSerivce = matchSerivce;
        this.summaryPaneController = summaryPaneController;
    }

    @FXML
    public void initialize() {
        ObservableList<Match> data;
        data = matchSerivce.post();

        lista.setItems(data);
        lista.setCellFactory(listView -> new MatchRecordCell(summaryPaneController));
    }


}
