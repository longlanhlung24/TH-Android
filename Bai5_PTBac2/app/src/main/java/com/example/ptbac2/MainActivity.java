package com.example.ptbac2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edta, edtb, edtc;
    TextView tvKQ;
    Button btnTiepTuc, btnGiai, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        edtc = findViewById(R.id.edtc);
        tvKQ = findViewById(R.id.tvKQ);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        btnGiai = findViewById(R.id.btnGiai);
        btnThoat = findViewById(R.id.btnThoat);

        // Nút "Tiếp tục" - Xóa trắng
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edta.setText("");
                edtb.setText("");
                edtc.setText("");
                tvKQ.setText("");
                edta.requestFocus();
            }
        });

        // Nút "Giải PT"
        btnGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double a = Double.parseDouble(edta.getText().toString());
                    double b = Double.parseDouble(edtb.getText().toString());
                    double c = Double.parseDouble(edtc.getText().toString());

                    if (a == 0) {
                        Toast.makeText(MainActivity.this, "Đây không phải PT bậc 2 (a phải khác 0)", Toast.LENGTH_LONG).show();
                        return;
                    }

                    double delta = b * b - 4 * a * c;
                    String ketQua;

                    if (delta < 0) {
                        ketQua = "Phương trình vô nghiệm.";
                    } else if (delta == 0) {
                        double x = -b / (2 * a);
                        ketQua = "Phương trình có nghiệm kép x = " + x;
                    } else {
                        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                        ketQua = "Phương trình có 2 nghiệm:\nx1 = " + x1 + "\nx2 = " + x2;
                    }

                    tvKQ.setText(ketQua);

                    // Hiển thị AlertDialog với kết quả
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Kết quả")
                            .setMessage(ketQua)
                            .setPositiveButton("Đóng", null)
                            .show();

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ và đúng định dạng a, b, c", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Nút "Thoát"
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hộp thoại xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận thoát")
                        .setMessage("Bạn có chắc chắn muốn thoát?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish(); // Thoát app
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
    }
}
