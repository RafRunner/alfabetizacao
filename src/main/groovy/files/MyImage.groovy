package files

import excecoes.EntradaInvalidaException
import groovy.transform.CompileStatic
import javafx.scene.image.Image

@CompileStatic
class MyImage {

    private static final String EXTENSAO_PADRAO = '.png'

    private Ambiente ambiente = Ambiente.instancia

    String titulo
    Image image
    String caminhoImagem

    MyImage(final String pastaImagem, final String titulo, final Boolean ehFundo) {
        String caminho = ambiente.getFullPath(pastaImagem, titulo + EXTENSAO_PADRAO)

        try {
            this.image = new Image(new File(caminho).toURI().toURL().toString(), ehFundo)
            this.titulo = titulo
            this.caminhoImagem = caminho

        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Arquivo de imagem não existe, não pôde ser lido ou não é uma imagem: ' + caminho + '!')
        }
    }

    MyImage(final String pastaImagem, final String titulo) {
        this(pastaImagem, titulo, false)
    }
}
