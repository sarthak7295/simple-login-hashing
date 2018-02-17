package per.sar.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logindetails")
public class LoginCredentials {

	@Id
	@Column(name="username")
	private String username;
	@Column(name="pass")
	private String pass;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LoginCredentials() {
		super();
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public LoginCredentials(String username, String pass) {
		super();
		this.username = username;
		this.pass = pass;
	}
 
   
}
