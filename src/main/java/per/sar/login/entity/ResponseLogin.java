package per.sar.login.entity;

public class ResponseLogin {

	public ResponseLogin(String error, String success, String userId) {
		super();
		this.error = error;
		this.success = success;
		this.userId = userId;
	}
	private String error;
	private String success;
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public ResponseLogin(String error, String success) {
		super();
		this.error = error;
		this.success = success;
	}
}
