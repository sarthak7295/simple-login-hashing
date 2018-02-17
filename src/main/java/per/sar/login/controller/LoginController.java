package per.sar.login.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import per.sar.login.entity.LoginCredentials;
import per.sar.login.repo.LoginRepo;

@RestController
public class LoginController {

	@Autowired
	LoginRepo loginRepo;

	@PostMapping("/register")
	public ResponseEntity<String> create(@RequestBody Map<String, String> body){
		String username = body.get("username");
		String pass = body.get("pass");
		pass=hashPassword(pass);
		
		try{
			if(!loginRepo.exists(username)){
				loginRepo.save(new LoginCredentials(username, pass));
			}else{
				Exception idException = new Exception("Username already exists");
				throw idException;
			}

		}
		catch (Exception e) {
			System.err.println(e);
			return new ResponseEntity<>("username alreday exists",HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>("Sucessfully registered",HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> body){
		String username = body.get("username");
		String pass = body.get("pass");
		pass=hashPassword(pass);
		if(loginRepo.exists(username)){
			String storedPassword=loginRepo.findOne(username).getPass();
			if(pass.equals(storedPassword)){
				return new ResponseEntity<>("Sucess",HttpStatus.OK);
			}
			else{
				return new ResponseEntity<>("Wrong password",HttpStatus.UNAUTHORIZED);
			}
		}
		else{
			return new ResponseEntity<>("No such username exists",HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/healthcheck")
	public ResponseEntity<String> hc(){
		return new ResponseEntity<>("welldone",HttpStatus.OK );
	}


	private String hashPassword(String pass){
		String passwordToHash = pass;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		System.out.println(generatedPassword);

		return generatedPassword;
	}
}
