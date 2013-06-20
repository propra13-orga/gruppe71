package src.com.github.propa13_orga.gruppe71;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class DSound {

	protected Clip clip;
	protected FloatControl laut;
	
	public DSound(String datei){
		
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
	
	public void Abspielen(){
		clip.setMicrosecondPosition(0);
		clip.start();
	}
	public void Title(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	public void SetVolume(float value){
		laut.setValue(value);
	}
	
}
