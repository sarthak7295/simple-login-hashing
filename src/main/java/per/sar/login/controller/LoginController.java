package per.sar.login.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import per.sar.login.entity.LoginCredentials;
import per.sar.login.entity.ResponseLogin;
import per.sar.login.repo.LoginRepo;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	LoginRepo loginRepo;

	@PostMapping("/register")
	public ResponseEntity<ResponseLogin> create(@RequestBody Map<String, String> body){
		String username = body.get("email");
		String pass = body.get("password");
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
			ResponseLogin responseLogin= new ResponseLogin("some error"+e,"false");
			return new ResponseEntity<ResponseLogin>(responseLogin,HttpStatus.OK);
		}
		ResponseLogin responseLogin= new ResponseLogin("none","true");
		return new ResponseEntity<ResponseLogin>(responseLogin,HttpStatus.OK);
	}

	@RequestMapping(value="/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseLogin> login(@RequestBody Map<String, String> body){
		String username = body.get("email");
		String pass = body.get("password");
		pass=hashPassword(pass);
		if(loginRepo.exists(username)){
			String storedPassword=loginRepo.findOne(username).getPass();
			if(pass.equals(storedPassword)){
				ResponseLogin responseLogin= new ResponseLogin("none","true");
				return new ResponseEntity<ResponseLogin>(responseLogin,HttpStatus.OK);
			}
			else{
				ResponseLogin responseLogin= new ResponseLogin("wrong password","false","csd");
				return new ResponseEntity<ResponseLogin>(responseLogin,HttpStatus.UNAUTHORIZED);
			}
		}
		else{
			ResponseLogin responseLogin= new ResponseLogin("No such username exists","false","well");
			
			return new ResponseEntity<ResponseLogin>(responseLogin,HttpStatus.OK);
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
