package com.example.litesav.Calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SettingNotation extends AppCompatActivity {
    public static int mainPosition = 8;
    protected static String numberOfNotation = "10";
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerProcess();

    }

    public void spinnerProcess() {
        Integer[] select = new Integer[9];
        fillingArray(select);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, select);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("spinnerOfSelect");
        spinner.setSelection(mainPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                numberOfNotation = spinner.getSelectedItem().toString();
                mainPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", numberOfNotation);
        setResult(RESULT_OK, intent);
        finish();


    }

    private Integer[] fillingArray(Integer[] nums) {
        int num = 1;
        for (int i = 0; i < 9; i++) {
            num++;
            nums[i] = num;
        }
        return nums;
    }


}
