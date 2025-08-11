package com.example.phonelist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến
    ListView listPhone;
    TextView txtSelection;

    // Danh sách điện thoại
    String[] phones = {
            "Iphone 7", "SamSung Galaxy S7", "Nokia Lumia 730",
            "Sony Xperia XZ", "HTC One E9"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        listPhone = findViewById(R.id.listPhone);
        txtSelection = findViewById(R.id.txtSelection);

        // Adapter để gắn dữ liệu vào ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                phones
        );

        listPhone.setAdapter(adapter);

        // Bắt sự kiện chọn item
        listPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Gán nội dung cho TextView
                txtSelection.setText("Vị trí " + position + " : " + phones[position]);
            }
        });
    }
}
