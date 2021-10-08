package ch.unisg.tapasadditionexecutor;

public class AddNumbersCommand {
    private final float operator1;
    private final float operator2;

    public AddNumbersCommand(float operator1, float operator2) {
        this.operator1 = operator1;
        this.operator2 = operator2;
    }

    public float getOperator1() {
        return operator1;
    }

    public float getOperator2() {
        return operator2;
    }
}
