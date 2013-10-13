/**
 * 
 */
package adm.Repository;

/**
 * @author Julian
 *
 */
public class CoordInfo {
	private String username;
	private	String name;
	private String idPlace;
	public String getIdPlace() {
		return idPlace;
	}
	public void setIdPlace(String idPlace) {
		this.idPlace = idPlace;
	}
	private	float longitude; 
	private	float latitude;
	private	String description;
	
	
	/**
	 * @param username
	 * @param name
	 * @param idPlace
	 * @param longitude
	 * @param latitude
	 * @param description
	 */
	public CoordInfo(String username, String name, String idPlace,
			float longitude, float latitude, String description) {
		super();
		this.username = username;
		this.name = name;
		this.idPlace = idPlace;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
