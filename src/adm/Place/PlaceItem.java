package adm.Place;

import com.google.gson.annotations.SerializedName;

import adm.Comment.CommentItem;
import adm.Image.ImageItem;

/**
 * Clase que encapsula el objeto Place Simplificado, para la jerarqu�a de otros Place.
 *  
 */
public class PlaceItem {
	 private Integer id;
	 private String name;
	 private String place_type;
	 private String location_desc;
	 private float lat;
	 private float lon;
	 private String desc;
	 private float rate;
	 @SerializedName("check-in")
	 private int check_in;
	 private ImageItem thumb;
	 private CommentItem comment;
	 private PlaceHierarchy hierarchy;
	/*
	 * Getters and Setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace_Type() {
		return place_type;
	}
	public void setPlace_Type(String place_type) {
		this.place_type = place_type;
	}
	public String getLocation_Desc() {
		return location_desc;
	}
	public void setLocation_Desc(String location_desc) {
		this.location_desc = location_desc;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getCheck_In() {
		return check_in;
	}
	public void setCheck_In(int check_in) {
		this.check_in = check_in;
	}
	public ImageItem getImage() {
		return thumb;
	}
	public void setImage(ImageItem thumb) {
		this.thumb = thumb;
	}
	public CommentItem getComment() {
		return comment;
	}
	public void setComment(CommentItem comment) {
		this.comment = comment;
	}
	public PlaceHierarchy getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(PlaceHierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}
		
	/*
	 * Constructores
	 */
	public PlaceItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}

