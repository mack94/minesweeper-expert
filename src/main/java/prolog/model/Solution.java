package prolog.model;

public class Solution {
    private Integer X;
    private Integer Y;
    private FieldState fieldState;

    public Solution(Integer x, Integer y, FieldState fieldState) {
        X = x;
        Y = y;
        this.fieldState = fieldState;
    }

    public Integer getX() {
        return X;
    }

    public void setX(Integer x) {
        X = x;
    }

    public Integer getY() {
        return Y;
    }

    public void setY(Integer y) {
        Y = y;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }
}
