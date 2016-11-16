package com.anthonykim.smartfactory.generator;

public class HEATDriver {
	public static void main(String[] args) {
		Thread tHEAT = new Thread(new HEATSimulator(HEATSimulator.WRITE_IMDG));
		tHEAT.start();
	}
}