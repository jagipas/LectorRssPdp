package com.pdp.lectorrsspdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.mcsoxford.rss.RSSItem;

import java.util.ArrayList;

/**
 * Created by Marc on 22/01/2016.
 */
public class RssAdapter extends ArrayAdapter<RSSItem> {

    public RssAdapter(Context context, ArrayList<RSSItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RSSItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        TextView tvTitulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.descripcion);
        tvTitulo.setText(item.getTitle());
        tvDescripcion.setText(item.getDescription());
        return convertView;
    }
}
