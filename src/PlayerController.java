public class PlayerController extends AbstractComponent implements IController, IInputListener {
    private int _playerNo;

    public PlayerController(int playerNo) {
        this._playerNo = playerNo;
    }

    @Override
    public void start() {
        InputManager.getInstance().add(MouseInputEvent.class, this);
    }

    @Override
    public void update() {

    }

    public int throwDice(ADice dice) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getPlayerNo() {
        return this._playerNo;
    }

    public void onInput(AInputEvent event) {
        MouseInputEvent mouseEvent = (MouseInputEvent)event;
        if(mouseEvent != null && mouseEvent.getKeyState() == KeyState.Up) {
            Vector2 newPosition = mouseEvent.getPosition();
            
            PictureGraphic graphic = (PictureGraphic)this.getGameObject().getComponent(EComponentType.AGraphic);
            if(graphic != null) {
                newPosition = new Vector2(
                    newPosition.x - graphic.getWidth()/2,
                    newPosition.y - graphic.getHeight()/2
                );
            }

            this.getTransform().setPosition(newPosition);
        }
    }
    
}
