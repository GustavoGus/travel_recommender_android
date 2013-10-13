/**
 * 
 */
package adm.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AndroIT
 * 
 */
public class SearchMyLocationResponse {
	private int status;
	private List<SearchMyLocationResult> result;

	public SearchMyLocationResponse(int status,
			List<SearchMyLocationResult> result) {
		super();
		this.status = status;
		this.result = result;
	}

	/**
	 * 
	 */
	public SearchMyLocationResponse() {
		status = -2;
		result = new ArrayList<SearchMyLocationResult>();
	}

	/*
	 * Getters and Setters
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<SearchMyLocationResult> getResult() {
		return result;
	}
	public void setResult(List<SearchMyLocationResult> result) {
		this.result = result;
	}

	/*
	 * ToString
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchMyLocationResponse [status=" + status + ", result="
				+ result + "]";
	}
}
