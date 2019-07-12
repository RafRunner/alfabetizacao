package services

import enuns.Consoantes
import files.Ambiente

class ConsoanteService {

    static ConsoanteService instancia

    private ConsoanteService() {
        ambiente = Ambiente.instancia
    }

    static ConsoanteService getInstancia() {
        if (!instancia) {
            instancia = new ConsoanteService()
        }
        return instancia
    }

    Ambiente ambiente

    private static final String nomePastaConsoantes = 'consoantes'

    List<String> getAll() {
        return Consoantes.valores()
    }

    boolean ehConsoante(final String texto) {
        return texto in getAll()
    }

    boolean saoConsoantes(final List<String> textos) {
        return textos.every { ehConsoante(it) }
    }
}
