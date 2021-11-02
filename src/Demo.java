
import java.util.Arrays;

public class Demo {

    public static void main (String[] args) {
    	
    	
        AbstractManager diceManager = new DiceManager();
        


        GameObject g1 = new GameObject(1, "Backgammon");

        g1.addComponent(new ClassicDice());
        g1.addComponent(new ClassicDice());
        //System.out.println(classicDice.getDiceSides());

        ADice test_dice = new ClassicDice();
        System.out.println(Arrays.deepToString(test_dice.getDiceFaces()));

        for (int i = 0; i < 20; i++) {
            System.out.println(Arrays.toString(test_dice.roll()));
        }

        System.out.println(g1.getComponents());
      
     
    }
}
