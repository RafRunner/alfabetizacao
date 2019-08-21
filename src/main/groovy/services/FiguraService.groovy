package services

import files.MyImage

import java.awt.Dimension
import java.awt.Toolkit

class FiguraService {

    static FiguraService instancia

    private static final Dimension screenSize = Toolkit.defaultToolkit.screenSize

    private FiguraService() {
        silabaService = SilabaService.instancia
    }

    static FiguraService getInstancia() {
        if (!instancia) {
            instancia = new FiguraService()
        }
        return instancia
    }

    private static final String nomePastaFiguras = 'figuras'

    private SilabaService silabaService

    List<MyImage> getFigurasPorConsoante(String consoante) {
        List<String> silabas = silabaService.getSilabasAssociadasAConsoante(consoante)
        return silabas.collect { String silaba ->
            MyImage myImage = new MyImage(nomePastaFiguras, silaba)
            myImage.resize(0.088541 * screenSize.width, 0.101852 * screenSize.height)
            myImage
        }
    }
}
