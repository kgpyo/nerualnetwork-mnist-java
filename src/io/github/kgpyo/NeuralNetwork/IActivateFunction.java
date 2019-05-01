package io.github.kgpyo.NeuralNetwork;

public interface IActivateFunction {
	public double sigmoid(double z);
	public double sigmoidGrad(double z); 
	public double linearAct(double x);
	public double linearGrad(double x);
}
