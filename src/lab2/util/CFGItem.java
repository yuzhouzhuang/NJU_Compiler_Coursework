package lab2.util;

public class CFGItem {
    private String leftValue;
    private String rightValue;

    public CFGItem(String leftValue, String rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public String getLeftValue() {
        return leftValue;
    }

    public String getRightValue() {
        return rightValue;
    }

    public String getValue() {
        return leftValue + " -> " + rightValue;
    }
}
