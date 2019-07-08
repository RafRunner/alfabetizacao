package dominio

import groovy.transform.CompileStatic
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import view.draggable.Nature

@CompileStatic
class Fundo {

    Scene scene

    String consoante
    Boolean ehCasoEspecial

    GridPane painelPai
    GridPane painelImagem
    GridPane painelFiguras

    Map<String, Nature> vogalToLocalSilaba = [:]
    Map<String, Nature> vogalToLocalFigura = [:]

    Map<String, Nature> vogalToSilaba = [:]
    Map<String, Nature> vogalToFigura = [:]
}
