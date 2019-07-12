package enuns

enum Consoantes {

    B('B'),
    C('C'),
    D('D'),
    F('F'),
    G('G'),
    J('J'),
    L('L'),
    M('M'),
    N('N'),
    P('P'),
    QU('QU'),
    R('R'),
    S('S'),
    T('T'),
    V('V'),
    X('X'),
    Z('Z')

    String stringEquivalente

    Consoantes(String stringEquivalente) {
        this.stringEquivalente = stringEquivalente
    }

    static boolean ehConsoante(String letra) {
        return letra in valores()
    }

    static ArrayList<String> valores() {
        return Arrays.asList(values()).stringEquivalente
    }
}