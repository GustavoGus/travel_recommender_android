/**
 * 
 */
package adm.Search;

import com.google.gson.annotations.SerializedName;

/**
 * @author AndroIT
 *
 */
public class SearchMyLocationItem {
	@SerializedName("id")
	private String id;
	@SerializedName("name")
	private String name;
	
	
	public SearchMyLocationItem()
	{
		id="";
		name="";
	}
	
	public SearchMyLocationItem(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * toString
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchMyLocationItem [id=" + id + ", name=" + name + "]";
	}
}
