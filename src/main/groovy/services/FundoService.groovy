package services

import enuns.CasosEspeciais
import files.MyImage
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundImage
import javafx.scene.layout.BackgroundPosition
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BackgroundSize
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints

import java.awt.Dimension
import java.awt.Toolkit

class FundoService {

    static FundoService instancia

    private static final Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize
    private static final double alturaGavetaFiguras = 200

    private FundoService() {
        silabaService = SilabaService.instancia
    }

    static FundoService getInstancia() {
        if (!instancia) {
            instancia = new FundoService()
        }
        return instancia
    }

    private static final String nomePastaFundos = 'fundos'
    private static final String nomeFundoPadrao = 'fundo'

    private SilabaService silabaService

    private MyImage getFundoPorConsoante(final String consoante) {
        final CasosEspeciais casoEspecial = CasosEspeciais.getCasoEspecial(consoante)
        final String nomeFundo

        if (casoEspecial) {
            nomeFundo = casoEspecial.nomeFundoEspecial
        } else {
            nomeFundo = nomeFundoPadrao
        }

        return new MyImage(nomePastaFundos, nomeFundo, true)
    }

    Scene constroiFundo(final String consoante) {
        final Image imagem = getFundoPorConsoante(consoante).image
        final BackgroundImage fundo = new BackgroundImage(imagem, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)

        final GridPane painelPai = new GridPane()
        final GridPane painelImagem = new GridPane()
        final GridPane painelFiguras = new GridPane()

        painelPai.getRowConstraints().add(new RowConstraints(tamanhoTela.height - alturaGavetaFiguras))
        painelPai.getRowConstraints().add(new RowConstraints(alturaGavetaFiguras))

        painelImagem.setMinSize(tamanhoTela.width, tamanhoTela.height - alturaGavetaFiguras)
        painelImagem.setBackground(new Background(fundo))
        GridPane.setConstraints(painelImagem, 0, 0)

        painelFiguras.setPadding(new Insets(30, 30, 30, 30))
        painelFiguras.setHgap(50)
        GridPane.setConstraints(painelFiguras, 0, 1)

        painelPai.children.addAll(painelImagem, painelFiguras)

        final Scene stage = new Scene(painelPai, tamanhoTela.width, tamanhoTela.height)

        return stage
    }
}
