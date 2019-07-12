package view;

import controllers.FundoController;
import files.Logger;
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

        Label explicacao = new Label("Por favor, entre com as consoantes que serão apresentadas separadas por vírgula: ");
        explicacao.setWrapText(true);

        TextField consoantes = new TextField();
        consoantes.setPromptText("Consoantes a serem aprendidas:");

        Label lblNomeCrianca = new Label("Nome da Criança: ");
        explicacao.setWrapText(true);

        TextField txtNomeCrianca = new TextField();
        consoantes.setPromptText("Nome da Criança:");

        Button start = new Button("Começar!");
        start.setOnAction(event -> {
            final List<String> consoantesSelecionadas = parseConsoantes(consoantes.getText());
            final String nomeCrianca = txtNomeCrianca.getText();

            if (nomeCrianca == null) {
                OptionPane.alerta("Erro!", "Por favor, entre com um nome para a criança!");
            } else if (consoantesSelecionadas == null) {
                OptionPane.alerta("Erro!", "Por favor, entre com as consoantes separadas por vírgulas");
            } else if (!consoanteService.saoConsoantes(consoantesSelecionadas)){
                final List<String> naoConsoantes = consoanteService.achaNaoConsoantes(consoantesSelecionadas);
                OptionPane.alerta("Erro!", naoConsoantes + ": Não são consoantes ou não estão disponíveis!");

            } else {
                final List<String> listConsoantes = parseConsoantes(consoantes.getText());
                final Logger logger = new Logger(nomeCrianca, listConsoantes);

                FundoController.getInstancia().executarSequenciaDeConsoantes(this, listConsoantes, logger);
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lblNomeCrianca, txtNomeCrianca, explicacao, consoantes, start);

        Scene primeiraScene = new Scene(vBox, 500, 500);
        menuInicial = primeiraScene;

        stage.setScene(primeiraScene);
        stage.setTitle("Alfabetização");
        stage.show();
    }

    private List<String> parseConsoantes(String texto) {
        List<String> consoantes;
        try {
            texto = texto.toUpperCase();
            texto = StringUtils.regexReplace(texto, "[^A-Z,]", "");
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
