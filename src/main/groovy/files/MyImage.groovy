package files

import excecoes.EntradaInvalidaException
import groovy.transform.CompileStatic

import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.BufferedImage

@CompileStatic
class MyImage {

    private static final String EXTENSAO_PADRAO = '.png'

    private Ambiente ambiente = Ambiente.instancia

    String titulo
    BufferedImage bufferedImage
    String caminhoImagem

    MyImage(String pastaImagem, String titulo) {
        String caminho = ambiente.getFullPath(pastaImagem, titulo + EXTENSAO_PADRAO)

        try {
            File arquivo = new File(caminho)

            this.bufferedImage = ImageIO.read(arquivo)
            this.titulo = titulo
            this.caminhoImagem = caminho

        } catch (IOException ignored) {
            throw new EntradaInvalidaException('Arquivo de imagem não existe, não pôde ser lido ou não é uma imagem! :' + caminho)
        }
    }

    void resize(int scaledWidth, int scaledHeight) throws IOException {
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, bufferedImage.getType())

        Graphics2D g2d = outputImage.createGraphics()
        g2d.drawImage(bufferedImage, 0, 0, scaledWidth, scaledHeight, null)
        g2d.dispose()

        bufferedImage = outputImage
    }
}
