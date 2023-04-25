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

@Component
@Controller
@FxmlView("AccountResetWindow.fxml")
public class AccountResetController {
    @FXML
    private final FxControllerAndView<MainWindowController, AnchorPane> mainWindowController;
    private final BetService betService;

    private final MatchService matchService;
    private Stage stage;

    @FXML
    private Button resetAccountButton;
    @FXML
    private AnchorPane dialog;

    public AccountResetController(FxControllerAndView<MainWindowController, AnchorPane> mainWindowController, BetService betService, MatchService matchService) {
        this.mainWindowController = mainWindowController;
        this.betService = betService;
        this.matchService = matchService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        resetAccountButton.setOnAction(
                actionEvent -> {
                    betService.deleteAll();
                    matchService.deleteAll();
                    mainWindowController.getController().accountBalanceLabel.setText("20.00");
                    stage.close();
                }
        );
    }

    public void show() {
        stage.show();
    }
}
