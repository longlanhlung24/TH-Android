package com.example.autocompleteprovince;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView selectedProvince;
    AutoCompleteTextView autoCompleteSingle;
    MultiAutoCompleteTextView autoCompleteMulti;

    String[] provinces = {
            "Ha Noi", "Hue", "Sai Gon",
            "Ha Giang", "Hai An", "Kien Giang",
            "Lam Dong", "Long Khanh"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views
        selectedProvince = findViewById(R.id.selectedProvince);
        autoCompleteSingle = findViewById(R.id.autoCompleteSingle);
        autoCompleteMulti = findViewById(R.id.autoCompleteMulti);

        // Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provinces
        );
        autoCompleteSingle.setAdapter(adapter);
        autoCompleteMulti.setAdapter(adapter);
        autoCompleteMulti.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Update selectedProvince text when user types
        autoCompleteSingle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedProvince.setText(autoCompleteSingle.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}