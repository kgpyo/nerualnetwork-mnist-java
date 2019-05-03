package io.github.kgpyo.NeuralNetwork;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		NeuralNetwork nn = null;
		try {
			nn = new NeuralNetwork("mnist_train.txt","mnist_test.txt", 10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 하이퍼 파라미터(초매개변수), 경험이나 실험을 통해 최적의 개수 결정
		// = 은닉 노드의 개수, 학습률 등
		// 레이어 = 784-100-10, 학습률 = 0.3, 정확도 = 92%
		// 레이어 = 784-100-10, 학습률 = 0.2, 정확도 = 92%
		// 레이어 = 784-100-10, 학습률 = 0.1, 정확도 = 90%
		// 레이어 = 784-200-10, 학습률 = 0.2, 정확도 = 94.78%
		// 레이어 = 784-200-10, 학습률 = 0.1, epoch = 1, 정확도 = 93.17%
		// 레이어 = 784-200-10, 학습률 = 0.1, epoch = 2, 정확도 = 95.2%
		// 레이어 = 784-200-10, 학습률 = 0.1, epoch = 3, 정확도 = 96.4%
		// 레이어 = 784-200-10, 학습률 = 0.01, epoch = 1, 정확도 = 88.33%
		// 레이어 = 784-200-10, 학습률 = 0.01, epoch = 2, 정확도 = 90.51%
		// 레이어 = 784-200-10, 학습률 = 0.01, epoch = 3, 정확도 = 91.39%
		nn.setLaerningRate(0.1);
		nn.setLayers(new int[] {784,200,10}); // 입력층, 은닉층..., 출력층
		nn.train(3); //매개변수 = epoch
		System.out.println("종료");
	}
}
