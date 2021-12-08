package DAF.Audio.Components;
public class Audio extends AAudio {
	
	Audio(String filePath) {
		this.filePath = filePath;
		this.repeat = false;
		this.playing = false;		
	}
	
	Audio(String filePath, Boolean repeat, Boolean playing) {
		this.filePath = filePath;
		this.repeat = repeat;
		this.playing = playing;		
	}
	
}
