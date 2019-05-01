package io.github.kgpyo.NeuralNetwork;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		NeuralNetwork nn = null;
		try {
			nn = new NeuralNetwork("mnistdataset.txt","mnisttest.txt", 10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		nn.setLaerningRate(0.01);
		nn.setLayers(new int[] {784,500,10});
		nn.train();
		System.out.println("Á¾·á");
	}
}
