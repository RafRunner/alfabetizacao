package view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Modality
import javafx.stage.Stage

import java.awt.Dimension

class OptionPane {

    private static Stage configuraStage(final String titulo, final Dimension tamanho) {
        Stage stage = new Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.setTitle(titulo)
        if (tamanho) {
            stage.setMinHeight(tamanho.height)
            stage.setMaxWidth(tamanho.width)
        } else {
            stage.setMinWidth(250)
        }

        return stage
    }

    private static void showAndWait(final Stage stage, final String mensagem, final List componentes, final int tamanhoFonte) {
        Label label = new Label()
        label.setText(mensagem)
        label.setFont(new Font('Arial',tamanhoFonte))
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

    static void alerta(final String titulo, final String mensagem, final int tamanhoFonte = 15, final Dimension tamanho = null) {
        Stage stage = configuraStage(titulo, tamanho)

        Button ok = new Button("Ok")
        ok.setOnAction({ e -> stage.close() })

        showAndWait(stage, mensagem, [ok], tamanhoFonte)
        stage.centerOnScreen()
    }

    static boolean confirmacao(final String titulo, final String mensagem, final Dimension tamanho = null) {
        Stage stage = configuraStage(titulo, tamanho)

        boolean resposta = false

        Button sim = new Button("sim")
        sim.setOnAction({ e -> resposta = true; stage.close() })

        Button nao = new Button("não")
        nao.setOnAction({ e -> stage.close() })

        showAndWait(stage, mensagem, [sim, nao], 15)
        return resposta
    }
}
