package view;

import controllers.FundoController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import services.ConsoanteService;
import utils.StringUtils;

import java.util.Arrays;
import java.util.List;

public class JanelaPrincipal extends Application {

    private Stage stage;
    private Scene menuInicial;

    public JanelaPrincipal() {
        consoanteService = ConsoanteService.getInstancia();
    }

    private ConsoanteService consoanteService;

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
        vBox.setPadding(new Insets(30, 30, 30, 30));
        vBox.setSpacing(50);

        Label explicacao = new Label("Por favor, entre com as consoantes que serão apresentadas separadas por vígura: ");
        explicacao.setWrapText(true);

        TextField consoantes = new TextField();
        consoantes.setPromptText("Consoantes a serem aprendidas:");

        Button start = new Button("Começar!");
        start.setOnAction(event -> {
            final List<String> consoantesSelecionadas = parseConsoantes(consoantes.getText());
            if (consoantesSelecionadas == null || !consoanteService.saoConsoantes(consoantesSelecionadas)) {
                OptionPane.alerta("Erro!", "Valor inválido! Por favor entre com as consoantes separadas por vígulas");
            } else {
                FundoController.getInstancia().executarSequenciaDeConsoantes(this, parseConsoantes(consoantes.getText()));
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(explicacao, consoantes, start);

        Scene primeiraScene = new Scene(vBox, 500, 500);
        menuInicial = primeiraScene;

        stage.setScene(primeiraScene);
        stage.setTitle("Alfabetização");
        stage.show();
    }

    private List<String> parseConsoantes(String texto) {
        texto = texto.toUpperCase();
        texto = StringUtils.regexReplace(texto, "[^A-Z,]", "");
        List<String> consoantes;
        try {
            consoantes =  Arrays.asList(texto.split(","));
        } catch (Exception ignored) {
            return null;
        }
        return consoantes;
    }

    public void mudarSceneAtual(final Scene scene) {
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void voltarParaMenuPrincipal() {
        mudarSceneAtual(menuInicial);
    }

    private void fecharPrograma() {
        boolean fechar = OptionPane.confirmacao("Confirme sua ação", "Tem certeza que deseja fechar o programa?");

        if (fechar) {
            stage.close();
        }
    }
}
