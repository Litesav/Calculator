package com.example.litesav.Calculator;

import java.util.regex.Pattern;

import static com.example.litesav.Calculator.MainActivity.countOperation;
import static com.example.litesav.Calculator.MainActivity.display;
import static com.example.litesav.Calculator.MainActivity.functionOn;
import static com.example.litesav.Calculator.MainActivity.resultOfFunction;

public class Calculating {
    static protected double simpleAction(String a, String b, String op) { //операции вычисления с полученными аргументами
        if (!MainActivity.notationNumber.equals("10")) {                      //перевод из исходной системы счисления в десятичное
            a = toTen(a, Integer.valueOf(MainActivity.notationNumber));
            b = toTen(b, Integer.valueOf(MainActivity.notationNumber));
        }
        double result;
        switch (op) {
            case "+":
                result = Double.valueOf(a) + Double.valueOf(b);
                break;
            case "-":
                result = Double.valueOf(a) - Double.valueOf(b);
                break;
            case "×":
                result = Double.valueOf(a) * Double.valueOf(b);
                break;
            case "÷":
                try {

                    result = Double.valueOf(a) / Double.valueOf(b);
                    break;
                } catch (ArithmeticException e) {

                }
            default:
                result = Double.valueOf(b);
        }
        if (!MainActivity.notationNumber.equals("10")) {                     //перевод из десятичной системы счисления в исходную
            int variable = (int) result;
            String strResult = Integer.toString(variable, Integer.valueOf(MainActivity.notationNumber)); //перевод целых неотрицательных чисел
            result = Double.valueOf(strResult);
        }
        return result;
    }

    static protected double simpleAction(String a, String op) {  //перегруженный метод вычисления выр. для работы с функциями
        double result;
        switch (op) {
            case "sin":
                result = Math.sin(Double.valueOf(a));   //исходное число берет в радианах
                break;
            case "exp":
                result = Math.exp(Double.valueOf(a));
                break;
            case "log":
                result = Math.log10(Double.valueOf(a));
                break;
            default:
                result = Double.valueOf(a);
        }
        return result;
    }


    static protected double calculation() {               //алгоритм вычисления результата по содержимому в строке
        double result;
        String oneNumeric = "";
        try {
            String[] operation = display.split(Pattern.quote(MainActivity.operator));
            if ((functionOn) && (countOperation < 2)) {
                operation[0] = String.valueOf(resultOfFunction);
                MainActivity.oneNumber = String.valueOf(resultOfFunction);
            }
            if (countOperation > 1) { //определение элементов операции по наличию оператора
                oneNumeric = MainActivity.oneNumber;
                operation[1] = display.substring(display.lastIndexOf(MainActivity.operator) + 1, display.length());
            } else
                oneNumeric = operation[0];
            result = simpleAction(oneNumeric, operation[1], MainActivity.operator);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }


    public static String toTen(String number, int osn) {            //перевод в десятичную систему счисления
        int result = 0;
        int num;
        for (int i = 0; i < number.length(); i++) {
            num = Character.digit(number.charAt(number.length() - i - 1), 10); //перевод целых неотрицательных чисел
            result += (Math.pow(osn, i)) * num;
        }
        String strResult = String.valueOf(result);                  //строковая переменная для работы с simpleAction
        return strResult;
    }
}
