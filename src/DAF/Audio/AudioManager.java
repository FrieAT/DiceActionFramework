package DAF.Audio;


import java.util.ArrayList;

import DAF.AbstractManager;

public class AudioManager extends AbstractManager {
    protected static AudioManager _instance;
    public static AudioManager getInstance() {
        if (_instance == null)
            _instance = new AudioManager();
        return _instance;
    }
    
    protected AudioManager () {
        super();

        this.gameObjects = new ArrayList<>(); // Only Audio Gameobjects
    }
}
