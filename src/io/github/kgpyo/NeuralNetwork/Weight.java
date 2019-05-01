package io.github.kgpyo.NeuralNetwork;
import java.util.Random;

public class Weight {
	private double _w;
	Neuron nextNeuron;
	Neuron prevNeuron;
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
