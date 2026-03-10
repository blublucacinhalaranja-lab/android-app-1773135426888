package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewResult;
    private String currentInput = "";
    private String currentOperator = "";
    private BigDecimal firstOperand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);

        // Number Buttons
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        // Operator Buttons
        findViewById(R.id.buttonPlus).setOnClickListener(this);
        findViewById(R.id.buttonMinus).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonPercent).setOnClickListener(this);
        findViewById(R.id.buttonPlusMinus).setOnClickListener(this);

        // Functionality Buttons
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonDecimal).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        switch (button.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                handleNumberInput(buttonText);
                break;
            case R.id.buttonDecimal:
                handleDecimalInput();
                break;
            case R.id.buttonPlus:
            case R.id.buttonMinus:
            case R.id.buttonMultiply:
            case R.id.buttonDivide:
                handleOperatorInput(buttonText);
                break;
            case R.id.buttonPercent:
                handlePercentage();
                break;
            case R.id.buttonPlusMinus:
                handlePlusMinus();
                break;
            case R.id.buttonEquals:
                handleEquals();
                break;
            case R.id.buttonClear:
                handleClear();
                break;
        }
    }

    private void handleNumberInput(String number) {
        if (currentInput.equals("0")) {
            currentInput = number;
        } else {
            currentInput += number;
        }
        updateResultTextView();
    }

    private void handleDecimalInput() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput = "0.";
            } else {
                currentInput += ".";
            }
            updateResultTextView();
        }
    }

    private void handleOperatorInput(String operator) {
        if (!currentInput.isEmpty()) {
            if (firstOperand == null) {
                firstOperand = new BigDecimal(currentInput);
            } else {
                handleEquals();
                firstOperand = new BigDecimal(currentInput);

            }
            currentOperator = operator;
            currentInput = "";
            updateResultTextView();
        }
    }

    private void handlePercentage() {
        if (!currentInput.isEmpty()) {
            BigDecimal value = new BigDecimal(currentInput);
            value = value.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            currentInput = value.toString();
            updateResultTextView();
        }
    }

    private void handlePlusMinus() {
        if (!currentInput.isEmpty()) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            updateResultTextView();
        }
    }

    private void handleEquals() {
        if (!currentInput.isEmpty() && firstOperand != null) {
            BigDecimal secondOperand = new BigDecimal(currentInput);
            BigDecimal result = calculateResult(firstOperand, secondOperand, currentOperator);

            if (result != null) {
                currentInput = result.stripTrailingZeros().toPlainString();
                textViewResult.setText(currentInput);
            }

            firstOperand = null;
            currentOperator = "";
            currentInput = "";
        }
    }

    private BigDecimal calculateResult(BigDecimal firstOperand, BigDecimal secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand.add(secondOperand);
            case "-":
                return firstOperand.subtract(secondOperand);
            case "*":
                return firstOperand.multiply(secondOperand);
            case "/":
                if (secondOperand.compareTo(BigDecimal.ZERO) != 0) {
                    return firstOperand.divide(secondOperand, 10, RoundingMode.HALF_UP);
                } else {
                    currentInput = "Error";
                    return null;
                }
            default:
                return null;
        }
    }

    private void handleClear() {
        currentInput = "";
        currentOperator = "";
        firstOperand = null;
        updateResultTextView();
    }

    private void updateResultTextView() {
        if (currentInput.isEmpty()) {
            textViewResult.setText("0");
        } else {
            textViewResult.setText(currentInput);
        }
    }
}
```

```yaml
//