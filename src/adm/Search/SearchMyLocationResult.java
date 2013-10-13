/**
 * 
 */
package adm.Search;

import com.google.gson.annotations.SerializedName;

/**
 * @author Usuario
 * 
 */
public class SearchMyLocationResult {

	@SerializedName("place")
	private SearchMyLocationItem place;
	@SerializedName("city")
	private SearchMyLocationItem city;
	@SerializedName("region")
	private SearchMyLocationItem region;
	@SerializedName("country")
	private SearchMyLocationItem country;
	@SerializedName("distance")
	private float distance;

	public SearchMyLocationResult(SearchMyLocationItem place,
			SearchMyLocationItem city, SearchMyLocationItem region,
			SearchMyLocationItem country, float distance) {
		super();
		this.place = place;
		this.city = city;
		this.region = region;
		this.country = country;
		this.distance = distance;
	}
	public SearchMyLocationResult() {
		super();
	}

	public SearchMyLocationItem getPlace() {
		return place;
	}
	public void setPlace(SearchMyLocationItem place) {
		this.place = place;
	}
	public SearchMyLocationItem getCity() {
		return city;
	}
	public void setCity(SearchMyLocationItem city) {
		this.city = city;
	}
	public SearchMyLocationItem getRegion() {
		return region;
	}
	public void setRegion(SearchMyLocationItem region) {
		this.region = region;
	}
	public SearchMyLocationItem getCountry() {
		return country;
	}
	public void setCountry(SearchMyLocationItem country) {
		this.country = country;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	/*
	 * ToString
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchMyLocationResult [place=" + place + ", city=" + city
				+ ", region=" + region + ", country=" + country + ", distance="
				+ distance + "]";
	}

	public String toStringForFeelingLucky() {

		String res = "";

		if (place != null)
			res += place.getName() + ", ";
		if (city != null)
			res += city.getName() + ", ";
		if (region != null)
			res += region.getName() + ", ";
		if (country != null)
			res += country.getName();

		res += " a " + distance + " km";
		return res;
	}

}
