package com.example.sumsharepreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB;
    TextView tvKetQua, tvLichSu;
    Button btnTong, btnClear;
    SharedPreferences sharedPreferences;
    String history = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edt_a);
        edtB = findViewById(R.id.edt_b);
        tvKetQua = findViewById(R.id.tv_ketqua);
        tvLichSu = findViewById(R.id.tv_lichsu);
        btnTong = findViewById(R.id.btn_tong);
        btnClear = findViewById(R.id.btn_clear);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Lấy lịch sử đã lưu
        history = sharedPreferences.getString("history", "");
        tvLichSu.setText(history);

        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strA = edtA.getText().toString();
                String strB = edtB.getText().toString();

                if (strA.isEmpty() || strB.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập đủ A và B", Toast.LENGTH_SHORT).show();
                    return;
                }

                int a = Integer.parseInt(strA);
                int b = Integer.parseInt(strB);
                int sum = a + b;

                tvKetQua.setText(String.valueOf(sum));

                // Cập nhật lịch sử
                String newEntry = a + " + " + b + " = " + sum;
                history += newEntry + "\n";

                // Lưu vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("history", history);
                editor.apply();

                // Hiển thị lịch sử
                tvLichSu.setText(history);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = "";
                tvLichSu.setText("");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("history");
                editor.apply();
            }
        });
    }
}
