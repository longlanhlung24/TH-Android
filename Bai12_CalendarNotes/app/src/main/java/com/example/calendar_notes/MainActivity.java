package com.example.calendar_notes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    EditText edtwork, edthour, edtmin;
    Button btnadd;
    ListView listView;
    ArrayList<String> works;
    ArrayAdapter<String> adapter;
    TextView tvdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        edtwork = findViewById(R.id.edtwork);
        edthour = findViewById(R.id.edthour);
        edtmin = findViewById(R.id.edtmin);
        btnadd = findViewById(R.id.btnadd);
        listView = findViewById(R.id.listView);
        tvdate = findViewById(R.id.tvdate);

        // Hiển thị ngày hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        tvdate.setText("Hôm Nay: " + currentDate);

        // Danh sách công việc
        works = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, works);
        listView.setAdapter(adapter);

        // Bắt sự kiện click nút Add
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String work = edtwork.getText().toString();
                String hour = edthour.getText().toString();
                String minute = edtmin.getText().toString();

                if (work.isEmpty() || hour.isEmpty() || minute.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String item = "+ " + work + " : " + hour + ":" + minute;
                works.add(item);
                adapter.notifyDataSetChanged();

                // Xóa nội dung sau khi thêm
                edtwork.setText("");
                edthour.setText("");
                edtmin.setText("");
            }
        });
    }
}
