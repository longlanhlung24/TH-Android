package com.example.appvnexpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ListNew> arrayList;
    MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new MyArrayAdapter(this, R.layout.listview, arrayList);
        listView.setAdapter(adapter);

        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String link = arrayList.get(position).getLink();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        });
    }

    private class ReadRSS extends AsyncTask<String, Void, ArrayList<ListNew>> {
        @Override
        protected ArrayList<ListNew> doInBackground(String... strings) {
            ArrayList<ListNew> temp = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream, "UTF-8");

                int eventType = parser.getEventType();
                String title = "", link = "", description = "", imageUrl = "";
                boolean insideItem = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();
                    if (eventType == XmlPullParser.START_TAG) {
                        if (tagName.equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (insideItem) {
                            if (tagName.equalsIgnoreCase("title")) {
                                title = parser.nextText();
                            } else if (tagName.equalsIgnoreCase("link")) {
                                link = parser.nextText();
                            } else if (tagName.equalsIgnoreCase("description")) {
                                String cdata = parser.nextText();
                                int imgStart = cdata.indexOf("src=\"") + 5;
                                int imgEnd = cdata.indexOf("\"", imgStart);
                                if (imgStart > 4 && imgEnd > imgStart) {
                                    imageUrl = cdata.substring(imgStart, imgEnd);
                                }
                                description = cdata.replaceAll("<.*?>", "").trim();
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && tagName.equalsIgnoreCase("item")) {
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openConnection().getInputStream());
                            temp.add(new ListNew(bitmap, title, description, link));
                        }
                        insideItem = false;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<ListNew> lists) {
            arrayList.clear();
            arrayList.addAll(lists);
            adapter.notifyDataSetChanged();
        }
    }
}
