package utils;
import javax.sound.sampled.*;
import java.io.File;

public class Audio {
    private static Clip clip;
    private static FloatControl volumeControl;
    private static int volumen = 100;
    private static boolean muteado = false;

    public static void reproducir(String ruta) throws Exception {
        detener();

        AudioInputStream audio = AudioSystem.getAudioInputStream(new File(ruta));
        clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            aplicarVolumen(muteado ? 0 : volumen);
        }
    }

    public static void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void aplicarVolumen(int porcentaje) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float rango = max - min;
            float db = min + (porcentaje / 100.0f) * rango;
            volumeControl.setValue(db);
        }
    }

    public static void mutear() {
        muteado = true;
        aplicarVolumen(0);
    }

    public static void desmutear() {
        muteado = false;
        aplicarVolumen(volumen);
    }

    public static boolean estaMuteado() {
        return muteado;
    }
}
