package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
@FxmlView
public class SummaryPaneController {
    @FXML
    private TextField bidTextField;
    private Text currentOddText;
    private Text potentailWinText;
    private Text potentialWinValueText;
}
