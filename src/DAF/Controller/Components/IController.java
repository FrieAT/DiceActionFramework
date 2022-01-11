package DAF.Controller.Components;

import DAF.Components.IComponent;
import DAF.Dice.Components.ADice;

public interface IController extends IComponent {
    /**
     * Get a players unique player number in order to distinquish for example different input mappings in InputManager.
     * @return the player number
     */
    int getPlayerNo();
}
