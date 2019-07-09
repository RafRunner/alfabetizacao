package files


import groovy.transform.CompileStatic
import view.OptionPane

@CompileStatic
class Ambiente {

    static Ambiente instancia = new Ambiente()

    private boolean producao

    private String rootDirectory
    private String sistemaOperacional
    private String separadorEndereco

    private Ambiente() {
        sistemaOperacional = System.getProperty('os.name')
        separadorEndereco = System.getProperty('file.separator')

        File location = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI())

        boolean producao = location.getPath().contains('.jar')
        this.producao = producao
        if (producao) {
            rootDirectory = ''
//            OptionPane.alerta("caminho produção", rootDirectory)
        } else {
            rootDirectory = System.getProperty('user.dir')
            rootDirectory += separadorEndereco + 'src' + separadorEndereco + 'main' + separadorEndereco + 'resources'
//            OptionPane.alerta("caminho dev", rootDirectory)
        }
    }

    String getFullPath(String nomePasta, String nomeArquivo = null) {
        String fullPath

        fullPath = rootDirectory + separadorEndereco + nomePasta
        if (nomeArquivo) {
            fullPath += separadorEndereco + nomeArquivo
        }
//        OptionPane.alerta("caminho completo", fullPath)
        return fullPath
    }

    File getFile(String nomeArquivo, String nomePasta = null) {
        String fullPath
        fullPath = rootDirectory

        if (nomePasta) {
            fullPath += separadorEndereco + nomePasta
        }
        fullPath += separadorEndereco + nomeArquivo

        return getFileDualMode(fullPath)
    }

    List<File> getFiles(String nomePasta) {
        if (!producao) {
            File pasta
            try {
                pasta = new File(getFullPath(nomePasta))
            } catch(Exception ignored) {
                throw ignored
            }

//            OptionPane.alerta("arquivos pasta", pasta.listFiles().toString())
            return (List<File>) pasta.listFiles().findAll { File arquivo -> arquivo.isFile() }
        } else {
            return null
        }
    }

    private File getFileDualMode(String caminho) {
        if (producao) {
            return new File(getClass().getResource(caminho).toExternalForm())
        } else {
            return new File(caminho)
        }
    }
}
