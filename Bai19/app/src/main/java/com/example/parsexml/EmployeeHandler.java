import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class EmployeeHandler extends DefaultHandler {
    private final List<Employee> employees = new ArrayList<>();
    private Employee current;
    private StringBuilder text = new StringBuilder();

    public List<Employee> getEmployees() { return employees; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("employee".equalsIgnoreCase(qName)) {
            current = new Employee();
            current.setId(attributes.getValue("id"));
            current.setTitle(attributes.getValue("title"));
        }
        // reset bộ đệm ký tự mỗi khi vào 1 tag mới
        text.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // SAX có thể gọi nhiều lần → gom lại bằng StringBuilder
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = text.toString().trim();
        if ("name".equalsIgnoreCase(qName)) {
            current.setName(value);
        } else if ("phone".equalsIgnoreCase(qName)) {
            current.setPhone(value);
        } else if ("employee".equalsIgnoreCase(qName)) {
            employees.add(current);
        }
    }
}
