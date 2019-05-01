package io.github.kgpyo.NeuralNetwork;
import java.util.Random;

public class Weight {
	private double _w;
	Neuron nextNeuron;
	Neuron prevNeuron;
	/* 가중치 값이 0이거나 동일한 경우 학습이 제대롣 되지 않음
	 * 가중치가 크거나 작으면 1 또는0으로 수렴
	 * Xvaier는 시그모이드, He는 ReLU 
	 * */
	public Weight(Neuron prev, Neuron next, int input, int output) {
		// 표준편차 * nextGuasian() + 평균
		// Xavier 초기화
		// TODO 맞는지 나중에 확인
		
		double devation = 0.0;
		if(input != output) {
			devation = Math.sqrt(2.0/(input+output));
		} else {
			devation = 1.0/Math.sqrt(input);
		}
		Random random = new Random(System.nanoTime());
		this._w = devation * random.nextGaussian();
		this.nextNeuron = next;
		this.prevNeuron = prev;
	}
	public Weight(Neuron prev, Neuron next) {
		this(prev, next, 1, 1);	
	}
	
	public double getw() {
		return this._w;
	}
	
	public void setw(double w) {
		this._w = w; 
	}
	
	public void update(double w) {
		this._w -= w;
	}
}
