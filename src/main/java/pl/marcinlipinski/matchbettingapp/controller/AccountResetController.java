package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.service.BetService;
import pl.marcinlipinski.matchbettingapp.service.MatchService;
import pl.marcinlipinski.matchbettingapp.service.UserService;

@Component
@Controller
@FxmlView("AccountResetWindow.fxml")
public class AccountResetController {
    @FXML
    private final FxControllerAndView<MainWindowController, AnchorPane> mainWindowController;
    private final UserService userService;
    private final BetService betService;

    private final MatchService matchService;
    private Stage stage;

    @FXML
    private Button resetAccountButton;
    @FXML
    private AnchorPane dialog;
    private FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;

    public AccountResetController(FxControllerAndView<MainWindowController, AnchorPane> mainWindowController, UserService userService, BetService betService, MatchService matchService, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController) {
        this.mainWindowController = mainWindowController;
        this.userService = userService;
        this.betService = betService;
        this.matchService = matchService;
        this.summaryPaneController = summaryPaneController;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        resetAccountButton.setOnAction(
                actionEvent -> {
                    betService.deleteAll();
                    matchService.deleteAll();
                    userService.newUser();
                    summaryPaneController.getController().refreshAccountBalanceText();
                    stage.close();
                }
        );
    }

    public void show() {
        stage.show();
    }
}
