import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parsexml.R;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        listView = findViewById(R.id.listView);

        btnParse.setOnClickListener(v -> parseXML());
    }

    private void parseXML() {
        try {
            // 1) Mở file trong assets
            InputStream is = getAssets().open("employee.xml");

            // 2) Khởi tạo SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            // 3) Gán handler và parse
            EmployeeHandler handler = new EmployeeHandler();
            xmlReader.setContentHandler(handler);

            InputSource inputSource = new InputSource(is);
            inputSource.setEncoding("UTF-8");  // tránh lỗi dấu tiếng Việt
            xmlReader.parse(inputSource);

            // 4) Lấy kết quả → đổ ra ListView
            List<Employee> list = handler.getEmployees();
            List<String> viewData = new ArrayList<>();
            for (Employee e : list) {
                viewData.add(
                        e.getId() + "-" + e.getTitle() + "-" + e.getName() + "-" + e.getPhone()
                );
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, viewData
            );
            listView.setAdapter(adapter);
            Toast.makeText(this, "Parse thành công " + list.size() + " nhân viên", Toast.LENGTH_SHORT).show();

            is.close(); // đóng stream (thói quen tốt)
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Lỗi: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
