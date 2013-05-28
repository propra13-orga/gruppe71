package src.com.github.propa13_orga.gruppe71;
import java.applet.*;
import java.io.File;
import java.net.*;
import javax.sound.*;
public class DSound {
	
	

	
/*
	public String NameSoundDatei(){ 
	
		this.data=this.sound[0];
		return this.data;
		
	}
	*/
	@SuppressWarnings("deprecation")
	public void playSoundEffect(){
		File sound=new File("src/src/com/github/propa13_orga/gruppe71/Schmatz.wav");
		try{
		final AudioClip audio=Applet.newAudioClip(sound.toURL());
		audio.play();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		catch(MalformedURLException murle){
			System.out.println(murle);
		}
	}
	
}
