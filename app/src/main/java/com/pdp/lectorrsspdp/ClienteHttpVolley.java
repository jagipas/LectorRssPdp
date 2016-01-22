package com.pdp.lectorrsspdp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Creado por Hermosa Programaci�n.
 *
 * Clase que representa un cliente HTTP Volley
 */

public final class ClienteHttpVolley {

    // Atributos
    private static ClienteHttpVolley singleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private static Context context;


     private ClienteHttpVolley(Context context) {
        ClienteHttpVolley.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(40);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Retorna la instancia unica del singleton
     * @param context contexto donde se ejecutar�n las peticiones
     * @return Instancia
     */
    public static synchronized ClienteHttpVolley getInstance(Context context) {
        if (singleton == null) {
            singleton = new ClienteHttpVolley(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Obtiene la instancia de la cola de peticiones
     * @return cola de peticiones
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * A�ade la petici�n a la cola
     * @param req petici�n
     * @param <T> Resultado final de tipo T
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
