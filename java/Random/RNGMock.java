package Random;

public class RNGMock extends RNGImpl {

    @Override
    public int random(int max) {
        return max > 0 ? max / 2 + 1 : 0;
    }
}
