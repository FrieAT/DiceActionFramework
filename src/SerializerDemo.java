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


        System.out.println("\n Version 2:\n");
        System.out.println(js.serialize2(playerGraphic));
    }
}
