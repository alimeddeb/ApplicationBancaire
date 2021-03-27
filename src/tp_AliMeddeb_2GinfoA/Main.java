package tp_AliMeddeb_2GinfoA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("guiBanque.fxml"));
        primaryStage.setTitle("ENSIT Bank");
        primaryStage.setScene(new Scene(root, 850, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
