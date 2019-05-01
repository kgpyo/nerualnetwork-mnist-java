package io.github.kgpyo.NeuralNetwork;
import java.util.*;
public interface IErrorFunction {
	public double mse(double y, double d);
	public double mse(List<Double> y, List<Double> d);
}
