package com.example.parsejson;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lv = findViewById(R.id.lv);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSON();
            }
        });
    }

    private void parseJSON() {
        String json = loadJSONFromAsset();
        if (json == null) return;

        try {
            JSONObject root = new JSONObject(json);

            // Lấy MaDM và TenDM
            String maDM = root.getString("MaDM");
            String tenDM = root.getString("TenDM");
            arrayList.add(maDM);
            arrayList.add(tenDM);

            // Lấy mảng Sanphams
            JSONArray sanphams = root.getJSONArray("Sanphams");
            for (int i = 0; i < sanphams.length(); i++) {
                JSONObject sp = sanphams.getJSONObject(i);

                String maSP = sp.getString("MaSP");
                String tenSP = sp.getString("TenSP");
                int soLuong = sp.getInt("SoLuong");
                int donGia = sp.getInt("DonGia");
                int thanhTien = sp.getInt("ThanhTien");
                String hinh = sp.getString("Hinh");

                arrayList.add(maSP + " - " + tenSP);
                arrayList.add(soLuong + " * " + donGia + " = " + thanhTien);
                arrayList.add(hinh);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("computer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
