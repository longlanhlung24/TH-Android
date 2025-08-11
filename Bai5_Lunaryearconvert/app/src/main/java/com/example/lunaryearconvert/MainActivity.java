package com.example.lunaryearconvert;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText edtNamDuong;
    TextView txtKetQua;
    Button btnDoi;

    String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mẹo", "Thìn", "Tỵ", "Ngọ", "Mùi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNamDuong = findViewById(R.id.edtNamDuong);
        txtKetQua = findViewById(R.id.textViewKetQua);
        btnDoi = findViewById(R.id.button1);

        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edtNamDuong.getText().toString().trim();

                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập năm dương lịch", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int namDuong = Integer.parseInt(input);
                    String canStr = can[namDuong % 10];
                    String chiStr = chi[namDuong % 12];
                    String ketQua = canStr + " " + chiStr;

                    // Hiển thị trong TextView
                    txtKetQua.setText(ketQua);

                    // Hiển thị Toast
                    Toast.makeText(MainActivity.this, "Năm âm lịch là: " + ketQua, Toast.LENGTH_SHORT).show();

                    // Hiển thị AlertDialog
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Kết quả chuyển đổi")
                            .setMessage("Năm âm lịch là: " + ketQua)
                            .setPositiveButton("OK", null)
                            .show();

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
