package controllers

import dominio.Fundo
import factories.FundoFactory
import files.EfeitosSonoros
import view.OptionPane
import view.Position
import view.draggable.Event
import view.draggable.Nature

class FundoController {

    static FundoController instancia

    private FundoController() {
        fundoFactory = FundoFactory.instancia
    }

    static FundoController getInstancia() {
        if (!instancia) {
            instancia = new FundoController()
        }
        return instancia
    }

    private FundoFactory fundoFactory

    private boolean jaColocouAsFiguras

    void configuraListeners(final Fundo fundo) {
        final Map<String, Nature> vogalToLocalSilaba = fundo.vogalToLocalSilaba
        final Map<String, Nature> vogalToLocalFigura = fundo.vogalToLocalFigura

        jaColocouAsFiguras = false

        vogalToLocalSilaba.each {
            it.value.setStartingPosition(fundo.anchorPaneDireitoEspacamento.width + it.value.eventRegion.layoutX, it.value.eventRegion.layoutY)
        }

        vogalToLocalFigura.each {
            it.value.setStartingPosition(fundo.anchorPaneDireitoEspacamento.width + it.value.eventRegion.layoutX, it.value.eventRegion.layoutY)
        }

        configuraListener(fundo, vogalToLocalSilaba, fundo.vogalToSilaba, {
            if (fundo.vogalToSilaba.any { it.value.isEnabled() }) {
                return
            }
            if (!jaColocouAsFiguras) {
                fundoFactory.colocaSilabasOuFigurasNaTela(fundo.painelFiguras, fundo.vogalToFigura)
                new Thread( {
                    sleep(100)
                    configuraListener(fundo, fundo.vogalToLocalFigura, fundo.vogalToFigura, {
                        if (fundo.vogalToFigura.any { it.value.isEnabled() }) {
                            return
                        }
                        OptionPane.alerta("Parabéns!", "Você finalizou essa letra!")
                    })
                    jaColocouAsFiguras = true
                }).start()
            }
        })
    }

    private void configuraListener(final Fundo fundo, final Map<String, Nature> vogalToLocalSilabaOuFigura, final Map<String, Nature> vogalToSilabaOuFigura, final Closure acaoAoFinalizar) {
        vogalToSilabaOuFigura.each { Map.Entry<String, Nature> vt ->
            final String vogal = vt.key
            final Nature silabaOuFigura = vt.value
            silabaOuFigura.setStartingPosition(silabaOuFigura.eventRegion.layoutX, silabaOuFigura.eventRegion.layoutY + fundo.getPainelImagem().height)

            final Position posicaoInicial = silabaOuFigura.getPosition()

            silabaOuFigura.addListener({ draggableNature, dragEvent ->
                handle(vogal, silabaOuFigura, vogalToLocalSilabaOuFigura, posicaoInicial, dragEvent)
                acaoAoFinalizar()
            })
        }
    }

    private void handle(final String vogal,
                        final Nature objetoMovido,
                        final Map<String, Nature> regioes,
                        final Position posicaoInicial,
                        final Event dragEvent) {

        if (dragEvent != Event.DragEnd) {
            return
        }

        final Map<String, Nature> areasTocadas = regioes.findAll { it.value.isTouching(objetoMovido) }
        if (!areasTocadas) {
            objetoMovido.deslocate(posicaoInicial)
            EfeitosSonoros.ERRO.play()
            return
        }

        Nature areaTocadaMaisProxima = areasTocadas.values().first()
        String vogalAssociada = areasTocadas.keySet().first()
        Position menorDistancia = areasTocadas.get(vogalAssociada).getPosition().relativeAbsoluteDistance(objetoMovido.getPosition())

        for (int i = 1; i < areasTocadas.size(); i++) {
            final vogalAtual = areasTocadas.keySet()[i]
            final Position distanciaAtual = areasTocadas.get(vogalAtual).getPosition().relativeAbsoluteDistance(objetoMovido.getPosition())
            if (distanciaAtual < menorDistancia) {
                menorDistancia = distanciaAtual
                areaTocadaMaisProxima = areasTocadas.get(vogalAtual)
                vogalAssociada = vogalAtual
            }
        }

        if (vogalAssociada == vogal) {
            objetoMovido.fit(areaTocadaMaisProxima)
            EfeitosSonoros.ACERTO.play()
            objetoMovido.disable()
        } else {
            objetoMovido.deslocate(posicaoInicial)
            EfeitosSonoros.ERRO.play()
        }
    }
}
