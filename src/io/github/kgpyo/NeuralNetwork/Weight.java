package io.github.kgpyo.NeuralNetwork;
import java.util.Random;

public class Weight {
	private double _w;
	Neuron nextNeuron;
	Neuron prevNeuron;
	/* 가중치 값이 0이거나 동일한 경우 학습이 제대로 되지 않음
	 * 가중치가 크거나 작으면 1 또는0으로 수렴
	 * Xavier는 시그모이드, He는 ReLU 
	 * */
	static Random random = new Random(System.nanoTime());
	public Weight(Neuron prev, Neuron next, int input) {
		// 표준편차 * nextGuasian() + 평균
		/*nextGausian = mean 0.0 and standard deviation 1.0 from this random number generator's sequence. */
		double devation = 1.0 / Math.sqrt(input);
		this._w = (devation * random.nextGaussian());
		this.nextNeuron = next;
		this.prevNeuron = prev;
	}
	
	public double getw() {
		return this._w;
	}
	
	public void setw(double w) {
		this._w = w; 
	}
	
	/* 경사하강법의 예시그림에서 x축 기준으로 보았을 때 양의 기울기를 가지면 왼쪽으로 이동
	 * 음의 기울기를 가지면 오른쪽으로 이동하기 때문에
	 * 부호를 반대로 바꿔준다.
	 * */
	public void update(double w) {
		this._w -= w;
	}
}
