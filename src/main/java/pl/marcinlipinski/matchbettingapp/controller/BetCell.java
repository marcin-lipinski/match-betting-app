package pl.marcinlipinski.matchbettingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Bet;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Controller
@FxmlView
public class BetCell extends ListCell<Bet> {
    @FXML
    private Text betDateText, insetValueText, potentialWinValueText, oddValueText, idValueText;
    BetListController betListController;

    public BetCell(BetListController betListController) {
        this.betListController = betListController;
        loadFXML();
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    if (this.getStyleClass().contains("content")) {
                        betListController.betMatchList.getController().show();
                        betListController.betMatchList.getController().loadData(this.itemProperty().get().getId());
                    }
                }
            }
        });
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BetCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    protected void updateItem(Bet item, boolean empty) {
        super.updateItem(item, empty);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            this.getStyleClass().add("content");
            idValueText.setText("#" + format(item.getId()));
            betDateText.setText(item.getEndDate().format(formatter));
            oddValueText.setText(format(item.getOddValue()));
            potentialWinValueText.setText(format(item.getPossibleWinValue()));
            insetValueText.setText(format(item.getInputValue()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    private String format(double v) {
        int fv = (int) (v * 100);
        if (fv <= 0) return "0.0";
        Double dv = ((double) fv) / 100;
        return String.valueOf(dv);
    }
}