package services

import enuns.CasosEspeciais
import enuns.Vogais
import groovy.transform.CompileStatic

@CompileStatic
class VogalService {

    static VogalService instancia

    private VogalService() {
        silabaService = SilabaService.instancia
    }

    static getInstancia() {
        if (!instancia) {
            instancia = new VogalService()
        }
        return instancia
    }

    private SilabaService silabaService

    ArrayList<String> getVogaisParaConsoante(final String consoante) {
        final CasosEspeciais casoEspecial = CasosEspeciais.getCasoEspecial(consoante)
        if (casoEspecial) {
            return casoEspecial.vogaisUsadas
        }
        return Vogais.valores()
    }

    String getVogalParaIndice(final String consoante, final int indice) {
        final ArrayList<String> vogais

        final CasosEspeciais casoEspecial = CasosEspeciais.getCasoEspecial(consoante)
        if (casoEspecial) {
            vogais = casoEspecial.vogaisUsadas
        } else {
            vogais = Vogais.valores()
        }

        if (indice >= vogais.size()) {
            return null
        }
        return vogais[indice]
    }
}
