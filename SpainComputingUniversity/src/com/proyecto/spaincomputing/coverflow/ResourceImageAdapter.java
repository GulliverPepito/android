package com.proyecto.spaincomputing.coverflow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proyecto.spaincomputing.entity.UniversidadBean;
import com.proyecto.spaincomputing.singleton.MySingleton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

/**
 * This class is an adapter that provides images from a fixed set of resource
 * ids. Bitmaps and ImageViews are kept as weak references so that they can be
 * cleared by garbage collection when not needed.
 * 
 */
public class ResourceImageAdapter extends AbstractCoverFlowImageAdapter {

    /** The Constant TAG. */
    private static final String TAG = ResourceImageAdapter.class.getSimpleName();

    /** The Constant DEFAULT_LIST_SIZE. */
    private static final int DEFAULT_LIST_SIZE = 20;

    /** The Constant IMAGE_RESOURCE_IDS. */
    private static final List<Integer> IMAGE_RESOURCE_IDS = new ArrayList<Integer>(DEFAULT_LIST_SIZE);

    /** The bitmap map. */
    @SuppressLint("UseSparseArrays")
	private final Map<Integer, WeakReference<Bitmap>> bitmapMap = new HashMap<Integer, WeakReference<Bitmap>>();

    private final Context context;

    private ArrayList<UniversidadBean> listado=new ArrayList<UniversidadBean>();
    
    /**
     * Creates the adapter with default set of resource images.
     * 
     * @param context
     *            context
     */
    public ResourceImageAdapter(final Context context) {
        super();
        this.context = context;
        
        inicializarDatos();
        
        setResources();
    }

    /**
     * Replaces resources with those specified.
     * 
     * @param resourceIds
     *            array of ids of resources.
     */
    public final synchronized void setResources() {
        IMAGE_RESOURCE_IDS.clear();
        
        for(UniversidadBean ub:listado){
        	 IMAGE_RESOURCE_IDS.add(ub.getIdImagen());
		}
           
        notifyDataSetChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public synchronized int getCount() {
        return IMAGE_RESOURCE_IDS.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see pl.polidea.coverflow.AbstractCoverFlowImageAdapter#createBitmap(int)
     */
    @Override
    protected Bitmap createBitmap(final int position) {
        Log.v(TAG, "creating item " + position);
        final Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(IMAGE_RESOURCE_IDS.get(position)))
                .getBitmap();
        bitmapMap.put(position, new WeakReference<Bitmap>(bitmap));
        return bitmap;
    }
    
    public  ArrayList<UniversidadBean> inicializarDatos(){
		  
		  listado=MySingleton.getInstance().getUniversitiesList();
		  
		  return listado;
	}
}