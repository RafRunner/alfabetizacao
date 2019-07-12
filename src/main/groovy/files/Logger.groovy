package files

import java.text.SimpleDateFormat

class Logger {

    private String nomeCrianca
    private List<String> consoantesTreinadas

    private String nomeArquivo
    private File arquivo
    private Date horaInicio

    private static final SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd")
    private static final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss")
    private static final SimpleDateFormat formatoCompleto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private final String lineSeparator = ambiente.lineSeparator
    private static final String nomePastaLogs = 'Resultados'
    private static final String extensaoArquivos = '.txt'

    private static final Ambiente ambiente = Ambiente.instancia

    Logger(final String nomeCrianca, final List<String> consoantes) {
        this.nomeCrianca = nomeCrianca
        this.consoantesTreinadas = consoantes
        this.horaInicio = new Date()
        this.nomeArquivo = montaNomeArquivo()
        this.arquivo = criaArquivo()
        iniciaArquivo()
    }

    private File criaArquivo() {
        criaPastaSeNaoExiste()
        File arquivo = new File(ambiente.getFullPath(nomePastaLogs, nomeArquivo + extensaoArquivos))
        int numeroArquivo = 1

        while (arquivo.exists()) {
            arquivo = new File(ambiente.getFullPath(nomePastaLogs, "$nomeArquivo($numeroArquivo)$extensaoArquivos"))
            numeroArquivo++
        }

        return arquivo
    }

    private void criaPastaSeNaoExiste() {
        final File pasta = new File(ambiente.getFullPath(nomePastaLogs))
        if (!pasta.exists()) {
            pasta.mkdir()
        }
    }

    private String montaNomeArquivo() {
        return "$nomeCrianca-$consoantesTreinadas-${formatoData.format(horaInicio)}"
    }

    private void iniciaArquivo() {
        StringBuilder resultado = new StringBuilder()

        resultado.append("Nome da Criança: ${nomeCrianca}$lineSeparator$lineSeparator")
        resultado.append("Consoantes Usadas: ${consoantesTreinadas}$lineSeparator")
        resultado.append("Data e Horário de Início: ${formatoCompleto.format(horaInicio)}$lineSeparator")
        resultado.append("Resultados:$lineSeparator$lineSeparator")

        arquivo.append(resultado.toString())
    }

    void registraMensagem(String mensagem, String prefixos = "") {
        mensagem = mensagem.replaceAll('\n', lineSeparator)
        String linhaCompleta = prefixos + "\t${formatoHora.format(new Date())}: ${mensagem}$lineSeparator"
        arquivo.append(linhaCompleta)
    }
}
