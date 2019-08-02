package services

import enuns.Consoantes

class ConsoanteService {

    static ConsoanteService instancia

    private ConsoanteService() {
    }

    static ConsoanteService getInstancia() {
        if (!instancia) {
            instancia = new ConsoanteService()
        }
        return instancia
    }

    List<String> getAll() {
        return Consoantes.valores()
    }

    boolean ehConsoante(final String texto) {
        return texto in getAll()
    }

    boolean saoConsoantes(final List<String> textos) {
        return textos.every { ehConsoante(it) }
    }

    List<String> achaNaoConsoantes(final List<String> textos) {
        return textos.findAll { !ehConsoante(it) }
    }
}
