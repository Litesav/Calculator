package com.example.litesav.Calculator;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.example.litesav.Calculator.MainActivity.buttons;
import static com.example.litesav.Calculator.MainActivity.display;
import static com.example.litesav.Calculator.MainActivity.functionOn;
import static com.example.litesav.Calculator.MainActivity.notationNumber;
import static com.example.litesav.Calculator.MainActivity.operator;
import static com.example.litesav.Calculator.MainActivity.operatorOn;
import static com.example.litesav.Calculator.MainActivity.output;
import static com.example.litesav.Calculator.MainActivity.someButtons;
import static com.example.litesav.Calculator.MainActivity.someStandartColor;
import static com.example.litesav.Calculator.MainActivity.standartColor;

public class Display {
    static protected void updateScreen() {               //обновление содержимого экрана по переменной display
        MainActivity.input.setText(display);
    }


    static protected void clear() {
        MainActivity.countOperation = 0;
        output = "";
        operatorOn = false;
        functionOn = false;
        MainActivity.operatorFlag = false;
        if (!notationNumber.equals("")) display = "0";
        MainActivity.operator = "";
        MainActivity.results.setText("");
    }


    static protected void formatResult(double result) {               //форматированный вывод результата
        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);
        if (!notationNumber.equals("10") && (result < 0)) {
            MainActivity.results.setText("отрицательное число");
        } else {
            output = df.format(result);
            MainActivity.results.setText(output);
            MainActivity.functionFlag = true;
        }

    }


    static protected void deactivate() {         //деактивация кнопок в соответствии со системой счисления
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(standartColor);
        }
        for (int i = Integer.valueOf(notationNumber); i < buttons.length; i++) {
            buttons[i].setEnabled(false);
            buttons[i].setBackgroundColor(Color.GRAY);
        }
        if (notationNumber.equals("10")) {             //включение группы кнопок для рабоы с десятичной системой счисления
            for (int i = 0; i < someButtons.length; i++) {
                someButtons[i].setEnabled(true);
                someButtons[i].setBackgroundColor(someStandartColor);
            }
            someButtons[0].setBackgroundColor(standartColor);

        }
    }

    public static int addSymbol(View v) { //добавление символа с проверками на ввод
        Button b = (Button) v;
        String str;
        char lastSymbol = display.charAt(display.length() - 1);
        boolean flag = (lastSymbol == '+') || (lastSymbol == '-') || (lastSymbol == '×') || (lastSymbol == '÷');
        if (operatorOn == true)
            str = display.substring(display.lastIndexOf(operator), display.length());
        else str = display.substring(0, display.length());
        if (String.valueOf(b.getText()).equals(".")) {
            if (flag) return 0;
            if (str.lastIndexOf(".") == -1) {
                display += ".";
            } else return 0;
        } else if ((display.equals("0")) && (!b.getText().equals("."))) {
            display = String.valueOf(b.getText());
        } else
            display += b.getText();
        return 1;

    }
}
