package services

import enuns.CasosEspeciais
import enuns.Vogais
import files.Ambiente
import files.MyImage
import groovy.transform.CompileStatic

@CompileStatic
class SilabaService {

    static SilabaService instancia

    private SilabaService() {
        ambiente = Ambiente.instancia
    }

    static SilabaService getInstancia() {
        if (!instancia) {
            instancia = new SilabaService()
        }
        return instancia
    }

    private static final String nomePastaSilabas = 'silabas'

    private Ambiente ambiente

    List<String> getSilabasAssociadasAConsoante(String consoante) {
        if (Vogais.ehVogal(consoante)) {
            return []
        }
        List<String> vogais = consoante in CasosEspeciais.valores() ? CasosEspeciais.getCasoEspecial(consoante).vogaisUsadas : Vogais.valores()

        return vogais.collect { String vogal -> consoante + vogal }
    }

    List<MyImage> getImagensSilabasAssociadasAConsoante(String consoante) {
        return getSilabasAssociadasAConsoante(consoante).collect { String silaba -> new MyImage(nomePastaSilabas, silaba) }
    }
}
