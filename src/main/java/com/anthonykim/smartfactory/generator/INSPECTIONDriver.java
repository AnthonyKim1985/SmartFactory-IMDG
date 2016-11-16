package com.anthonykim.smartfactory.generator;

public class INSPECTIONDriver {
	public static void main(String[] args) {
		Thread  tINSPECTION = new Thread(new INSPECTIONSimulator(INSPECTIONSimulator.WRITE_IMDG));
		tINSPECTION.start();
	}	
}
