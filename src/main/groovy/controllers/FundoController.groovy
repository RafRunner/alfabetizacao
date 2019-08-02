package controllers

import dominio.Fundo
import factories.FundoFactory
import files.EfeitosSonoros
import files.Logger
import view.JanelaPrincipal
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

    private int indiceAtual
    private Boolean mostrarParabenizacao

    void executarSequenciaDeConsoantes(final JanelaPrincipal janelaPrincipal, final List<String> consoantes, final Boolean mostrarParabenizacao, final Logger logger) {
        indiceAtual = 0
        this.mostrarParabenizacao = mostrarParabenizacao
        logger.registraMensagem("Iniciando apresentação!")
        executarProximaConsoante(janelaPrincipal, consoantes, logger)
    }

    private void executarProximaConsoante(final JanelaPrincipal janelaPrincipal, final List<String> consoantes, final Logger logger) {
        if (indiceAtual == consoantes.size()) {
            indiceAtual = 0
            logger.registraMensagem("Finalizadas todas as sílabas e figuras!", "\n\t")
            janelaPrincipal.voltarParaMenuPrincipal()
            OptionPane.alerta("Parabens!", "Você acabou tudas as letras! Parabéns!")
            return
        }

        final String consoanteAtual = consoantes.get(indiceAtual)
        logger.registraMensagem("Consoante de número ${indiceAtual + 1}: $consoanteAtual", "\t")
        logger.registraMensagem("Iniciando apresentação das sílabas:", "\t")

        final Fundo fundo = fundoFactory.constroiFundo(consoanteAtual)
        janelaPrincipal.mudarSceneAtual(fundo.scene)
        configuraListeners(fundo, janelaPrincipal, consoantes, logger)
        indiceAtual++
    }

    void configuraListeners(final Fundo fundo, final JanelaPrincipal janelaPrincipal, final List<String> consoantes, final Logger logger) {
        final Map<String, Nature> vogalToLocalSilaba = fundo.vogalToLocalSilaba
        final Map<String, Nature> vogalToLocalFigura = fundo.vogalToLocalFigura

        boolean jaColocouAsFiguras = false

        vogalToLocalSilaba.each {
            it.value.setStartingPosition(fundo.anchorPaneDireitoEspacamento.width + it.value.eventRegion.layoutX, it.value.eventRegion.layoutY)
        }

        vogalToLocalFigura.each {
            it.value.setStartingPosition(fundo.anchorPaneDireitoEspacamento.width + it.value.eventRegion.layoutX, it.value.eventRegion.layoutY)
        }

        configuraListener(fundo, vogalToLocalSilaba, fundo.vogalToSilaba, logger, {
            if (fundo.vogalToSilaba.any { it.value.isEnabled() }) {
                return
            }
            if (!jaColocouAsFiguras) {
                logger.registraMensagem("A criança terminou todas as sílabas! Passando para as figuras", '\t')
                fundoFactory.colocaSilabasOuFigurasNaTela(fundo.painelFiguras, fundo.vogalToFigura)
                new Thread( {
                    sleep(100)
                    configuraListener(fundo, fundo.vogalToLocalFigura, fundo.vogalToFigura, logger, {
                        if (fundo.vogalToFigura.any { it.value.isEnabled() }) {
                            return
                        }
                        logger.registraMensagem("A criança terminou todas as figuras! Passando para a próxima consoante.\n", '\t')
                        if (mostrarParabenizacao) {
                            OptionPane.alerta("Parabéns!", "Você finalizou essa letra!", 25)
                        }
                        executarProximaConsoante(janelaPrincipal, consoantes, logger)
                    })
                    jaColocouAsFiguras = true
                }).start()
            }
        })
    }

    private void configuraListener(final Fundo fundo, final Map<String, Nature> vogalToLocalSilabaOuFigura, final Map<String, Nature> vogalToSilabaOuFigura, final Logger logger, final Closure acaoAoFinalizar) {
        vogalToSilabaOuFigura.each { Map.Entry<String, Nature> vt ->
            final String vogal = vt.key
            final Nature silabaOuFigura = vt.value
            silabaOuFigura.setStartingPosition(silabaOuFigura.eventRegion.layoutX, silabaOuFigura.eventRegion.layoutY + fundo.getPainelImagem().height)

            final Position posicaoInicial = silabaOuFigura.getPosition()

            silabaOuFigura.addListener({ draggableNature, dragEvent ->
                handle(vogal, silabaOuFigura, vogalToLocalSilabaOuFigura, posicaoInicial, dragEvent, logger)
                acaoAoFinalizar()
            })
        }
    }

    private void handle(final String vogal,
                        final Nature objetoMovido,
                        final Map<String, Nature> regioes,
                        final Position posicaoInicial,
                        final Event dragEvent,
                        final Logger logger) {

        if (dragEvent != Event.DragEnd) {
            return
        }

        final Map<String, Nature> areasTocadas = regioes.findAll { it.value.isTouching(objetoMovido) }
        if (!areasTocadas) {
            objetoMovido.deslocate(posicaoInicial)
            logger.registraMensagem("Criança arrastou a silaba/figura associada a vogal $vogal para uma posição não associada a qualquer vogal", '\t')
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
            logger.registraMensagem("Criança acertou! Arrastou a silaba/figura associada a vogal $vogal para sua posição correta", '\t')
            EfeitosSonoros.ACERTO.play()
            objetoMovido.disable()
        } else {
            objetoMovido.deslocate(posicaoInicial)
            logger.registraMensagem("Criança errou! Arrastou a silaba/figura associada a vogal $vogal para uma posição associada a vogal $vogalAssociada", '\t')
            EfeitosSonoros.ERRO.play()
        }
    }
}
