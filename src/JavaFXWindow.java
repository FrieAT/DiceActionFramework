import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXWindow extends Application
{
	private LinkedList<Node> _nodes;

	private boolean _notifyChanged;

	public JavaFXWindow() {
		JavaFXWindow._instance = this;

		this._nodes = new LinkedList<>();
	}

	private static JavaFXWindow _instance;
	public static JavaFXWindow getInstance() { return _instance; }

	public synchronized void notifyChanged(boolean isChanged) {
		this._notifyChanged = true;
	}

	public synchronized boolean isChanged() {
		return this._notifyChanged;
	}

	public synchronized void clearNodes() {
		this._nodes = new LinkedList<>();
	}

	public synchronized void renderNode(Node n) {
		this._nodes.push(n);
	}

	@Override
	public void start(Stage stage) {
		this.notifyChanged(true);
		
		while(true) {
			if(!this.isChanged()) {
				continue;
			}

			StackPane root = new StackPane();
			
			root.getChildren().addAll(this._nodes);

			Scene scene = new Scene(root, 640, 480);
			stage.setScene(scene);
			stage.show();

			this.notifyChanged(false);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                 break;
            }
		}
	}
}