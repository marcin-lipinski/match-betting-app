package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("MainWindow.fxml")
public class MainWindowController {
    @FXML
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane;
    private final FxControllerAndView<MatchListController, AnchorPane> matchRecordListController;
    @FXML
    private final FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog;
    @FXML
    public Button openResetAccountDialogButton;
    @FXML
    public Text accountBalanceLabel;


    public MainWindowController(FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane, FxControllerAndView<MatchListController, AnchorPane> matchRecordList, FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog) {
        this.summaryPane = summaryPane;
        this.matchRecordListController = matchRecordList;
        this.accountResetDialog = accountResetDialog;
    }

    @FXML
    public void initialize() {
        this.openResetAccountDialogButton.setOnAction(actionEvent -> {
            accountResetDialog.getController().show();
        });


    }
}
