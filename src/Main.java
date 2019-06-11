import javafx.application.Application;
import javafx.stage.Stage;
import json.Initializer;
import view.View;

public class Main extends Application {
    public static void main(String[] args) {
        new Thread(Initializer::main).start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        View.getInstance().setPrimaryStage(primaryStage);
        View.getInstance().run();
        primaryStage.show();
    }
}
