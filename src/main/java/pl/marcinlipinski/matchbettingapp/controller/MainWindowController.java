package pl.marcinlipinski.matchbettingapp.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.marcinlipinski.matchbettingapp.service.UserService;

@Component
@FxmlView("MainWindow.fxml")
public class MainWindowController {
    @FXML
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane;
    @FXML
    private final FxControllerAndView<MatchListController, AnchorPane> matchRecordListController;
    @FXML
    private final UserService userService;
    @FXML
    private final FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog;
    private final FxControllerAndView<BetListController, AnchorPane> betListController;


    public MainWindowController(FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane, FxControllerAndView<MatchListController, AnchorPane> matchRecordList, UserService userService, FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog, FxControllerAndView<BetListController, AnchorPane> betListController) {
        this.summaryPane = summaryPane;
        this.matchRecordListController = matchRecordList;
        this.userService = userService;
        this.accountResetDialog = accountResetDialog;
        this.betListController = betListController;
    }

    @FXML
    public void initialize() {

    }

    public void refresh(Event event) {
        betListController.getController().searchForBets();
    }
}
