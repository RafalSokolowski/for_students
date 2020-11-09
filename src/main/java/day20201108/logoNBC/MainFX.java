package day20201108.logoNBC;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class MainFX extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        stage.setTitle("NBC logo");
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

        Shape peacockFeatherYellow = new PeacockFeather(new Point(400, 400), new Point(200, 400), new Point(200, 200), Color.BLACK).draw();
        Shape peacockFeatherGreen = new PeacockFeather(new Point(400, 400), new Point(600, 400), new Point(600, 200), Color.BLACK).draw();

        Shape peacockFeatherCenter = new PeacockFeather(new Point(400, 400), new Point(300, 100), new Point(500, 100), Color.BLACK).draw();

        root.getChildren().add(peacockFeatherYellow);
        root.getChildren().add(peacockFeatherGreen);

        root.getChildren().add(peacockFeatherCenter);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
