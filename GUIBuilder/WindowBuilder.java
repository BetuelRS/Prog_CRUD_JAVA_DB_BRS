import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WindowBuilder extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Awesome Window");

        Pane pane = new Pane();
        pane.setPrefSize(505, 238);
        pane.setStyle("-fx-background-color: #1e1e1e;");

        Button element1 = new Button("Click Me");
        element1.setLayoutX(204.95);
        element1.setLayoutY(61.69);
        element1.setPrefWidth(105.81);
        element1.setPrefHeight(30.00);
        element1.setDisable(false);
        element1.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lato.ttf"), 14.00));
        element1.setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: #D9D9D9; -fx-border-color: #979797; -fx-border-radius: 4px; -fx-background-radius: 4px; -fx-border-width: 1px;");
        element1.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> { element1.setBackground(new Background(new BackgroundFill(Color.web("#232323"), new CornerRadii(4.00), null))); });
        element1.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> { element1.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), new CornerRadii(4.00), null))); });
        pane.getChildren().add(element1);

        Scene scene = new Scene(pane, 505, 238);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}