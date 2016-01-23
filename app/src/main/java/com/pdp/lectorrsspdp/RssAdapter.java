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


public class RssAdapter extends ArrayAdapter<RSSItem> {

    private String rssTitle;

    public RssAdapter(Context context, String rssTitle,ArrayList<RSSItem> items) {
        super(context, 0, items);
        this.rssTitle = rssTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RSSItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        TextView tvPublisher = (TextView) convertView.findViewById(R.id.publisher);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.descripcion);
        TextView tvFecha = (TextView) convertView.findViewById(R.id.fechaPub);
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.imagen);

        tvPublisher.setText(this.rssTitle);
        tvTitulo.setText(item.getTitle());
        tvDescripcion.setText(item.getDescription());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("es"));
        tvFecha.setText(df.format(item.getPubDate()));

        if(item.getThumbnails().size()>0) {
            ivThumbnail.setImageURI(item.getThumbnails().get(0).getUrl());
        }
        return convertView;
    }
}
