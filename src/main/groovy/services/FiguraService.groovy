package services

import files.MyImage

class FiguraService {

    static FiguraService instancia

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
            myImage.resize(170, 110)
            myImage
        }
    }
}
