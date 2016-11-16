package com.anthonykim.smartfactory.generator;

public class POLISHDriver {
	public static void main(String[] args) {
		Thread tPOLISH = new Thread(new POLISHSimulator(POLISHSimulator.WRITE_IMDG));
		tPOLISH.start();
	}
}
