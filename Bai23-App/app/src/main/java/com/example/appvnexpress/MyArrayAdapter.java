package com.example.appvnexpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<ListNew> {
    private Context context;
    private int resource;
    private List<ListNew> objects;

    public MyArrayAdapter(Context context, int resource, List<ListNew> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDescription = view.findViewById(R.id.txtDescription);

        ListNew item = objects.get(position);
        imageView.setImageBitmap(item.getImage());
        txtTitle.setText(item.getTitle());
        txtDescription.setText(item.getDescription());

        return view;
    }
}
