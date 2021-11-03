import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXWindow extends Application
{
    public static final int MAX_FPS = 30;

	private LinkedList<Node> _nodesBufferA;

    private LinkedList<Node> _nodesBufferB;

	private boolean _notifyChanged;

    private Scene[] _scenes;

    private int _oldBuffer = 0;

    private int _newBuffer = 1;

    private Stage _window = null;

    private boolean _didRender = false;

	public JavaFXWindow() {
		JavaFXWindow._instance = this;

		this._nodesBufferA = new LinkedList<>();
        this._nodesBufferB = new LinkedList<>();
	}

	private static JavaFXWindow _instance;
	public static JavaFXWindow getInstance() { return _instance; }

	public synchronized void notifyChanged(boolean isChanged) {
        this._notifyChanged = isChanged;
        this._didRender = false;
	}

    public synchronized void notifyRendered() {
        this._didRender = true;
        this._notifyChanged = false;
    }

    public synchronized boolean isRendered() { 
        if(this._window == null) {
            return false;
        }
        return this._didRender;
    }

	public synchronized boolean isChanged() {
		return this._notifyChanged;
	}

	public void clearNodes() {
        if(_newBuffer == 0) {
            this._nodesBufferA = new LinkedList<>();
        } else {
            this._nodesBufferB = new LinkedList<>();
        }
	}

	public void renderNode(Node n) {
        if(_newBuffer == 0) {
            this._nodesBufferA.push(n);
        } else {
            this._nodesBufferB.push(n);
        }
	}

    @Override
    public void init() {
        int buffers = 2;
        _scenes = new Scene[buffers];

        for(int i = 0; i < buffers; i++) {
            _scenes[i] = null;
        }
    }

	@Override
	public void start(Stage stage) {
        _window = stage;

        notifyRendered();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / MAX_FPS), e -> refresh()));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play();

        _window.show();
	}

    public void refresh() {
        if(!isChanged()) {
            return;
        }

        // Make new scene for next frame.
        StackPane root = new StackPane();
        
        if(_oldBuffer == 0) {
            root.getChildren().addAll(this._nodesBufferA);
        } else {
            root.getChildren().addAll(this._nodesBufferB);
        }

        _scenes[_newBuffer] = new Scene(root, 640, 480);

        _window.setScene(_scenes[_newBuffer]);

        _oldBuffer = _newBuffer;
        _newBuffer = (_newBuffer + 1) % 2;

        notifyRendered();
    }
}