package com.pdp.lectorrsspdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.mcsoxford.rss.RSSItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class RssAdapter extends ArrayAdapter<RssAdapterItem> {


    public RssAdapter(Context context,ArrayList<RssAdapterItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RssAdapterItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        TextView tvPublisher = (TextView) convertView.findViewById(R.id.publisher);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.descripcion);
        TextView tvFecha = (TextView) convertView.findViewById(R.id.fechaPub);

        tvPublisher.setText(item.getRssTitle());
        tvTitulo.setText(item.getItem().getTitle());
        tvDescripcion.setText(item.getItem().getDescription());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("es"));
        tvFecha.setText(df.format(item.getItem().getPubDate()));

        return convertView;
    }
}
