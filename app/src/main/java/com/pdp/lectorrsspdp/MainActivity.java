package com.pdp.lectorrsspdp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.util.ArrayList;


/**
 * Creado por Hermosa Programaci�n
 *
 * Actividad principal que representa el Home de la aplicaci�n
 */

public class MainActivity extends AppCompatActivity {

    /*
    Etiqueta de depuraci�n
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
    URL del feed
     */
    public static final String URL_FEED = "http://www.forbes.com/most-popular/feed/"; //"http://www.forbes.com/most-popular/feed/";

    /*
    Lista
     */
    private ListView listView;

    /*
    Adaptador
     */
    //private FeedAdapter adapter;

    private RssAdapter rssAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener la lista
        listView = (ListView)findViewById(R.id.lista);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
          if (networkInfo != null && networkInfo.isConnected()) {
            new LoadRssData().execute("http://nypost.com/feed/");
        }

        // Regisgrar escucha de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RSSItem item = (RSSItem) rssAdapter.getItem(position);

                // Obtene url de la entrada seleccionada
                Log.i(TAG,"Url clicada: " + item.getLink());
                String url = item.getLink().toString();

                // Nuevo intent expl�cito
                Intent i = new Intent(MainActivity.this, DetailActivity.class);

                // Setear url
                i.putExtra("url-extra", url);

                // Iniciar actividad
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // Item boton conectar a un dispositivo bluetooth
        if(id ==R.id.action_settings){

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public class LoadRssData extends AsyncTask<String,Void,RSSFeed>{

        @Override
        protected RSSFeed doInBackground(String... params) {
            RSSReader test = new RSSReader();
            String uri = params[0];
            try {
                RSSFeed feed = test.load(uri);
                return feed;

            } catch (RSSReaderException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSFeed rssFeed) {
            super.onPostExecute(rssFeed);
            if(rssFeed != null) {
                Log.i(TAG, "El feed es: " + rssFeed.getItems().toString());
                ArrayList<RSSItem> auxArray = new ArrayList<RSSItem>();
                auxArray.addAll(rssFeed.getItems());

                rssAdapter = new RssAdapter(MainActivity.this, rssFeed.getTitle(),auxArray);
                listView.setAdapter(rssAdapter);
            }
        }
    }


}
