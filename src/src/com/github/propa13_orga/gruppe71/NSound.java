package src.com.github.propa13_orga.gruppe71;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
* Klasse der Soundeffekte 
*/
public class NSound {

	protected Clip clip;
	protected FloatControl laut;
	
	public NSound(String datei){
		
		AudioInputStream stream;
		
		try{
			stream=AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(datei));
			clip=AudioSystem.getClip();
			clip.open(stream);
			laut=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Spielt Sound ab
	 */
	public void Abspielen(){
		clip.stop();
		clip.setMicrosecondPosition(0);
		clip.start();
	}

	/**
	 * Setzt Titel
	 */
	public void Title(){
		clip.stop();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	
	/**
	 * Setzt Lautstaerke
	 * @param value Wert
	 */
	public void SetVolume(float value){
		laut.setValue(value);
	}
	
}
