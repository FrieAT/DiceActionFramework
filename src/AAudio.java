
import java.io.File;

public abstract class AAudio implements IComponent {

	String filePath;
	Boolean repeat;
	Boolean playing;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Boolean getRepeat() {
		return repeat;
	}
	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}
	public Boolean getPlaying() {
		return playing;
	}
	public void setPlaying(Boolean playing) {
		this.playing = playing;
	}
	
	


}
