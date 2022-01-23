

import DAF.GameObject;
import DAF.Controller.Components.PlayerController;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;
import DAF.Renderer.Components.PictureGraphic;
import DAF.Serializer.ASerializer;
import DAF.Serializer.JsonSerializer;

public class SerializerDemo {
    public static void main (String[] args) {

        GameObject player1 = new GameObject("PlayerOne");
        PictureGraphic playerGraphic = player1.addComponent(PictureGraphic.class);
        playerGraphic.setPicturePath("images/player2.png");
        playerGraphic.setWidth(50);
        playerGraphic.setHeight(75);
        playerGraphic.setLeft(480);
        playerGraphic.setTop(550);
        player1.getTransform().setScale(new Vector2(0.5, 0.5));
        player1.addComponent(PlayerController.class);

        // Check different types of AGraphics

        GameObject rollButton = new GameObject("Roll_Button");
        ButtonGraphic buttonG = rollButton.addComponent(ButtonGraphic.class);
        buttonG.setLabelText("Würfeln!");
        buttonG.setWidth(300);
        buttonG.setHeight(50);
        rollButton.getTransform().setPosition(new Vector2(800, 600));
        rollButton.addComponent(RollDiceButtonController.class);

        ASerializer js = new JsonSerializer();
        System.out.println(js.serialize(playerGraphic));
    }
}
