package services

import files.MyImage

class BocaService {

    static BocaService instancia

    private BocaService() {
        silabaService = SilabaService.instancia
    }

    static getInstancia() {
        if (!instancia) {
            instancia = new BocaService()
        }
        return instancia
    }

    private static final String nomePastaBocas = 'bocas'

    private SilabaService silabaService

    MyImage getBocaPorConsoante(String consoante) {
        final MyImage boca = new MyImage(nomePastaBocas, consoante)
        boca.resize(170, 110)
        return boca
    }
}
