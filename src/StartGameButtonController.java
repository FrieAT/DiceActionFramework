import DAF.GameObject;
import DAF.Components.AbstractComponent;
import DAF.Event.AInputEvent;
import DAF.Event.IInputListener;
import DAF.Event.KeyState;
import DAF.Event.MouseInputEvent;
import DAF.Input.InputManager;
import DAF.Math.Vector2;
import DAF.Renderer.Components.ButtonGraphic;

public class StartGameButtonController extends AbstractComponent implements IInputListener {

    private View _view;

    @Override
    public void start() {
        GameObject view = GameObject.find("mainScreen");
        if (view == null)
            throw new NullPointerException("Can't find the view to display");

        View viewComponent = view.getComponent(View.class);
        if (viewComponent == null)
            throw new NullPointerException("Given gameObject does not contain a view.");

        this._view = viewComponent;

        InputManager.getInstance().add(MouseInputEvent.class, this);
    }

    @Override
    public void onInput(AInputEvent event) {
        MouseInputEvent mouseEvent = (MouseInputEvent) event;

        if (mouseEvent != null && mouseEvent.getKeyState() == KeyState.Up) {
            Vector2 ownPosition = this.getTransform().getPosition();
            Vector2 mousePosition = mouseEvent.getPosition();
            Vector2 way = new Vector2(
                    Math.abs(ownPosition.x - mousePosition.x),
                    Math.abs(ownPosition.y - mousePosition.y)
            );

            ButtonGraphic buttonGraphic = this.getGameObject().getComponent(ButtonGraphic.class);
            double xDist = buttonGraphic.getWidth()/2.0;
            double yDist = buttonGraphic.getHeight()/2.0;


            if (buttonGraphic != null && way.x < xDist && way.y < yDist) {
                //GameObject.disableAll();
                GameObject inGameView = GameObject.find("InGame");

                inGameView.setEnabled(true);
            }
        }
    }
}
