package com.example.internetworking;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class PizzaXmlHandler extends DefaultHandler {

    private List<Pizza> pizzas = new ArrayList<>();
    private Pizza currentPizza;
    private StringBuilder content = new StringBuilder();

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        content.setLength(0); // reset buffer
        String tag = qName.toLowerCase();
        if (tag.equals("item")) {
            currentPizza = new Pizza();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String tag = qName.toLowerCase();
        String text = content.toString().trim();

        if (currentPizza != null) {
            switch (tag) {
                case "id":
                    try { currentPizza.setId(Integer.parseInt(text)); } catch (NumberFormatException ignored) {}
                    break;
                case "name":
                    currentPizza.setName(text);
                    break;
                case "cost":
                    currentPizza.setCost(text);
                    break;
                case "description":
                    currentPizza.setDescription(text);
                    break;
                case "item":
                    pizzas.add(currentPizza);
                    currentPizza = null;
                    break;
            }
        }
    }
}
