package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ExemploDrag extends Application {


    Stage stage;
    Scene scene1, scene2;

    public ExemploDrag() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setOnCloseRequest(e -> {
            e.consume();
            fecharPrograma();
        });

        VBox vBox = new VBox(20);

        Shape shape1 = new Rectangle(50, 50, Color.BLACK);
        HBox pane1 = new HBox();
        pane1.setAlignment(Pos.CENTER);
        pane1.getChildren().addAll(shape1);
        pane1.setMaxWidth(50);
        Shape shape2 = new Rectangle(50, 50, Color.ORANGE);
        HBox pane2 = new HBox();
        pane2.setAlignment(Pos.CENTER);
        pane2.getChildren().addAll(shape2);
        pane2.setMaxWidth(50);

        Draggable.Nature nature1 = new Draggable.Nature(pane1);
        Draggable.Nature nature2 = new Draggable.Nature(pane2);

        nature1.addListener((draggableNature, dragEvent) -> handle(nature1, nature2, dragEvent));

        nature2.addListener((draggableNature, dragEvent) -> handle(nature2, nature1, dragEvent));

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(pane1, pane2);

        scene1 = new Scene(vBox, 300, 300);

        stage.setScene(scene1);
        stage.setTitle("Título");
        stage.show();

        nature1.setStartingPosition(pane1.getLayoutX(), pane1.getLayoutY());
        nature2.setStartingPosition(pane2.getLayoutX(), pane2.getLayoutY());
    }

    private void handle(Draggable.Nature nature1, Draggable.Nature nature2, Draggable.Event dragEvent) {
        if (dragEvent.equals(Draggable.Event.DragEnd)) {
            System.out.println(nature1.isTouching(nature2));
            System.out.println("Position of 1: " + nature1.getPosition());
            System.out.println("Position of 2: " + nature2.getPosition());
            System.out.println("Relative Position: " + nature1.getPosition().relativeDistance(nature2.getPosition()));
            System.out.println();

            if (nature1.isTouching(nature2)) {
                nature1.fit(nature2);
            }

            if (OptionPane.confirmacao("Desabilitar", "Parar de mover?")){
                nature1.disable();
                nature2.disable();
            }
        } else if (dragEvent.equals(Draggable.Event.Press)) {
            System.out.println("Clicou" + "\n");
        }
    }

    private void fecharPrograma() {
        boolean fechar = OptionPane.confirmacao("Confirme sua ação", "Tem certeza que deseja fechar o programa?");

        if (fechar) {
            stage.close();
        }
    }
}
