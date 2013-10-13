package adm.Image;

/**
 * Clase que encapsula el objeto Imagen.
 *  
 */
public class ImageItem {
	 private String user_name;
	 private String url;
	 private String date;
	 
	/*
	 * Getters and Setters
	 */
	public String getUser_Name() {
		return user_name;
	}
	public void setUser_Name(String user_name) {
		this.user_name = user_name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
		
	/*
	 * Constructores
	 */
	public ImageItem(String user_name, String url, String date) {
		super();
		this.user_name = user_name;
		this.url = url;
		this.date = date;
	}
}

