package view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

class OptionPane {

    private static Stage configuraStage(String titulo) {
        Stage stage = new Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.setTitle(titulo)
        stage.setMinWidth(250)

        return stage
    }

    private static void showAndWait(Stage stage, String mensagem, List componentes) {
        Label label = new Label()
        label.setText(mensagem)
        label.setWrapText(true)

        HBox botoes = new HBox(50)
        botoes.setPadding(new Insets(10, 10, 10, 10))
        botoes.setAlignment(Pos.CENTER)
        botoes.getChildren().addAll(componentes)

        VBox layout = new VBox(10)
        layout.setPadding(new Insets(20, 20, 20, 20))
        layout.getChildren().addAll(label, botoes)
        layout.setAlignment(Pos.CENTER)

        Scene scene = new Scene(layout)
        stage.setScene(scene)
        stage.showAndWait()
    }

    static void alerta(String titulo, String mensagem) {
        Stage stage = configuraStage(titulo)

        Button ok = new Button("Ok")
        ok.setOnAction({ e -> stage.close() })

        showAndWait(stage, mensagem, [ok])
    }

    static boolean confirmacao(String titulo, String mensagem) {
        Stage stage = configuraStage(titulo)

        boolean resposta = false

        Button sim = new Button("sim")
        sim.setOnAction({ e -> resposta = true; stage.close() })

        Button nao = new Button("não")
        nao.setOnAction({ e -> stage.close() })

        showAndWait(stage, mensagem, [sim, nao])
        return resposta
    }
}
