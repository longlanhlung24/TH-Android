package com.example.internetworking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.internetworking.Pizza;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    private static final String PIZZA_URL = "https://api.androidhive.info/pizza/?format=xml";

    private Button btnParse;
    private ListView lvPizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lvPizzas = findViewById(R.id.lvPizzas);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadXmlTask().execute(PIZZA_URL);
            }
        });
    }

    // AsyncTask chạy nền
    private class LoadXmlTask extends AsyncTask<String, Void, List<Pizza>> {

        private Exception error;
        private String rawXml;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnParse.setEnabled(false);
            Toast.makeText(MainActivity.this, "Đang tải dữ liệu...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Pizza> doInBackground(String... params) {
            String url = params[0];
            try {
                // 1) Tải XML về dạng String
                XMLParser parser = new XMLParser();
                rawXml = parser.getXmlFromUrl(url);

                // Log toàn bộ XML ra Logcat
                Log.d("DEBUG_XML", "Nội dung XML:\n" + rawXml);

                if (rawXml == null || rawXml.trim().isEmpty()) {
                    return null;
                }

                // 2) Parse XML bằng SAX
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();

                PizzaXmlHandler handler = new PizzaXmlHandler();
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(new StringReader(rawXml.trim())));

                return handler.getPizzas();
            } catch (Exception e) {
                error = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Pizza> pizzas) {
            super.onPostExecute(pizzas);
            btnParse.setEnabled(true);

            if (error != null) {
                Toast.makeText(MainActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            if (pizzas == null || pizzas.isEmpty()) {
                Toast.makeText(MainActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị danh sách pizza
            List<String> display = new ArrayList<>();
            for (Pizza p : pizzas) {
                String line = p.getId() + ". " + p.getName();
                if (p.getCost() != null && !p.getCost().isEmpty())
                    line += " - $" + p.getCost();
                display.add(line);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    display
            );
            lvPizzas.setAdapter(adapter);
        }
    }
}
