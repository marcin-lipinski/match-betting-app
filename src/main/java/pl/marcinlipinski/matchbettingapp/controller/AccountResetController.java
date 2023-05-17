package pl.marcinlipinski.matchbettingapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private final UserService userService;
    private final BetService betService;
    private final MatchService matchService;
    private final FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController;
    @FXML
    public Button exitButton, resetAccountButton;
    private Stage stage;
    @FXML
    private AnchorPane dialog;

    public AccountResetController(UserService userService, BetService betService, MatchService matchService, FxControllerAndView<SummaryPaneController, AnchorPane> summaryPaneController) {
        this.userService = userService;
        this.betService = betService;
        this.matchService = matchService;
        this.summaryPaneController = summaryPaneController;
    }

    @FXML
    public void initialize() {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
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
        exitButton.setOnAction(actionEvent -> stage.close());
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if (!stage.isFocused())
                Platform.runLater(() -> stage.requestFocus());
        });
    }
}
