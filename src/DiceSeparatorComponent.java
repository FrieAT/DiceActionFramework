import DAF.Components.AbstractComponent;
import DAF.Math.Vector2;

public class DiceSeparatorComponent extends AbstractComponent {

    private Vector2 _initPosition;

    private float _distance = 50.0f;

    private double _counter = 0;

    @Override
    public void start() {
        this._initPosition = this.getTransform().getPosition();
    }

    @Override
    public void update() {
        //double deltaTime = RenderManager.getInstance().getDeltaTime();
        Vector2 dV = new Vector2(this._distance * Math.sin(this._counter), this._distance * Math.cos(this._counter));
        //dV = Vector2.Mul(dV, deltaTime);
        this.getTransform().setPosition(Vector2.Add(this._initPosition, dV));
        this._counter += 1;
    }
}
