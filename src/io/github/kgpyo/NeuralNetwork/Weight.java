package io.github.kgpyo.NeuralNetwork;
import java.util.Random;

public class Weight {
	private double _w;
	Neuron nextNeuron;
	Neuron prevNeuron;
	public Weight(Neuron prev, Neuron next, int size) {
		// 초기화 부분 수정 필요할듯
		Random random = new Random(System.nanoTime());
		this._w = random.nextGaussian();
		//this._w = this._w % 1.0;
		//if(this._w <0) this._w *= -1;
		this._w /= Math.sqrt(size);
		this.nextNeuron = next;
		this.prevNeuron = prev;
	}
	public Weight(Neuron prev, Neuron next) {
		this(prev, next, 1);	
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
