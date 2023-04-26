package pl.marcinlipinski.matchbettingapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.marcinlipinski.matchbettingapp.model.Match;
import pl.marcinlipinski.matchbettingapp.service.BetService;
import pl.marcinlipinski.matchbettingapp.service.MatchService;

@Component
@Controller
@FxmlView("BetMatchList.fxml")
public class BetMatchListController {
    @FXML
    public ListView<Match> betMatchListView;
    private Stage stage;

    @FXML
    private AnchorPane dialog;
    @FXML
    private ProgressBar progressBar;
    private final BetService betService;
    private MatchService matchService;

    public BetMatchListController(BetService betService, MatchService matchService) {
        this.betService = betService;
        this.matchService = matchService;
    }

    @SneakyThrows
    public void loadData(long betId){
        var matches = betService.getMatchesByBetId(betId).stream().toList();
        var size = matches.size();
        Thread thread = new Thread(() -> {
            int counter = 0;
            progressBar.setVisible(true);
            for(var match : matches){
                matchService.getRefreshedMatchByBetId(match);
                counter++;
                System.out.println(match.getHomeTeam());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int finalCounter = counter;
                Platform.runLater(() -> progressBar.setProgress((double) finalCounter /size));
            }
            progressBar.setVisible(false);
        });
        thread.setDaemon(true);

        thread.start();
        try{
            thread.wait();
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
        betMatchListView.getItems().clear();
        betMatchListView.setItems(betService.getMatchesByBetId(betId));
        betMatchListView.refresh();
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        betMatchListView.setCellFactory(listView -> new MatchInBetList());
    }

    public void show() {
        stage.show();
    }
}
