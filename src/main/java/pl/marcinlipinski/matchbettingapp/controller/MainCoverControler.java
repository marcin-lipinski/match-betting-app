package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MainCoverControler {
    @FXML
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane;
    private final FxControllerAndView<MatchRecordList, AnchorPane> matchRecordListController;
    @FXML
    private final FxControllerAndView<AccountResetControler, AnchorPane> accountResetDialog;
    @FXML
    public Button openResetAccountDialogButton;
    @FXML
    public Text accountBalanceLabel;


    public MainCoverControler(FxControllerAndView<SummaryPaneController, AnchorPane> summaryPane, FxControllerAndView<MatchRecordList, AnchorPane> matchRecordList, FxControllerAndView<AccountResetControler, AnchorPane> accountResetDialog) {
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
