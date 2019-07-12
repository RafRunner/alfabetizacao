package files

import groovy.transform.CompileStatic

@CompileStatic
class Ambiente {

    static Ambiente instancia = new Ambiente()

    private String rootDirectory
    private String sistemaOperacional
    private String separadorEndereco

    String lineSeparator

    private Ambiente() {
        sistemaOperacional = System.getProperty('os.name')
        separadorEndereco = System.getProperty('file.separator')
        lineSeparator = System.getProperty('line.separator')

        File location = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI())

        boolean producao = location.getPath().contains('.jar')
        if (producao) {
            rootDirectory = location.getParent()
        } else {
            rootDirectory = System.getProperty('user.dir')
        }
    }

    InputStream getResourceInputStream(final String nomePasta, final String nomeArquivo) {
        return getClass().getResourceAsStream('/' + nomePasta + '/' + nomeArquivo)
    }

    URL getResourceURL(final String nomePasta, final String nomeArquivo) {
        return getClass().getResource('/' + nomePasta + '/' + nomeArquivo)
    }

    String getFullPath(String nomePasta, String nomeArquivo = null) {
        String fullPath = rootDirectory

        if (nomePasta) {
            fullPath += separadorEndereco + nomePasta
        }
        if (nomeArquivo) {
            fullPath += separadorEndereco + nomeArquivo
        }
        return fullPath
    }

    File getFile(String nomeArquivo, String nomePasta = null) {
        return new File(getFullPath(nomePasta, nomeArquivo))
    }
}
