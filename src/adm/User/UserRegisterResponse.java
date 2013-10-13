/**
 * 
 */
package adm.User;

public class UserRegisterResponse {

	private int status;
	private int result;
	private String message;
	
	/*
	 * Getters and Setters
	 */
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/*
	 * Constructor
	 */
	
	public UserRegisterResponse(int status, int result, String message) {
		super();
		this.status = status;
		this.result = result;
		this.message = message;
	}
	
	/*
	 * ToString
	 */
	
	@Override
	public String toString() {
		return "UserRegisterResponse [status=" + status + ", result=" + result
				+ "]";
	}
	
}
