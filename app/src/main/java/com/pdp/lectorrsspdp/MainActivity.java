package com.pdp.lectorrsspdp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements DialogFiltrosListener,DialogAddListener{

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

    private ArrayList<Rss> rssList;

    private ArrayList<String> categorias;

    private RssItemContainer itemContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón filtros
        final Button btnFiltros = (Button) findViewById(R.id.btnFiltros);
        btnFiltros.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment dialogFiltros = new DialogFiltros();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("categorias",categorias);
                dialogFiltros.setArguments(bundle);
                dialogFiltros.show(getSupportFragmentManager(), "DialogFiltros");
            }
        });

        // Botón añadir
        final Button btnAnyadir = (Button) findViewById(R.id.btnAdd);
        btnAnyadir.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment dialogAdd = new DialogAdd();
                dialogAdd.show(getSupportFragmentManager(), "DialogAdd");
            }
        });
        // Obtener la lista
        listView = (ListView)findViewById(R.id.lista);

        final FloatingActionButton fabUpdate = (FloatingActionButton) findViewById(R.id.update);
        fabUpdate.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new LoadRssData().execute();
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Incializar arrays
        categorias = new ArrayList<String>();
        addCategorias();
        rssList = new ArrayList<Rss>();
        addDefaultFeeds();
        itemContainer = new RssItemContainer();

        if (networkInfo != null && networkInfo.isConnected()) {
            new LoadRssData().execute();
        }

        // Regisgrar escucha de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RssAdapterItem item = (RssAdapterItem) rssAdapter.getItem(position);

                // Obtene url de la entrada seleccionada
                Log.i(TAG,"Url clicada: " + item.getItem().getLink());
                String url = item.getItem().getLink().toString();

                // Nuevo intent expl�cito
                Intent i = new Intent(MainActivity.this, DetailActivity.class);

                // Setear url
                i.putExtra("url-extra", url);

                // Iniciar actividad
                startActivity(i);
            }
        });
    }

    private void addCategorias() {
        this.categorias.add("Noticias");
        this.categorias.add("Deportes");
        this.categorias.add("Tecnología");
        this.categorias.add("Otros");
    }

    private void addDefaultFeeds() {
        this.rssList.add(new Rss("http://ep00.epimg.net/rss/elpais/portada.xml","Noticias"));
        this.rssList.add(new Rss("http://as.com/rss/tags/ultimas_noticias.xml","Deportes"));
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

    @Override
    public void onSelectedOptions(ArrayList<String> selected) {
        categorias = selected;
        new LoadRssData().execute();
    }

    @Override
    public void addRss(String url, String categoria) {
        rssList.add(new Rss(url,categoria));
        new LoadRssData().execute();
    }

    public class LoadRssData extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            RSSReader reader = new RSSReader();
            ArrayList<RssFeedContainer> feeds = new ArrayList<RssFeedContainer>();
            try {

                for(Rss r : rssList){
                    if(categorias.contains(r.getCategoria())) {
                        RSSFeed feed = reader.load(r.getUrl());
                        feeds.add(new RssFeedContainer(r.getCategoria(),feed));
                    }
                    if(feeds != null) {
                        itemContainer.empty();
                        for(RssFeedContainer f : feeds){
                            for(RSSItem i : f.getFeed().getItems()){
                                itemContainer.addItem(new RssAdapterItem(i,f.getFeed().getTitle(),f.getCategoria()));
                            }
                        }
                        itemContainer.sortByDate();
                    }
                }

                return "Exito";

            } catch (RSSReaderException e) {
                e.printStackTrace();
            }
            return "Error";
        }

        @Override
        protected void onPostExecute(String info) {
            super.onPostExecute(info);
            if(info.equals("Exito")) {
                rssAdapter = new RssAdapter(MainActivity.this,itemContainer.getItems());
                listView.setAdapter(rssAdapter);
            }
        }
    }

}
