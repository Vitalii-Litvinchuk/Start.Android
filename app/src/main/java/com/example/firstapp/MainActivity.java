package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private TextView btnFirstNumber;
    private TextView btnAction;

    String[] elements = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", ".", "Reset", "=", "+", "-", "*", "/"
    };

    ArrayList<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        btnFirstNumber = findViewById(R.id.number);
        btnAction = findViewById(R.id.action);
        buttons.addAll(0, Arrays.asList(
                findViewById(R.id.one),
                findViewById(R.id.two),
                findViewById(R.id.three),
                findViewById(R.id.four),
                findViewById(R.id.five),
                findViewById(R.id.six),
                findViewById(R.id.seven),
                findViewById(R.id.eight),
                findViewById(R.id.nine),
                findViewById(R.id.zero),
                findViewById(R.id.dot),
                findViewById(R.id.reset),
                findViewById(R.id.equal),
                findViewById(R.id.plus),
                findViewById(R.id.minus),
                findViewById(R.id.multiply),
                findViewById(R.id.division)
        ));

        buttons.forEach(button -> {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Culc(button.getText().toString());
                }
            });
        });
    }

    private String action = "";
    private double firstNumber = 0;
    private int resetCounter = 0;

    private boolean checkResetCounter() {
        if (resetCounter > 0) {
            resetCounter = 0;
            return true;
        } else return false;
    }

    private void changeAction(String to) {
        action = to;
        btnAction.setText(to);
    }

    private void changeFirstNumber(String to) {
        btnFirstNumber.setText(to);
        if (to.isEmpty())
            firstNumber = 0;
        else
            firstNumber = Double.parseDouble(to);
    }

    public void Culc(String element) {
        String mainText = text.getText().toString();
        if (mainText.equals("Error")) text.setText("");
        switch (element) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                checkResetCounter();
                text.append(element);
                break;
            case ".":
                checkResetCounter();
                if (!mainText.contains(".") && !mainText.isEmpty())
                    text.append(element);
                break;
            case "Reset":
                if (checkResetCounter()) {
                    changeAction("");
                    changeFirstNumber("");
                    text.setText("");
                    break;
                }
                text.setText("");
                ++resetCounter;
                break;
            case "=":
                checkResetCounter();
                String textSecondNumber = mainText;
                if (!btnFirstNumber.getText().toString().isEmpty() && !textSecondNumber.isEmpty()) {
                    double secondNumber = Double.parseDouble(textSecondNumber);
                    switch (action) {
                        case "+":
                            text.setText(Double.toString(firstNumber + secondNumber));
                            break;
                        case "-":
                            text.setText(Double.toString(firstNumber - secondNumber));
                            break;
                        case "*":
                            text.setText(Double.toString(firstNumber * secondNumber));
                            break;
                        case "/":
                            if (secondNumber == 0) {
                                text.setText("Error");
                                break;
                            }
                            text.setText(Double.toString(firstNumber / secondNumber));
                            break;
                    }
                    changeAction("");
                    changeFirstNumber("");
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                checkResetCounter();
                if (!action.isEmpty()) {
                    changeAction(element);
                    break;
                }
                String number = mainText;
                if (number.isEmpty()) break;
                changeFirstNumber(number);
                changeAction(element);
                text.setText("");
                break;
            default:
                changeAction("");
                changeFirstNumber("");
                text.setText("Error");
                break;
        }
    }


}