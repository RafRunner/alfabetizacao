package files

import excecoes.EntradaInvalidaException
import groovy.transform.CompileStatic

@CompileStatic
class Ambiente {

    static Ambiente instancia = new Ambiente()

    private String rootDirectory
    private String sistemaOperacional
    private String separadorEndereco

    private Ambiente() {
        File location = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI())

        boolean producao = location.getPath().contains('.jar')
        if (producao) {
            rootDirectory = location.getParent()
        } else {
            rootDirectory = System.getProperty('user.dir')
        }
        sistemaOperacional = System.getProperty('os.name')
        separadorEndereco = System.getProperty('file.separator')

        rootDirectory += 'main' + separadorEndereco + 'resources'
    }

    String getFullPath(String nomePasta, String nomeArquivo = null) {
        String fullPath

        fullPath = rootDirectory + separadorEndereco + nomePasta
        if (nomeArquivo) {
            fullPath += separadorEndereco + nomeArquivo
        }
        return fullPath
    }

    File getFile(String nomeArquivo, String nomePasta = null) {
        String fullPath
        fullPath = rootDirectory

        if (nomePasta) {
            fullPath += separadorEndereco + nomePasta
        }
        fullPath += separadorEndereco + nomeArquivo

        return new File(fullPath)
    }

    List<File> getFiles(String nomePasta) {
        File pasta
        try {
            pasta = new File(getFullPath(nomePasta))
        } catch(Exception ignored) {
            throw new EntradaInvalidaException('Essa pasta não existe!')
        }

        return (List<File>) pasta.listFiles().findAll { File arquivo -> arquivo.isFile() }
    }

}
