package enuns

enum Vogais {

    A('A'),
    E('E'),
    I('I'),
    O('O'),
    U('U')

    String stringEquivalente

    Vogais(String stringEquivalente) {
        this.stringEquivalente = stringEquivalente
    }

    static boolean ehVogal(String letra) {
        return letra in valores()
    }

    static ArrayList<String> valores() {
        return Arrays.asList(values()).stringEquivalente
    }
}