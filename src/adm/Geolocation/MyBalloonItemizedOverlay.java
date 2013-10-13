package adm.Geolocation;

import java.util.ArrayList;

import adm.Activities.PlaceActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyBalloonItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	
	public MyBalloonItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenterBottom(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		BVOverlayItem bvoi = (BVOverlayItem) item;
		Intent i = new Intent(c, PlaceActivity.class);
		i.putExtra("idPlace", bvoi.getIdPlace());
		c.startActivity(i);
		return true;
	}
	
	// Metodo auxiliar para poder hacer uso de "boundCenterBottom" desde otras
	// clases
	public Drawable boundCenterBottomAux(Drawable m) {
		return boundCenterBottom(m);
	}

}
