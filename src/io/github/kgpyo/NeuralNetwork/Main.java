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
		nn.setLayers(new int[] {784,100,10});
		nn.train(100);
		System.out.println("종료");
	}
}
