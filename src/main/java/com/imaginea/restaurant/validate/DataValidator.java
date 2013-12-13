package com.imaginea.restaurant.validate;

import java.io.File;

public class DataValidator {
	
	
	public boolean validateInput(String[] input) throws Exception{
		if(!(input.length<2)){
			String trim = input[0].trim();
			if(new File(trim).getAbsoluteFile().exists()){
				return true;	
			}
			else{
				throw new Exception("Restaurant details File does not exists !!!!! ,Please check the file path");
			}
		}
		else{
			throw new Exception("Improper Data:Please enter valid data ");
		}
		
	}

}
