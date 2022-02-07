package ca.ammar.website.application.beans;

import ca.ammar.website.application.controller.MainController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	
	private int	id; 
	private String username; 
	private String password;

	static ArrayList<MainController> something = new ArrayList<>();
		
}

