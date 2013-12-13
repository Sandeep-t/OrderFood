package com.imaginea.restaurant.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.imaginea.restaurant.process.RequestProcessor;

public class OrderFoodApp {
	
	public static void main(String[] args) throws Exception {
		 
		System.out.println("Enter your order details here : ");
		RequestProcessor requestProcessor =new RequestProcessor();
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String line = bufferRead.readLine();
		    requestProcessor.processOrder(line);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	 
	  }

}
