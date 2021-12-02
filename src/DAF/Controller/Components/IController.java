package DAF.Controller.Components;
import DAF.Components.IComponent;
import DAF.Dice.Components.ADice;

public interface IController extends IComponent {
    /**
     * Throw a dice and return it's number.
     * @param dice Typical class extended from ADice to throw.
     * @return A number representing the resulted number 
     */
    int throwDice(ADice dice);

    /**
     * Get a players unique player number in order to distinquish for example different input mappings in InputManager.
     * @return the player number
     */
    int getPlayerNo();
}
