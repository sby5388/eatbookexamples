package com.by5388.java8;

/**
 * @author Administrator  on 2019/9/10.
 */
public class Temp {


    public static void main(String[] args) {
        final Temp temp = new Temp();
        Operation sumOperation = Integer::sum;
        Operation reduceOperation = (int a, int b) -> a - b;

        final int number1 = 20;
        final int number2 = 10;

        System.out.println(temp.action(number1, number2, sumOperation));
        System.out.println(temp.action(number1, number2, reduceOperation));

    }

    private int action(int a, int b, Operation operation) {
        return operation.action(a, b);
    }


    interface Operation {
        int action(int a, int b);
    }

}
