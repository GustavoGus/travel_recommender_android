package adm.Image;

public class ImageResponse {
	private int status;
	private Pics result;
	
	/*
	 * Getters and Setters
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Pics getResult() {
		return result;
	}
	public void setResult(Pics result) {
		this.result = result;
	}
	
	/*
	 * Constructor
	 */
	public ImageResponse(int status, Pics result) {
		super();
		this.status = status;
		this.result = result;
	}
	
	
	
}
