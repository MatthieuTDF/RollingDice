package Random;

import java.util.Random;

public class RNGImpl {
    private static Random r = new Random();

    public int random(int max) {
        return r.nextInt(max) + 1;
    }
}
