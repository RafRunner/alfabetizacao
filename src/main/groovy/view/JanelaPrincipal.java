package view;

import excecoes.EntradaInvalidaException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import services.FundoService;

public class JanelaPrincipal extends Application {

    private Stage stage;
    private Scene sceneAtual;

    private FundoService fundoService = FundoService.getInstancia();

    public JanelaPrincipal() {
    }

    public static void main(String[] args) {
        launch(args);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            String mensagemErro = "";
//            StringUtils.regexFind(e.toString(), "(?<=.EntradaInvalidaException: ).+!");

            try {
                mensagemErro = ((EntradaInvalidaException) e).getMensagem();
            } catch (Exception ignored) {}

             if (mensagemErro.equals("")) {
                mensagemErro = e.toString();
            }
            OptionPane.alerta("Erro!", mensagemErro);
        });
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setOnCloseRequest(e -> {
            e.consume();
            fecharPrograma();
        });

        VBox vBox = new VBox();
        Button start = new Button("Começar!");
        start.setOnAction(event -> mudarSceneAtual(fundoService.constroiFundo("QU")));

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(start);

        sceneAtual = new Scene(vBox, 300, 300);

        stage.setScene(sceneAtual);
        stage.setTitle("Título");
        stage.show();
    }

    public void mudarSceneAtual(Scene scene) {
        stage.setScene(scene);
        sceneAtual = scene;
    }

    private void fecharPrograma() {
        boolean fechar = OptionPane.confirmacao("Confirme sua ação", "Tem certeza que deseja fechar o programa?");

        if (fechar) {
            stage.close();
        }
    }
}
