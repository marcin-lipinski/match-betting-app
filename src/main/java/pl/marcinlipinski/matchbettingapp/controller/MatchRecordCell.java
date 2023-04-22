package pl.marcinlipinski.matchbettingapp.controller;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.marcinlipinski.matchbettingapp.model.Match;

@Component
@FxmlView
public class MatchRecordCell extends ListCell<Match> {

    private HBox content;
    private Text name;
    private Text price;

    public MatchRecordCell() {
        super();
        name = new Text();
        price = new Text();
        VBox vBox = new VBox(name, price);
        content = new HBox(new Label("[Graphic]"), vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Match item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            name.setText(item.name);
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
