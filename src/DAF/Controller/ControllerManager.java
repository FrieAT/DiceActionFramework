package DAF.Controller;

import DAF.AbstractManager;
import DAF.Controller.Components.PlayerController;
import DAF.Controller.Event.ConnectionState;
import DAF.Controller.Event.ControllerConnectionEvent;
import DAF.Controller.Handler.ControllerHandler;
import DAF.GameObject;
import DAF.Controller.Components.AbstractController;
import DAF.Controller.Components.IController;
import DAF.Input.InputManager;
import DAF.Renderer.RenderManager;
import DAF.Renderer.Server.ServerRenderer;
import DAF.Socket.HttpSocket.HttpResourceExistsException;
import DAF.Socket.HttpSocket.HttpServerSocket;
import DAF.Socket.HttpSocket.Resource.JsonBufferResource;
import DAF.Socket.IResource;
import DAF.Socket.ISocketListener;
import org.json.JSONObject;

public class ControllerManager extends AbstractManager implements ISocketListener {
    private static final String _jsonPlayerApi = "/api/player.json";

    protected static ControllerManager _instance;
    public static ControllerManager getInstance() {
        if (_instance == null)
            _instance = new ControllerManager();
        return _instance;
    }

    private int _localPlayerCounter = 0;

    private int _currentPlayer;

    private ControllerManager() {
        super();
    }

    public int GetPlayerCount() { return _localPlayerCounter; }

    public int GetNextPlayer() { return ++this._localPlayerCounter; }

    public boolean IsControllerAtCycle(int playerIndex) {
        return playerIndex == this._currentPlayer;
    }

    public int GetControllerAtCycle() { return this._currentPlayer; }

    public IController GetController(int controllerIndex) {
        for(GameObject gameObject : this.gameObjects) {
            AbstractController controller = gameObject.getComponent(AbstractController.class);
            if(controller.getPlayerNo() == controllerIndex) {
                return (IController)controller;
            }
        }
        return null;
    }

    @Override
    public void init() {
        HttpServerSocket socket = (HttpServerSocket) RenderManager.getInstance().getRenderer(ServerRenderer.class).getSocket();
        if(socket != null) {
            try {
                socket.addResource(JsonBufferResource.class, _jsonPlayerApi);
                socket.addListener(_jsonPlayerApi, this);
            }
            catch(HttpResourceExistsException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
    }

    /**
     * Loop after every update cycle over a player id
     * in order to perform custom controlled views.
     * See dependency @ControllerView
     */
    @Override
    public void update() {
        if(++this._currentPlayer > this._localPlayerCounter) {
            this._currentPlayer = 0;
        }
    }

    @Override
    public boolean add(GameObject gameObject) {
        throw new NullPointerException("Please add an AbstractController object and not a gameObject itself.");
    }

    public boolean add(AbstractController controller) {
        return super.add(controller.getGameObject());
    }

    @Override
    public boolean remove(GameObject gameObject) {
        throw new NullPointerException("Please add an AbstractController object and not a gameObject itself.");
    }

    public boolean remove(AbstractController controller) {
        return super.add(controller.getGameObject());
    }

    private IController logoutController(String token_id)
    {
        for(GameObject controller : this.getGameObjects()) {
            IController comp = controller.getComponent(AbstractController.class);
            if(comp.getTokenId().equalsIgnoreCase(token_id)) {
                //FIXME: Destroy GameObject from scene.
                controller.removeComponent(AbstractController.class);

                ControllerHandler handler = InputManager.getInstance().getInputHandler(ControllerHandler.class);
                if(handler != null) {
                    ControllerConnectionEvent event = new ControllerConnectionEvent(ConnectionState.Disconected, comp);
                    handler.addDelayedEvent(event);
                }

                return comp;
            }
        }

        return null;
    }

    private IController loginController(String name)
    {
        for(GameObject controller : this.getGameObjects()) {
            if(controller.getName().equalsIgnoreCase(name)) {
                return null;
            }
        }

        GameObject newPlayer = new GameObject(name);
        IController controller = newPlayer.addComponent(PlayerController.class);

        ControllerHandler handler = InputManager.getInstance().getInputHandler(ControllerHandler.class);
        if(handler != null) {
            ControllerConnectionEvent event = new ControllerConnectionEvent(ConnectionState.Connected, controller);
            handler.addDelayedEvent(event);
        }

        return controller;
    }

    @Override
    public void onSocketPrepareTransmission(IResource resource) {

    }

    @Override
    public void onSocketTransmission(IResource resource) {

    }

    @Override
    public void onSocketReceive(IResource resource) {
        assert (resource.getClass() == JsonBufferResource.class);
        JsonBufferResource json = (JsonBufferResource) resource;

        String jsonString = new String(json.getBufferedData());
        JSONObject jsonData = new JSONObject(jsonString);

        if(jsonData.has("action")) {
            JSONObject returnValue = new JSONObject();
            String action = jsonData.getString("action");

            //Action: Plasyer wants to login to session.
            if(action.equalsIgnoreCase("login")) {
                String playerName = jsonData.getString("player_name");
                IController controller = this.loginController(playerName);

                if(controller != null) {
                    returnValue.put("return_code", "success");
                    returnValue.put("token_id", controller.getTokenId());
                } else {
                    returnValue.put("return_code", "error");
                }
            }
            //Action: Player wants to logout from session.
            else if(action.equalsIgnoreCase("logout")) {
                String tokenId = jsonData.getString("token_id");
                IController controller = logoutController(tokenId);

                if(controller != null) {
                    returnValue.put("return_code", "success");
                } else {
                    returnValue.put("return_code", "error");
                }
            }

            json.writeBuffer(returnValue.toString());
        }
    }
}
