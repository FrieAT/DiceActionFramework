public class POTCDiceBag extends ADiceBag {

    public POTCDiceBag () {

    }

    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            this.add(ClassicDice.class);
        }
        super.start();
    }
}
