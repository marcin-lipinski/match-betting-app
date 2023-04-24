package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import org.springframework.stereotype.Component;
import pl.marcinlipinski.matchbettingapp.service.BetService;

@Component
public class AccountResetControler {
    @FXML
    private final FxControllerAndView<MainCoverControler, AnchorPane> mainCoverControler;
    private final BetService betService;
    private Stage stage;

    @FXML
    private Button resetAccountButton;
    @FXML
    private AnchorPane dialog;

    public AccountResetControler(FxControllerAndView<MainCoverControler, AnchorPane> mainCoverControler, BetService betService) {
        this.mainCoverControler = mainCoverControler;
        this.betService = betService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        resetAccountButton.setOnAction(
                actionEvent -> {
                    betService.deleteAll();
                    mainCoverControler.getController().accountBalanceLabel.setText("0.00");
                    stage.close();
                }
        );
    }

    public void show() {
        stage.show();
    }
}
