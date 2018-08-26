package com.example.litesav.Calculator;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.litesav.Calculator.Calculating.simpleAction;
import static com.example.litesav.Calculator.Display.addSymbol;
import static com.example.litesav.Calculator.Display.formatResult;
import static com.example.litesav.Calculator.Display.updateScreen;

public class MainActivity extends AppCompatActivity {

    static protected Button[] buttons, someButtons;        //категории кнопок для деактивации (при смене системы счисления)
    static protected int standartColor, someStandartColor;
    static protected TextView input;
    static protected TextView results;
    static protected String display = "0";
    static protected String output = "";                     //для временного хранения результата
    static protected String operator = "";
    static protected String oneNumber = "";
    static protected double resultOfFunction = 0;
    static protected boolean functionOn = false;             //последней использована функция
    static protected boolean functionFlag = true; //хранит первое число при операциях
    static protected boolean operatorOn = false;             //последним использован оператор
    //возможность использования функции
    static protected boolean operatorFlag = false;
    static String notationNumber = "10";                   //система счисления по умолчанию
    static int countOperation = 0;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, bPoint, bLog, bExp, bSin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        results = findViewById(R.id.result);
        input.setText(display);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        bPoint = findViewById(R.id.buttonPoint);
        bLog = findViewById(R.id.buttonLog);
        bSin = findViewById(R.id.buttonSin);
        bExp = findViewById(R.id.buttonExp);

        standartColor = ((ColorDrawable) button0.getBackground()).getColor();
        someStandartColor = ((ColorDrawable) bSin.getBackground()).getColor();
        buttons = new Button[]{button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, bPoint, bLog, bSin, bExp};
        someButtons = new Button[]{bPoint, bLog, bSin, bExp}; //группа кнопок, недоступная при работе с недесятичной системой счисления
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                Intent Intent = new Intent(this, SettingNotation.class);
                startActivityForResult(Intent, 1);
                break;
            }
            case R.id.exit:
                System.exit(0);
                break;
            case R.id.about: {
                Intent Intent = new Intent(this, About.class);
                startActivity(Intent);
            }
        }


        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String name = data.getStringExtra("name");
        notationNumber = name;
        Display.deactivate();
        Display.clear();
        updateScreen();
    }


    public void onClickNumber(View v) {
        char lastSymbol = display.charAt(display.length() - 1);
        boolean flag = lastSymbol == ')';
        if (flag) return;
        if (operatorOn) { //проверяем был ли введен знак оператора до этого
            int inf = addSymbol(v);   //результат фунции добавления символа
            updateScreen();
            if (inf != 0)
                formatResult(Calculating.calculation());        //вычисление и вывод результата
        } else { //если знак оператора не был введен, то заполняется последовательность цифр без действий
            addSymbol(v);
            updateScreen();
        }
        operatorFlag = false;
        functionFlag = true;
    }

    public void onClickOperator(View v) {
        if (display.charAt(display.length() - 1) == '.') return;
        countOperation++;
        oneNumber = output;
        Button b = (Button) v;
        if (operatorFlag == true) {
            countOperation--;
            display = display.substring(0, display.length() - 1) + b.getText().charAt(0); //замена существующего оператора
        } else display += b.getText();
        operator = (b.getText()).toString();
        updateScreen();
        display = String.valueOf(display);
        operatorOn = true;
        operatorFlag = true;
        functionFlag = false;
    }

    public void onClickFunction(View v) {
        char lastSymbol = display.charAt(display.length() - 1);
        boolean flag = (lastSymbol == '+') || (lastSymbol == '-') || (lastSymbol == '×') || (lastSymbol == '÷');
        if (flag) return;
        if (functionFlag) {
            Button b = (Button) v;
            String inputFunction, functionOperator;
            if (operator == "") {
                inputFunction = String.valueOf(input.getText());
                if (functionOn) {
                    inputFunction = String.valueOf(results.getText());
                    display = String.valueOf(b.getText()) + "(" + results.getText() + ")";
                } else
                    display = String.valueOf(b.getText()) + "(" + display + ")";
            } else {
                String num = display.substring(display.lastIndexOf(operator) + 1, display.length());
                inputFunction = String.valueOf(results.getText());
                display = String.valueOf(b.getText()) + "(" + results.getText() + ")";
            }
            functionOperator = (b.getText()).toString();
            try {
                resultOfFunction = simpleAction(inputFunction, functionOperator);
            } catch (Exception e) {
                resultOfFunction = 0;
            }
            updateScreen();
            formatResult(resultOfFunction);
        }
        functionOn = true;
    }

    public void onClickEqual(View v) {
        if (display == "0") return;
        if (!(output.equals(""))) {
            display = output;
            results.setText("");
        }
        updateScreen();
    }

    public void onClickClear(View v) {
        Display.clear();                               //перевести к исходному состоянию
        updateScreen();
    }


}

