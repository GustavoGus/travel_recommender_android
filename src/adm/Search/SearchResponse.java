package adm.Search;

import java.util.ArrayList;
import java.util.Collection;

import adm.widget.BonVoyageWidget;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SearchResponse {
	private int status;
	private ArrayList<SearchItem> result;

	/*
	 * Getters and Setters
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<SearchItem> getResult() {
		return result;
	}
	public void setResult(ArrayList<SearchItem> result) {
		this.result = result;
	}

	/*
	 * Constructor
	 */
	public SearchResponse(int status, ArrayList<SearchItem> result) {
		super();
		this.status = status;
		this.result = result;
	}

	/**
	 * 
	 */
	public SearchResponse() {
		this.status = -2;
		this.result = new ArrayList<SearchItem>();
	}
	/*
	 * ToString
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < result.size(); i++) {
			res += result.get(i).toStringWidget() + " \n";
		}

		return res;
	}

	/**
	 * ToString, to filter places selected on preferences
	 * activity.
	 * @param context
	 * @return
	 */
	public String toString(Context context) {
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		
		
		boolean sleep = preferences.getBoolean("widget_accomodation", true);

		boolean restaurant = preferences.getBoolean("widget_restaurants" , true);

		boolean bar = preferences.getBoolean("widget_bar" , true);

		boolean poi = preferences.getBoolean("widget_poi", true);

		boolean infra = preferences.getBoolean("widget_infrastructure", true);
		if(BonVoyageWidget.getWidget() != null){
			BonVoyageWidget.updateLocalize("");
		}
		String res = "";
		for (int i = 0; i < result.size(); i++) {
			
				if(result.get(i).getPlace_type().equals("HOTEL") && sleep){
					res += result.get(i).toStringWidget() + " \n";
								
				}else 	if(result.get(i).getPlace_type().equals("RESTAURANT") && restaurant){
					res += result.get(i).toStringWidget() + " \n";
	
				}else 	if((result.get(i).getPlace_type().equals("PUB") 
						|| result.get(i).getPlace_type().equals("DISCO") ) && bar){
					res += result.get(i).toStringWidget() + " \n";
	
				}else 	if((result.get(i).getPlace_type().equals("NATURAL") 
						||result.get(i).getPlace_type().equals("CULTURAL") 
						||result.get(i).getPlace_type().equals("POI") 
						)&& poi){
					res += result.get(i).toStringWidget() + " \n";
	
				}else	if(result.get(i).getPlace_type().equals("INFRASTRUCTURE") && infra){
					res += result.get(i).toStringWidget() + " \n";
	
				}
			
		}

		return res;
	}
	public boolean addAllDiferents(Collection<? extends SearchItem> collection) {
		

		for (SearchItem searchItem : collection) {
			if (!result.contains(searchItem)) {
				result.add(searchItem);
			}
		}

		return true;
	}

	

	/*
	 * Metodos de la lista Result
	 */
	public boolean add(SearchItem object) {
		return result.add(object);
	}
	public void clear() {
		result.clear();
	}
	public boolean contains(Object object) {
		return result.contains(object);
	}
	public boolean isEmpty() {
		return result.isEmpty();
	}
	public boolean remove(Object object) {
		return result.remove(object);
	}
	public int size() {
		return result.size();
	}
}
