package services

import files.MyImage

class BocaService {

    static BocaService instancia

    private BocaService() {
        silabaService = SilabaService.instancia
    }

    static getInstancia() {
        return instancia ?: new BocaService()
    }

    private static final String nomePastaBocas = 'bocas'

    private SilabaService silabaService

    List<MyImage> getBocasPorConsoante(String consoante) {
        List<String> silabas = silabaService.getSilabasAssociadasAConsoante(consoante)
        return silabas.collect { String silaba -> new MyImage(nomePastaBocas, silaba) }
    }
}