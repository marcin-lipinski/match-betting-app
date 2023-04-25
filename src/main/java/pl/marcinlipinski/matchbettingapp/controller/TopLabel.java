package pl.marcinlipinski.matchbettingapp.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.service.UserService;

@Controller
@Component
@FxmlView("TopLabel.fxml")
public class TopLabel {
    @FXML
    public Text accountBalanceText;
    @FXML
    public Button openResetAccountDialogButton;
    @FXML Button b;

    private final FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog;
    private final UserService userService;

    public TopLabel(FxControllerAndView<AccountResetController, AnchorPane> accountResetDialog, UserService userService) {
        this.accountResetDialog = accountResetDialog;
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        openResetAccountDialogButton.setOnAction(actionEvent -> {
            accountResetDialog.getController().show();
        });
    }
    @FXML
    public void refreshAccountBalanceText(){
        Thread thread = new Thread(() ->{
            var value = userService.getAccountValue();
            Platform.runLater(() -> accountBalanceText.setText(String.valueOf(value)));
        });
        thread.start();
    }
}
