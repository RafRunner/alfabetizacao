package factories

import services.ConsoanteService

class MenuInicialFactory {

    static MenuInicialFactory instancia

    private MenuInicialFactory() {
        consoanteService = ConsoanteService.instancia
        consoantes = consoanteService.getAll()
    }

    static MenuInicialFactory getInstancia() {
        if (!instancia) {
            instancia = new MenuInicialFactory()
        }
        return instancia
    }

    ConsoanteService consoanteService
    List<String> consoantes
}
