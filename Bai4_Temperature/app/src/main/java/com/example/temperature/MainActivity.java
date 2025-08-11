package com.example.temperature;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.DecimalFormat;

public class MainActivity extends Activity {

    EditText edttoC, edttoF;
    Button btnfc, btncf, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttoC = findViewById(R.id.edttoC);
        edttoF = findViewById(R.id.edttoF);
        btnfc = findViewById(R.id.btnfc);
        btncf = findViewById(R.id.btncf);
        btnClear = findViewById(R.id.btnClear);

        // Fahrenheit -> Celsius
        btnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String doF = edttoF.getText().toString();
                if (!doF.isEmpty()) {
                    int F = Integer.parseInt(doF);
                    double C = (F - 32) * 5.0 / 9;
                    edttoC.setText(dcf.format(C));
                }
            }
        });

        // Celsius -> Fahrenheit
        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String doC = edttoC.getText().toString();
                if (!doC.isEmpty()) {
                    int C = Integer.parseInt(doC);
                    double F = C * 9.0 / 5 + 32;
                    edttoF.setText(dcf.format(F));
                }
            }
        });

        // Xóa dữ liệu
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttoC.setText("");
                edttoF.setText("");
            }
        });
    }
}
