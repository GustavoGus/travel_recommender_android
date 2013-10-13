/**
 * 
 */
package adm.Submit;

import com.google.gson.annotations.SerializedName;

/**
 * @author AndroIT
 *
 */
public class SubmitResponseResult {
	@SerializedName("id")
	private String result;

	/**
	 * @param result
	 */
	public SubmitResponseResult(String result) {
		super();
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
}
