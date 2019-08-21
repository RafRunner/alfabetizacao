package services

import files.MyImage

import java.awt.Dimension
import java.awt.Toolkit

class BocaService {

    static BocaService instancia

    private static final Dimension screenSize = Toolkit.defaultToolkit.screenSize

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
        boca.resize(0.088541 * screenSize.width, 0.101852 * screenSize.height)
        return boca
    }
}
