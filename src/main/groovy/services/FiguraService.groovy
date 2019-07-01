package services

import files.MyImage

class FiguraService {

    static FiguraService instancia

    private FiguraService() {
        silabaService = SilabaService.instancia
    }

    static getInstancia() {
        return instancia ?: new FiguraService()
    }

    private static final String nomePastaFiguras = 'figuras'

    private SilabaService silabaService

    List<MyImage> getBocasPorConsoante(String consoante) {
        List<String> silabas = silabaService.getSilabasAssociadasAConsoante(consoante)
        return silabas.collect { String silaba -> new MyImage(nomePastaFiguras, silaba) }
    }
}
