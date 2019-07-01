package enuns

import groovy.transform.CompileStatic

@CompileStatic
enum CasosEspeciais {

    QU('QU', 'fundoQU', [Vogais.A.stringEquivalente, Vogais.E.stringEquivalente, Vogais.I.stringEquivalente, Vogais.O.stringEquivalente])

    String stringEquivalente
    String nomeFundoEspecial
    List<String> vogaisUsadas

    CasosEspeciais(String stringEquivalente, String nomeFundoEspecial, List<String> vogaisUsadas) {
        this.nomeFundoEspecial = nomeFundoEspecial
        this.vogaisUsadas = vogaisUsadas
    }

    static CasosEspeciais getCasoEspecial(String consoante) {
        return values().find { it.stringEquivalente == consoante }
    }

    static List<String> valores() {
        return Arrays.asList(values()).stringEquivalente
    }
}
