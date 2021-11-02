
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
    
	//---
    ArrayList<ImageView> imageViews;

	@Override
    public void start(Stage stage) {   
		/*
		for(GameObject g : renderManager.gameObjects) {
			AGraphic aGraphic = (AGraphic) g.getComponent(EComponentType.AGraphic);
			
					if((LabelGraphic)aGraphic != null) {
						LabelGraphic labelgraphic = (LabelGraphic)aGraphic;
						
						Image player2 = new Image(new File("images/player2.png").toURI().toString(), true);
						ImageView player2View = new ImageView();
						
					}
					
					if((PictureGraphic)aGraphic != null) {
						PictureGraphic pictureGraphic = (PictureGraphic)aGraphic;
						
						Image image = new Image(new File(pictureGraphic.getPicturePath()).toURI().toString(), true);
						
						ImageView imageView = new ImageView();
						imageView.setImage(image);
						imageView.setY(pictureGraphic.getTop());
						imageView.setX(pictureGraphic.getLeft());
						imageViews.add(imageView);
					}
		}
		
		*/
		
		
		   
		// Audio
        mediaPlayer.play();
    	//---

        StackPane root = new StackPane();
        root.getChildren().addAll(gameboardView
        		);



        

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
        
        
        
      

    }

    public static void main(String[] args) {
        launch();
    }

}