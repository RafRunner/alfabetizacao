package factories

import dominio.Fundo
import enuns.CasosEspeciais
import files.MyImage
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundImage
import javafx.scene.layout.BackgroundPosition
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BackgroundSize
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.RowConstraints
import javafx.scene.text.Font
import services.BocaService
import services.FiguraService
import services.FundoService
import services.SilabaService
import services.VogalService
import view.draggable.Nature

import java.awt.Dimension
import java.awt.Toolkit

class FundoFactory {

    static FundoFactory instancia

    private FundoFactory() {
        silabaService = SilabaService.instancia
        bocaService = BocaService.instancia
        fundoService = FundoService.instancia
        figuraService = FiguraService.instancia
        vogalService = VogalService.instancia
    }

    static FundoFactory getInstancia() {
        if (!instancia) {
            instancia = new FundoFactory()
        }
        return instancia
    }

    private static final Dimension screenSize = Toolkit.defaultToolkit.screenSize
    private static final Dimension tamanhoTela = new Dimension(screenSize.width - (0.10417 * screenSize.width) as Integer, screenSize.height as Integer)
    private static final Double alturaGavetaFiguras = 0.10417 * screenSize.width

    private SilabaService silabaService
    private BocaService bocaService
    private FundoService fundoService
    private FiguraService figuraService
    private VogalService vogalService

    Fundo constroiFundo(final String consoante) {
        final boolean ehCasoEspecial = CasosEspeciais.getCasoEspecial(consoante)

        final MyImage imagem = fundoService.getFundoPorConsoante(consoante)
        imagem.resize(tamanhoTela.width, tamanhoTela.height - alturaGavetaFiguras)

        final BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        final BackgroundImage backgroundImage = new BackgroundImage(imagem.image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)

        final GridPane painelPai = new GridPane()
        final GridPane painelImagem = new GridPane()
        final GridPane painelFiguras = new GridPane()
        final Fundo fundo = new Fundo(consoante: consoante, ehCasoEspecial: ehCasoEspecial, painelPai: painelPai, painelImagem: painelImagem, painelFiguras: painelFiguras)

        painelPai.rowConstraints.add(new RowConstraints(tamanhoTela.height - alturaGavetaFiguras))
        painelPai.rowConstraints.add(new RowConstraints(alturaGavetaFiguras))

        painelImagem.setMaxSize(tamanhoTela.width, tamanhoTela.height - alturaGavetaFiguras)
        painelImagem.setBackground(new Background(backgroundImage))
        configuraFundo(fundo)

        painelFiguras.setPadding(new Insets(30, 30, 30, 30))
        painelFiguras.setHgap(50)
        configuraFiguras(fundo)

        GridPane.setConstraints(painelImagem, 0, 0)
        GridPane.setConstraints(painelFiguras, 0, 1)
        painelPai.children.addAll(painelImagem, painelFiguras)

        final Scene scene = new Scene(painelPai, tamanhoTela.width, tamanhoTela.height)
        fundo.scene = scene

        return fundo
    }

    private void configuraFundo(final Fundo fundo) {
        final GridPane painelImagem = fundo.painelImagem
        final String consoante = fundo.consoante

        painelImagem.setAlignment(Pos.CENTER)
        painelImagem.columnConstraints.add(new ColumnConstraints(tamanhoTela.width / 2))
        painelImagem.columnConstraints.add(new ColumnConstraints(tamanhoTela.width / 2))

        final AnchorPane anchorPaneDireito = new AnchorPane()
        fundo.anchorPaneDireitoEspacamento = anchorPaneDireito
        final AnchorPane anchorPaneEsquerdo = new AnchorPane()
        anchorPaneDireito.setMinHeight(tamanhoTela.height - alturaGavetaFiguras)
        anchorPaneEsquerdo.setMinHeight(tamanhoTela.height - alturaGavetaFiguras)

        GridPane.setConstraints(anchorPaneDireito, 0, 0)
        GridPane.setConstraints(anchorPaneEsquerdo, 1, 0)

        if (!fundo.ehCasoEspecial) {
            final ImageView figuraBoca = new ImageView(bocaService.getBocaPorConsoante(consoante).image)
            final Label labelConsoante = new Label(consoante)
            labelConsoante.font = new Font('Arial', 0.09259 * screenSize.height)

            AnchorPane.setTopAnchor(figuraBoca, 0.226851 * screenSize.height)
            AnchorPane.setLeftAnchor(figuraBoca, 0.15104 * screenSize.width)
            AnchorPane.setTopAnchor(labelConsoante, 0.342592 * screenSize.height)
            AnchorPane.setLeftAnchor(labelConsoante, 0.177083 * screenSize.width)
            anchorPaneDireito.children.addAll(figuraBoca, labelConsoante)
        }

        for (int i = 0; i < silabaService.getNumeroSilabasAssociadasAConsoante(consoante); i++) {
            final Pane paneLugarSilaba = new Pane()
            paneLugarSilaba.setMinSize(0.052083 * screenSize.width, 0.092592 * screenSize.height)

            final Pane paneLugarFigura = new Pane()
            paneLugarFigura.setMinSize(0.052083 * screenSize.width, 0.092592 * screenSize.height)

            final Nature natureSilaba = new Nature(paneLugarSilaba)
            natureSilaba.disable()
            final Nature natureFigura = new Nature(paneLugarFigura)
            natureFigura.disable()

            final String vogalIndice = vogalService.getVogalParaIndice(consoante, i)

            fundo.vogalToLocalSilaba.put(vogalIndice, natureSilaba)
            fundo.vogalToLocalFigura.put(vogalIndice, natureFigura)

            AnchorPane.setTopAnchor(paneLugarSilaba, (0.083333 * screenSize.height) + (0.136111 * screenSize.height * i))
            AnchorPane.setLeftAnchor(paneLugarSilaba, 0.104166 * screenSize.width)
            AnchorPane.setTopAnchor(paneLugarFigura, (0.083333 * screenSize.height) + (0.136111 * screenSize.height * i))
            AnchorPane.setLeftAnchor(paneLugarFigura, 0.260416 * screenSize.width)
            anchorPaneEsquerdo.children.addAll(paneLugarSilaba, paneLugarFigura)
        }

        painelImagem.children.addAll(anchorPaneDireito, anchorPaneEsquerdo)
    }

    private void configuraFiguras(final Fundo fundo) {
        final String consoante = fundo.consoante
        int contador = 0

        silabaService.getSilabasAssociadasAConsoante(consoante).each {
            final Pane paneSilaba = new Pane()
            paneSilaba.setMinSize(0.088541 * screenSize.width, 0.092592 * screenSize.height)

            Label label = new Label(it)
            double size = consoante.length() > 1 ? 0.06481 * screenSize.height : 0.092592 * screenSize.height
            label.font = new Font('Arial', size)

            paneSilaba.children.add(label)
            final Nature natureSilaba = new Nature(paneSilaba)
            fundo.vogalToSilaba.put(vogalService.getVogalParaIndice(consoante, contador), natureSilaba)
            contador++
        }

        contador = 0

        figuraService.getFigurasPorConsoante(consoante).each {
            final Pane paneFigura = new Pane()
            paneFigura.setMinSize(0.088541 * screenSize.width, 0.101851 * screenSize.height)

            final ImageView figura = new ImageView(it.image)

            paneFigura.children.add(figura)
            final Nature natureFigura = new Nature(paneFigura)
            fundo.vogalToFigura.put(vogalService.getVogalParaIndice(consoante, contador), natureFigura)
            contador++
        }

        colocaSilabasOuFigurasNaTela(fundo.painelFiguras, fundo.vogalToSilaba)
    }

    void colocaSilabasOuFigurasNaTela(final GridPane painelFiguras, final Map<String, Nature> silabasOuFiguras) {
        final List<Nature> silabasOuFigurasList = silabasOuFiguras.values() as List<Nature>
        silabasOuFigurasList.sort { Math.random() }

        for (int i = 0; i <  silabasOuFigurasList.size(); i++) {
            final Pane silabaOuFigura = (Pane) silabasOuFigurasList[i].getEventRegion()

            GridPane.setConstraints(silabaOuFigura, i, 0)
            painelFiguras.children.add(silabaOuFigura)
        }
    }
}
