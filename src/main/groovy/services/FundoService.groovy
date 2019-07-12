package services

import enuns.CasosEspeciais
import files.MyImage

class FundoService {

    static FundoService instancia

    private FundoService() {
        silabaService = SilabaService.instancia
        bocaService = BocaService.instancia
    }

    static FundoService getInstancia() {
        if (!instancia) {
            instancia = new FundoService()
        }
        return instancia
    }

    private SilabaService silabaService
    private BocaService bocaService

    private static final String nomePastaFundos = 'fundos'
    private static final String nomeFundoPadrao = 'fundo'


    MyImage getFundoPorConsoante(final String consoante) {
        final CasosEspeciais casoEspecial = CasosEspeciais.getCasoEspecial(consoante)
        final String nomeFundo

        if (casoEspecial) {
            nomeFundo = casoEspecial.nomeFundoEspecial
        } else {
            nomeFundo = nomeFundoPadrao
        }

        return new MyImage(nomePastaFundos, nomeFundo)
    }
}
