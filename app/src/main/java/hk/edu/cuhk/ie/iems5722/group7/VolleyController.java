package hk.edu.cuhk.ie.iems5722.group7;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyController {
    private static final String TAG = "VolleyController";
    
    private static VolleyController instance;
    private RequestQueue requestQueue;
//    private ImageLoader imageLoader;
    private static Context ctx;

    

    private VolleyController(Context context){
        ctx=context;
        requestQueue = getRequestQueue();
//        imageLoader = new ImageLoader(requestQueue,
//                new ImageLoader.ImageCache() {
//                    private final LruCache<String, Bitmap>
//                            cache = new LruCache<String, Bitmap>(20);
//
//                    @Override
//                    public Bitmap getBitmap(String url) {
//                        return cache.get(url);
//                    }
//
//                    @Override
//                    public void putBitmap(String url, Bitmap bitmap) {
//                        cache.put(url, bitmap);
//                    }
//                });
    }
    public static synchronized VolleyController getInstance(Context context){
        if (instance==null){
            instance=new VolleyController(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue==null){
            // Instantiate the RequestQueue.
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());

        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

//    public ImageLoader getImageLoader() {
//        return imageLoader;
//    }

}
