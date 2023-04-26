package pl.marcinlipinski.matchbettingapp.application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.marcinlipinski.matchbettingapp.controller.MainWindowController;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        stage.setTitle("Losing money");
        Scene scene = new Scene(fxWeaver.loadView(MainWindowController.class));
        stage.setScene(scene);
        stage.show();
    }
}
