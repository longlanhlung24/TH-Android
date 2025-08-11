package com.example.register_infor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editHoten, editCMND, editBosung;
    RadioButton rdoTC, rdoCD, rdoDH;
    CheckBox chkdocbao, chkdocsach, chkdoccoding;
    Button btnguitt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần
        editHoten = findViewById(R.id.editHoten);
        editCMND = findViewById(R.id.editCMND);
        editBosung = findViewById(R.id.editBosung);

        rdoTC = findViewById(R.id.rdoTC);
        rdoCD = findViewById(R.id.rdoCD);
        rdoDH = findViewById(R.id.rdoDH);

        chkdocbao = findViewById(R.id.chkdocbao);
        chkdocsach = findViewById(R.id.chkdocsach);
        chkdoccoding = findViewById(R.id.chkdoccoding);

        btnguitt = findViewById(R.id.btnguitt);

        btnguitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiThongTin();
            }
        });
    }


    private void hienThiThongTin() {
        String hoten = editHoten.getText().toString().trim();
        String cmnd = editCMND.getText().toString().trim();
        String bosung = editBosung.getText().toString().trim();

        String bangcap = "";
        if (rdoTC.isChecked()) bangcap = "Trung cấp";
        else if (rdoCD.isChecked()) bangcap = "Cao đẳng";
        else if (rdoDH.isChecked()) bangcap = "Đại học";

        String sothich = "";
        if (chkdocbao.isChecked()) sothich += "Đọc báo - ";
        if (chkdocsach.isChecked()) sothich += "Đọc sách - ";
        if (chkdoccoding.isChecked()) sothich += "Đọc coding - ";
        if (!sothich.isEmpty()) sothich = sothich.substring(0, sothich.length() - 3); // xóa dấu cuối

        String thongtin = hoten + "\n" +
                cmnd + "\n" +
                bangcap + "\n" +
                sothich + "\n" +
                "------------------------------\n" +
                "Thông tin bổ sung:\n" +
                bosung + "\n" +
                "------------------------------";

        // Hiển thị AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setMessage(thongtin);
        builder.setPositiveButton("Đóng", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Căn giữa nút "Đóng"
        Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (btnPositive != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            btnPositive.setLayoutParams(params);
            btnPositive.setGravity(Gravity.CENTER);
        }
    }


    // Xử lý nút Back với AlertDialog xác nhận
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Thoát app
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
