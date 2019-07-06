package enuns

import groovy.transform.CompileStatic
import view.FundoQUView

@CompileStatic
enum CasosEspeciais {

    QU('QU', 'fundoQU', FundoQUView, [Vogais.A.stringEquivalente, Vogais.E.stringEquivalente, Vogais.I.stringEquivalente, Vogais.O.stringEquivalente])

    String stringEquivalente
    String nomeFundoEspecial
    Class viewExpecial
    List<String> vogaisUsadas

    CasosEspeciais(String stringEquivalente, String nomeFundoEspecial, Class viewExpecial, List<String> vogaisUsadas) {
        this.stringEquivalente = stringEquivalente
        this.nomeFundoEspecial = nomeFundoEspecial
        this.viewExpecial = viewExpecial
        this.vogaisUsadas = vogaisUsadas
    }

    static CasosEspeciais getCasoEspecial(String consoante) {
        return values().find { it.stringEquivalente == consoante }
    }

    static List<String> valores() {
        return Arrays.asList(values()).stringEquivalente
    }
}
