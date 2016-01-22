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
    private FeedAdapter adapter;

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
            ClienteHttpVolley.getInstance(this).addToRequestQueue(
                    new XmlRequest<>(
                            URL_FEED,
                            Rss.class,
                            null,
                            new Response.Listener<Rss>() {
                                @Override
                                public void onResponse(Rss response) {
                                    // Caching
                                    FeedDatabase.getInstance(MainActivity.this).
                                            sincronizarEntradas(response.getChannel().getItems());
                                    // Carga inicial de datos...
                                    new LoadData().execute();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }
                    )
            );
        } else {
            Log.i(TAG, "La conexi�n a internet no est� disponible");
            adapter= new FeedAdapter(
                    this,
                    FeedDatabase.getInstance(this).obtenerEntradas(),
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            listView.setAdapter(adapter);
        }




        // Regisgrar escucha de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) adapter.getItem(position);

                // Obtene url de la entrada seleccionada
                String url = c.getString(c.getColumnIndex(ScriptDatabase.ColumnEntradas.URL));

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

    public class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            // Carga inicial de registros
            return FeedDatabase.getInstance(MainActivity.this).obtenerEntradas();

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Crear el adaptador
            adapter = new FeedAdapter(
                    MainActivity.this,
                    cursor,
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            // Relacionar la lista con el adaptador
            listView.setAdapter(adapter);
        }
    }


}
