import java.sql.Time;
import java.time.Clock;
import java.time.Instant;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXWindow extends Application
{
    public static final double MAX_FPS = 33.3;

	private StackPane _nodesBuffer;

	private boolean _notifyChanged;

    private Scene _scene;

    private Stage _window = null;

    private boolean _didRender = false;

    private Instant _clock;

	public JavaFXWindow() {
		JavaFXWindow._instance = this;

		this._nodesBuffer = new StackPane();
        
        Clock clock = Clock.systemDefaultZone();
        this._clock = clock.instant();
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

        RenderManager.getInstance().setRenderedTime(this._clock.getNano());
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

	public synchronized void clearNodes() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                _nodesBuffer.getChildren().clear();
            }
        });
	}

	public synchronized void renderNode(Node n) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                _nodesBuffer.getChildren().add(n);
            }
        });

	}

    @Override
    public void init() {
        _scene = new Scene(this._nodesBuffer, 1024, 768);
    }

	@Override
	public void start(Stage stage) {
        _window = stage;

        notifyRendered();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(MAX_FPS), e -> refresh()));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play();

        _window.setScene(_scene);

        _window.show();
	}

    /**
     * Our frame limiter for rendering the elements.
     * The limit is based on MAX_FPS.
     * Here we calculate the nano seconds from the last frame.
     */
    public void refresh() {
        if(!isChanged()) {
            return;
        }

        

        notifyRendered();
    }
}