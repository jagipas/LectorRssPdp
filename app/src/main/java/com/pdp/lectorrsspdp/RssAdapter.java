package com.pdp.lectorrsspdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.mcsoxford.rss.RSSItem;

import java.util.ArrayList;

/**
 * Created by Marc on 22/01/2016.
 */
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
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.imagen);

        tvPublisher.setText(this.rssTitle);
        tvTitulo.setText(item.getTitle());
        tvDescripcion.setText(item.getDescription());

        if(item.getThumbnails().size()>0) {
            ivThumbnail.setImageURI(item.getThumbnails().get(0).getUrl());
        }
        return convertView;
    }
}
