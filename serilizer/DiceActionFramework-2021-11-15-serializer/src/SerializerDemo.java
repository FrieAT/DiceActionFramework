import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SerializerDemo {
    public static void main (String[] args) throws InterruptedException {


         JSONSerializer js = new JSONSerializer();

         
    			String server = "diceaction.bplaced.net";
    			int port = 21;
    			String user = "diceaction";
    			String pass = "KS2PH7";

    			FTPClient ftpClient = new FTPClient();
    			try {
    		
    				ftpClient.connect(server, port);
    				ftpClient.login(user, pass);
    				ftpClient.enterLocalPassiveMode();
    				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    				 GameObject player1 = new GameObject("PlayerOne");
    		         PictureGraphic playerGraphic = player1.addComponent(PictureGraphic.class);
    				for (int i = 1; i<10; i++) {
    		    	
    		         playerGraphic.setPicturePath("images/player2.png");
    		         playerGraphic.setWidth(50);
    		         playerGraphic.setHeight(75);
    		         playerGraphic.setLeft(200 + i * 50);
    		         playerGraphic.setTop(550);
    		         playerGraphic.RenderingLayer = 2;
    		         player1.getTransform().setScale(new Vector2(0.5, 0.5));
    		         player1.addComponent(PlayerController.class);
    		         
    				// APPROACH #1: uploads first file using an InputStream
    				PrintWriter out = new PrintWriter("fetchFrame.json");
    			    out.println(js.serialize(playerGraphic));
    			    out.close();
    				File firstLocalFile = new File("fetchFrame.json");

    				String firstRemoteFile = "www/api/fetchFrame.json";
    				InputStream inputStream = new FileInputStream(firstLocalFile);

    				System.out.println("Start uploading first file");
    				boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
    				inputStream.close();
    				if (done) {
    				System.out.println("The first file is uploaded successfully.");
    		} TimeUnit.SECONDS.sleep(1);

    				}


    			} catch (IOException ex) {
    				System.out.println("Error: " + ex.getMessage());
    				ex.printStackTrace();
    			} finally {
    				try {
    				if (ftpClient.isConnected()) {
    						ftpClient.logout();
    						ftpClient.disconnect();
    					}
    			} catch (IOException ex) {
    					ex.printStackTrace();
    			}
    		}
       


 
    }
}
