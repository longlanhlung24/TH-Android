package com.example.dongabank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Tygia> {
    private final int resource;

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<Tygia> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Tygia item = getItem(position);
        if (item != null) {
            TextView tvCode  = v.findViewById(R.id.tvCode);
            TextView tvLine1 = v.findViewById(R.id.tvLine1);
            TextView tvLine2 = v.findViewById(R.id.tvLine2);

            tvCode.setText(item.getCode());
            tvLine1.setText("MuaCK: " + item.getMuaCK() + "     BanCK: " + item.getBanCK());
            tvLine2.setText("MuaTM: " + item.getMuaTM() + "     BanTM: " + item.getBanTM());
        }
        return v;
    }
}
