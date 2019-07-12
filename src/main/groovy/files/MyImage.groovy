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
    String url

    MyImage(final String pastaImagem, final String titulo) {
        try {
            this.url = ambiente.getResourceURL(pastaImagem, titulo + EXTENSAO_PADRAO).toString()
            this.image = new Image(url)
            this.titulo = titulo

        } catch (Exception ignored) {
            throw new EntradaInvalidaException("Arquivo de imagem não existe, não pôde ser lido ou não é uma imagem (Pasta: $pastaImagem título: $titulo)!")
        }
    }

    void resize(final double width, final double height, final boolean preserveRatio = false) {
        final Image imagemResized = new Image(url, width, height, preserveRatio, true)
        this.image = imagemResized
    }
}
