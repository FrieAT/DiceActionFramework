public class POTCDiceBag extends ADiceBag {
    public POTCDiceBag() {
        for (int i = 0; i < 7; i++)
            this.add(new ClassicDice());
    }
}
