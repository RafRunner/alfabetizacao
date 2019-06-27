package excecoes

import groovy.transform.CompileStatic

@CompileStatic
class EntradaInvalidaException extends Exception {

    String duplicataMensagem

    EntradaInvalidaException(String mensagem) {
        super(mensagem)
        duplicataMensagem = mensagem
    }
}
