/**
 * 
 */
package adm.Repository;

import java.util.ArrayList;

import adm.Activities.R;
import adm.Search.SearchItem;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author AndroIT
 * 
 */

public class ListViewSuggestionAdapter extends ArrayAdapter<SearchItem> {

	private ArrayList<SearchItem> items;
	private ViewHolder holder;
	private Context context;
	public static boolean tipoBocadillito = false;

	public ListViewSuggestionAdapter(Context context, int textViewResourceId,
			ArrayList<SearchItem> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (tipoBocadillito == false) {
				v = vi.inflate(R.layout.listview_item_left, null);
				tipoBocadillito = true;
			} else {
				v = vi.inflate(R.layout.listview_item_right, null);
				tipoBocadillito = false;
			}

			holder = new ViewHolder();

			holder.setTitulo((TextView) v.findViewById(R.id.titulo));
			holder.setSubtitulo((TextView) v
					.findViewById(R.id.subtitulo));
			holder.setSubsubtitulo((TextView) v
					.findViewById(R.id.subsubtitulo));
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		SearchItem listViewItem = items.get(position);	
		//Titulo
		SpannableString content = new SpannableString(listViewItem.getName());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		holder.getTitulo().setText(content);
		//Subtitulo
		holder.getSubtitulo().setText(GlobalParameters.traduceTagTipoSitio(listViewItem.getPlace_type()));
		//Subsubtitulo
		holder.getSubsubtitulo().setText("");
		//Otros
		holder.setIdPlace(listViewItem.getId());
		holder.setTypePlace(listViewItem.getPlace_type());

		return v;
	}
}