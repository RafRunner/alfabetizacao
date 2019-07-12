package files

import groovy.transform.CompileStatic

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

@CompileStatic
enum EfeitosSonoros {

    ACERTO("acerto.wav"),
    ERRO("erro.wav")

    private Clip clip

    private Ambiente ambiente = Ambiente.instancia
    private String pastaAudio = 'audio'

    EfeitosSonoros(String soundFileName) {
        InputStream audio = ambiente.getResourceInputStream(pastaAudio, soundFileName)
        clip = AudioSystem.getClip()
        BufferedInputStream bufferStream = new BufferedInputStream(audio)
        clip.open(AudioSystem.getAudioInputStream(bufferStream))
    }

    void play() {
        if (clip.isRunning()) {
            clip.stop()
        }
        clip.setFramePosition(0)
        clip.start()
    }
}
