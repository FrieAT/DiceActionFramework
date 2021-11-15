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

        JSONSerializer js = new JSONSerializer();
        System.out.println(js.serialize(playerGraphic));

        // Check different types of AGraphics

        GameObject rollButton = new GameObject("Roll_Button");
        ButtonGraphic buttonG = rollButton.addComponent(ButtonGraphic.class);
        buttonG.setLabelText("Würfeln!");
        buttonG.setWidth(300);
        buttonG.setHeight(50);
        rollButton.getTransform().setPosition(new Vector2(800, 600));
        rollButton.addComponent(RollDiceButtonController.class);

        System.out.println("\n Version 2:\n");
        System.out.println(js.serialize2(buttonG));
    }
}
