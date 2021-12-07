public class PlayerMovement extends AbstractComponent implements IInputListener {
    private IController _forController = null;

    public void setController(IController controller) {
        this._forController = controller;
    }
    
    @Override
    public void start() {
        super.start();

        ControllerView playerView = this.getGameObject().getComponent(ControllerView.class);
        if(playerView != null) {
            this._forController = playerView.getController();
        }
        

        InputManager.getInstance().add(MouseInputEvent.class, this);
    }

    public void onInput(AInputEvent event) {
        if(this._forController == null) {
            return;
        }

        MouseInputEvent mouseEvent = (MouseInputEvent)event;
        if(mouseEvent != null && mouseEvent.getKeyState() == KeyState.Up) {
            Vector2 newPosition = mouseEvent.getPosition();

            PictureGraphic graphic = this._forController.getGameObject().getComponent(PictureGraphic.class);
            if(graphic != null) {
                newPosition = new Vector2(
                    newPosition.x - (graphic.getWidth() * graphic.getTransform().getScale().x)/2,
                    newPosition.y - (graphic.getHeight() * graphic.getTransform().getScale().y)/2
                );
            }

            if(newPosition.x < 200) {
                return;
            }
            if(newPosition.x > 990 - 220) {
                return;
            }

            if(newPosition.y < 50) {
                return;
            }
            if(newPosition.y > 935 - 70) {
                return;
            }

            this._forController.getGameObject().getTransform().setPosition(newPosition);
        }
    }
}
