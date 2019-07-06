package excecoes

import groovy.transform.CompileStatic

@CompileStatic
class EntradaInvalidaException extends Exception {

    String mensagem

    EntradaInvalidaException(String mensagem) {
        super(mensagem)
        this.mensagem = mensagem
    }
}
