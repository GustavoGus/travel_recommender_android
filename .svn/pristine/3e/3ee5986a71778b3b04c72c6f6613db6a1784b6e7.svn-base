/**
 * 
 */
package adm.Geolocation;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author AndroIT
 * 
 */
public class MarkerItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;

	/**
	 * @param marker
	 */
	public MarkerItemizedOverlay(Drawable marker) {
		super(marker);
		boundCenterBottom(marker);

		this.context = null;
		// populate();
	}

	/**
	 * @param marker
	 *            , context
	 */
	public MarkerItemizedOverlay(Drawable marker, Context context) {
		super(marker);

		this.context = context;
		boundCenterBottom(marker);
		// populate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	// Metodo auxiliar para poder hacer uso de "boundCenterBottom" desde otras
	// clases
	public Drawable boundCenterBottomAux(Drawable m) {
		return boundCenterBottom(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		if(context != null)
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("Detalle del lugar");
			dialog.setMessage(item.getTitle() + ", " + item.getSnippet());
			dialog.show();
		}
		return true;
	}
}
