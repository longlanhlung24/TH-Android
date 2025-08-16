package com.example.dbbrowersqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Tên file DB đúng với file trong assets
    private static final String DATABASE_NAME = "qlsach.db";
    private static final String DB_PATH_SUFFIX = "/databases/";

    private ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);

        // 1) Copy CSDL từ assets vào thư mục databases nếu chưa có
        copyDatabaseFromAssetsIfNeeded();

        // 2) Mở DB và đọc dữ liệu
        loadDataToListView();
    }

    private void loadDataToListView() {
        String dbPath = getDbPath(); // /data/data/<package>/databases/qlsach.db
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

            // Lấy toàn bộ cột (id, tensach, tacgia) từ bảng tbsach
            c = db.query("tbsach", null, null, null, null, null, "id ASC");

            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(0);              // id
                    String tensach = c.getString(1);   // tensach
                    String tacgia = c.getString(2);    // tacgia
                    items.add(id + ". " + tensach + " - " + tacgia);
                } while (c.moveToNext());
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi đọc DB: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
    }

    private String getDbPath() {
        // /data/user/0/<package>/databases/qlsach.db (Android 10+) hoặc /data/data/<package>/databases/...
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void copyDatabaseFromAssetsIfNeeded() {
        File outFile = new File(getDbPath());
        if (outFile.exists()) return; // đã có rồi thì thôi

        // Đảm bảo thư mục /databases/ tồn tại
        File dir = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!dir.exists()) dir.mkdir();

        try (InputStream in = getAssets().open(DATABASE_NAME);
             OutputStream out = new FileOutputStream(outFile)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            Toast.makeText(this, "Đã copy CSDL vào hệ thống", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi copy DB: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
