package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("MainWindow.fxml")
public class MainWindowController {
    @FXML
    private final FxControllerAndView<BetListController, AnchorPane> betListController;


    public MainWindowController(FxControllerAndView<BetListController, AnchorPane> betListController) {
        this.betListController = betListController;
    }

    @FXML
    public void initialize() {}

    public void refresh() {
        betListController.getController().searchForBets();
    }
}
