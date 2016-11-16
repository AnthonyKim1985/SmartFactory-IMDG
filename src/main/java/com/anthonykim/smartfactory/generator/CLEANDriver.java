package com.anthonykim.smartfactory.generator;

public class CLEANDriver {
	public static void main(String[] args) {
		Thread  tCLEAN = new Thread(new CLEANGenerator(CLEANGenerator.WRITE_IMDG));
		tCLEAN.start();
	}	
}
