public class PlayerController extends AbstractComponent implements IController, IInputListener {
    private static int playerCounter = 1; //FIXME: Better player id handling.
    
    private int _playerNo;

    public PlayerController() {
        this._playerNo = playerCounter++;
    }

    @Override
    public void start() {
        InputManager.getInstance().add(MouseInputEvent.class, this);
    }

    public int throwDice(ADice dice) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPlayerNo(int playerNo) {
        this._playerNo = playerNo;
    }

    public int getPlayerNo() {
        return this._playerNo;
    }

    public void onInput(AInputEvent event) {
        MouseInputEvent mouseEvent = (MouseInputEvent)event;
        if(mouseEvent != null && mouseEvent.getKeyState() == KeyState.Up) {
            Vector2 newPosition = mouseEvent.getPosition();
            
            PictureGraphic graphic = this.getGameObject().getComponent(PictureGraphic.class);
            if(graphic != null) {
                newPosition = new Vector2(
                    newPosition.x - (graphic.getWidth() * graphic.getTransform().getScale().x)/2,
                    newPosition.y - (graphic.getHeight() * graphic.getTransform().getScale().y)/2
                );
            }

            if(newPosition.x < 200) {
                newPosition = new Vector2(200, newPosition.y);
            }
            if(newPosition.x > 990 - 220) {
                newPosition = new Vector2(990 - 220, newPosition.y);
            }

            if(newPosition.y < 50) {
                newPosition = new Vector2(newPosition.x, 50);
            }
            if(newPosition.y > 935 - 70) {
                newPosition = new Vector2(newPosition.x, 935 - 70);
            }

            this.getTransform().setPosition(newPosition);
        }
    }
    
}