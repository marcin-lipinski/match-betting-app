package pl.marcinlipinski.matchbettingapp.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Bet;
import pl.marcinlipinski.matchbettingapp.model.Match;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
@Controller
@FxmlView
public class BetCell extends ListCell<Bet> {
    @FXML
    private Text betDateText, insetValueText, potentialWinValueText, oddValueText, idValueText;
    @FXML
    private Button refreshButton;

    BetListController betListController;

    public BetCell(BetListController betListController) {
        this.betListController = betListController;
        loadFXML();
        this.setOnMouseClicked(mouseEvent ->
            {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        this.betListController.betMatchList.getController().loadData(this.itemProperty().get().getId());
                        this.betListController.betMatchList.getController().show();
                    }
            }
        });
        this.setOnMouseExited(mouseEvent -> System.out.println("mouse off 1"));
        this.setOnMouseEntered(mouseEvent -> System.out.println("mouse on 1"));
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
            idValueText.setText(String.valueOf(item.getId()));
            betDateText.setText(item.getEndDate().format(formatter));
            oddValueText.setText(String.valueOf(item.getBetValue()));
            potentialWinValueText.setText(String.valueOf(item.getPotentialWinValue()));
            insetValueText.setText(String.valueOf(item.getBetValue()));



            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}