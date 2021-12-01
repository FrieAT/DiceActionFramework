public class POTCDiceBag extends ADiceBag {

    public POTCDiceBag () {
        for (int i = 0; i < 5; i++) {
            add(new ClassicDice());
        }
    }

}
