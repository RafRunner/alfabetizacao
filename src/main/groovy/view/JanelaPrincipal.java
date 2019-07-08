package view;

import controllers.FundoController;
import dominio.Fundo;
import factories.FundoFactory;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import services.FundoService;
import utils.StringUtils;

public class JanelaPrincipal extends Application {

    private Stage stage;
    private Scene sceneAtual;

    public JanelaPrincipal() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            String mensagemErro = StringUtils.regexFind(e.toString(), "(?<=.EntradaInvalidaException: ).+!?");

            if (mensagemErro == null) {
                mensagemErro = e.toString();
            }
            e.printStackTrace();
            OptionPane.alerta("Erro!", mensagemErro);
        });

        stage = primaryStage;
        stage.setOnCloseRequest(e -> {
            e.consume();
            fecharPrograma();
        });

        VBox vBox = new VBox();
        Button start = new Button("Começar!");
        start.setOnAction(event -> {
            Fundo fundo = FundoFactory.getInstancia().constroiFundo("D");
            mudarSceneAtual(fundo.getScene());
            FundoController.getInstancia().configuraListeners(fundo);
        });

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
