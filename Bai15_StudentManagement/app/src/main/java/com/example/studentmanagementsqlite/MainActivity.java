package com.example.studentmanagementsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtmlop, edttenlop, edtsiso;
    Button btninsert, btndelete, btnupdate, btnquery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtmlop = findViewById(R.id.edtmlop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        // Mở hoặc tạo DB
        DatabaseHelper helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();

        // Thêm
        btninsert.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("malop", edtmlop.getText().toString());
            values.put("tenlop", edttenlop.getText().toString());
            values.put("siso", Integer.parseInt(edtsiso.getText().toString()));
            db.insert("tbllop", null, values);
            Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
        });

        // Xóa
        btndelete.setOnClickListener(v -> {
            db.delete("tbllop", "malop=?", new String[]{edtmlop.getText().toString()});
            Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
        });

        // Cập nhật
        btnupdate.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("tenlop", edttenlop.getText().toString());
            values.put("siso", Integer.parseInt(edtsiso.getText().toString()));
            db.update("tbllop", values, "malop=?", new String[]{edtmlop.getText().toString()});
            Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
        });

        // Truy vấn
        btnquery.setOnClickListener(v -> {
            mylist.clear();
            Cursor c = db.query("tbllop", null, null, null, null, null, null);
            while (c.moveToNext()) {
                String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getInt(2);
                mylist.add(data);
            }
            c.close();
            myadapter.notifyDataSetChanged();
        });
    }
}
