package src;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Rules : A launch have this form <NumberLaunch>d<ValueLaunch>+<Modifier>
    A launch cannot be negative (except when the formula is illegal)
    A launch can be done in advantage or disadvantage (first take the highest of 2 roll, the other the lowest)
    A bad formula leads to a return of value -1
    The modifier is optional
    If a negative value is put for the nbRoll or the dice value, the request is incorrect and thus return -1
 */
public class Roll {
    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    private boolean isValid;
    private String formula;
    private Dice dice;
    private int nbRoll;
    private int modifier;

    public Roll(String formula) {
        this.formula = formula;
        Pattern p = Pattern.compile("(\\d*)([d])(\\d*)([-+])?(\\d*)?");
        Matcher m = p.matcher(formula);

        if (m.find() && m.matches()) {
            try {
                this.isValid = true;
                this.nbRoll = !m.group(1).isEmpty() ? Integer.parseInt(m.group(1)) : 1;
                this.dice = new Dice(Integer.parseInt(m.group(3)));
                this.modifier = (m.group(4) != null && m.group(5) != null) ? Integer.parseInt(m.group(4) + m.group(5)) : 0;
            } catch (NumberFormatException e) {
                this.isValid = false;
            }
        } else {
            this.isValid = false;
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.isValid = diceValue > 0 && nbRoll > 0;
        this.dice = new Dice(diceValue);
        this.nbRoll = nbRoll;
        this.modifier = modifier;
    }

    public int roll(RollType type) {
        if (isValid) {
            int result = 0;

            if (formula != null)
                System.out.println("Rolling " + formula);

            switch (type) {
                case NORMAL:
                    result = normalRoll();
                    break;
                case ADVANTAGE:
                    result = Math.max(normalRoll(), normalRoll());
                    break;
                case DISADVANTAGE:
                    result = Math.min(normalRoll(), normalRoll());
                    break;
            }

            System.out.println("Result : " + (result > 0 ? result : 0));
            return result > 0 ? result : 0;
        } else {
            System.out.println("Invalid roll : " + formula);
            return -1;
        }
    }

    private int normalRoll() {
        int result = 0;
        for (int i = 0; i < nbRoll; i++) {
            result += this.dice.rollDice();
        }

        System.out.println("src.Roll " + nbRoll + "d" + this.dice.getValue() + " -> " + result);

        return result + this.modifier;
    }
}
