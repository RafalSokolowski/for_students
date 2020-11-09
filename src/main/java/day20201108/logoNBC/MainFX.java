package day20201108.logoNBC;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        stage.setTitle("NBC logo");
        Scene scene = new Scene(root, 625, 600);
        stage.setScene(scene);
        stage.show();

        Group sixPeacockFeathers = SixPeacockFeathers.create();

        Text nbc = new TextNbc(61,570, "NBC", 250).create();

        root.getChildren().add(sixPeacockFeathers);
        root.getChildren().add(nbc);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
