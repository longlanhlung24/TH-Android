package com.example.dongabank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String DONGA_URL = "https://www.dongabank.com.vn/exchange/export";

    private TextView tvDate;
    private ListView lvRates;
    private ArrayList<Tygia> data;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate  = findViewById(R.id.tvDate);
        lvRates = findViewById(R.id.lvRates);

        data = new ArrayList<>();
        adapter = new MyArrayAdapter(this, R.layout.item_rate, data);
        lvRates.setAdapter(adapter);

        new LoadRatesTask().execute(DONGA_URL);
    }

    private class LoadRatesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return fetch(urls[0]);
        }

        @Override
        protected void onPostExecute(String body) {
            if (body == null || body.isEmpty()) {
                tvDate.setText("Không tải được dữ liệu.");
                return;
            }
            parseAndBind(body);
        }
    }

    // ---- HTTP ----
    private String fetch(String urlStr) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            // Thêm user-agent để giả lập request từ trình duyệt
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            conn.setConnectTimeout(12000);
            conn.setReadTimeout(12000);
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) conn.disconnect();
        }
        return sb.toString();
    }

    // ---- Parse JSON/JSONP của DongA ----
    private void parseAndBind(String raw) {
        try {
            // Nếu là JSONP kiểu:  var x = {...};
            int start = raw.indexOf('{');
            int end   = raw.lastIndexOf('}');
            String json = (start >= 0 && end > start) ? raw.substring(start, end + 1) : raw;

            JSONObject root = new JSONObject(json);

            // Lấy ngày/giờ
            String dateTime = root.optString("DateTime",
                    root.optString("datetime",
                            root.optString("date", "")));
            if (!dateTime.isEmpty()) tvDate.setText("Hôm nay: " + dateTime);

            // Mảng tỉ giá
            JSONArray arr = null;
            if (root.has("items"))        arr = root.getJSONArray("items");
            else if (root.has("Rates"))   arr = root.getJSONArray("Rates");
            else if (root.has("Currency"))arr = root.getJSONArray("Currency");
            else {
                // Lấy mảng đầu tiên tìm được (dùng keys() thay cho keySet())
                Iterator<String> keys = root.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (root.get(key) instanceof JSONArray) {
                        arr = root.getJSONArray(key);
                        break;
                    }
                }
            }

            if (arr == null) {
                tvDate.setText("Không tìm thấy dữ liệu tỉ giá.");
                return;
            }

            data.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);

                // Mã tiền tệ
                String code = o.optString("type",
                        o.optString("Type",
                                o.optString("code",
                                        o.optString("Code", "-"))));

                // Giá
                String muack = o.optString("muack", o.optString("Transfer", ""));
                String banck = o.optString("banck", o.optString("Sell", ""));
                String muatm = o.optString("muatm", o.optString("Buy", ""));
                String bantm = o.optString("bantm", o.optString("SellCash", banck));

                data.add(new Tygia(code, muack, banck, muatm, bantm));
            }

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            tvDate.setText("Lỗi phân tích dữ liệu.");
        }
    }
}
