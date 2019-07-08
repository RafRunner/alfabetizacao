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
            this.caminhoImagem = new File(caminho).toURI().toURL().toString()
            this.image = new Image(caminhoImagem, ehFundo)
            this.titulo = titulo

        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Arquivo de imagem não existe, não pôde ser lido ou não é uma imagem: ' + caminho + '!')
        }
    }

    MyImage(final String pastaImagem, final String titulo) {
        this(pastaImagem, titulo, false)
    }

    void resize(final double width, final double height, final boolean preserveRatio = false) {
        final Image imagemResized = new Image(caminhoImagem, width, height, preserveRatio, true)
        this.image = imagemResized
    }
}
