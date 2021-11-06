
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HelloFX extends Application{


	RenderManager renderManager  = new RenderManager();
	
    
    // Audio
	String musicFile = "sounds/piratePiano.mp3";    
	Media sound = new Media(new File(musicFile).toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(sound);
	//---
	
	// Image
	Image gameboard = new Image(new File("images/gameboard.png").toURI().toString(), true);
    ImageView gameboardView = new ImageView();
	//---
    
	// Image
	Image player2 = new Image(new File("images/player2.png").toURI().toString(), true);
	ImageView player2View = new ImageView();
	//---
 

    
	//---
    ArrayList<ImageView> imageViews;

	@Override
    public void start(Stage stage) {   
		
		
		// JavaFX Label
	    Label label = new Label();
	    label.setText("Man, Don't Get Angry");
	    label.setLayoutX(850);
	    label.setLayoutY(0);
	    label.setFont(new Font(20));
	    label.setStyle("-fx-font-weight: bold;");
	    label.setTextFill(Color.color(1, 0, 0)); //rgb in Modell?
	    //---
	   
		   
		// Audio
        mediaPlayer.play();
    	//---

        //Image   
        gameboardView.setImage(gameboard);
        gameboardView.setFitWidth(1920);
        gameboardView.setFitHeight(1080);
        gameboardView.setX(0);
        gameboardView.setY(0);
        player2View.setImage(player2);
        //---
        
        Pane root = new Pane();
        root.getChildren().addAll(
        		
        		gameboardView,
        		player2View,
        		label
        		);



        //--

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.show();
        
      

    }

    public static void main(String[] args) {
        launch();
    }

}