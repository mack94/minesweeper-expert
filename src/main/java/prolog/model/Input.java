package prolog.model;

import java.util.*;

public class Input {
    private static final Integer COL_SIZE = 5;
    private static final Integer ROW_SIZE = 5;
    private static final Integer OUT_OF_BOUNDS_VALUE = -10;
    private Map<Integer, Map<Integer, Integer>> listOfLists;
    private Integer javaColumnSize;
    private Integer javaRowSize;
    private Boolean containsNumberField;

    public Input(Integer javaColumnSize, Integer javaRowSize) {
        this();
        this.javaColumnSize = javaColumnSize;
        this.javaRowSize = javaRowSize;
    }

    public Input() {
        this.listOfLists = new TreeMap<>();
    }

    public void setInputFromArray(int[] array, Integer centerX, Integer centerY) {
        containsNumberField = false;
        int inputX = 1;
        int inputY = 1;
        for(int boardX = centerX-2; boardX <= centerX + 2; boardX++) {
            for(int boardY = centerY-2; boardY <= centerY + 2; boardY++) {
                put(inputX, inputY, getValueFromArray(array, boardX, boardY));
                inputY++;
            }
            inputX++;
            inputY = 1;
        }
    }

    private int getValueFromArray(int[] array, Integer x, Integer y) {
        try{
            if(x >= javaColumnSize) return OUT_OF_BOUNDS_VALUE;
            if(y >= javaRowSize) return OUT_OF_BOUNDS_VALUE;
            return  array[(javaColumnSize * x) + y] < 9 ?  array[(javaColumnSize * x) + y] : -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return OUT_OF_BOUNDS_VALUE;
        }
    }

    private void put(Integer x, Integer y, Integer value) {
        if(value > 0 && value < 9) containsNumberField = true;
        Map column = getColumnList(x);
        column.put(y, value);
    }

    private Map<Integer, Integer> getColumnList(Integer x) {
        if(isColumnListInitialized(x)) {
            return listOfLists.get(x);
        }
        else {
            listOfLists.put(x, new TreeMap<>());
            return listOfLists.get(x);
        }
    }

    private boolean isColumnListInitialized(Integer coord) {
        return Optional.ofNullable(listOfLists.get(coord)).isPresent();
    }

    public Boolean containsNumberField() {
        return containsNumberField;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Iterator<Map<Integer, Integer>> columnIterator = listOfLists.values().iterator(); columnIterator.hasNext();) {
            sb.append("[");
            for(Iterator<Integer> rowIterator = columnIterator.next().values().iterator(); rowIterator.hasNext();) {
                Integer value = rowIterator.next();
                sb.append(value);
                if(rowIterator.hasNext()) sb.append(", ");
            }
            sb.append("]");
            if(columnIterator.hasNext()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
