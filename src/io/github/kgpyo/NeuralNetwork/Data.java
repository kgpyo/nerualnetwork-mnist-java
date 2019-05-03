package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Data {
	private List<Double> data = new ArrayList<Double>();
	private List<Double> lableVector = new ArrayList<Double>();
	private int lable;
	public Data(List<Double> data, int lable, int pattern) {
		this.data.addAll(data);
		this.lable = lable;
		for(int i=0;i<pattern;i++) {
			// 시그모이드의 출력은 (0,1) 범위를 가지므로... (반올림 할경우 0, 1이 되지만..)
			if(i==lable) lableVector.add(0.99);
			else lableVector.add(0.01);
		}
	}
	//wrapper class (Double : double 객체)
	public List<Double> getData() {
		return this.data;
	}
	public int getLable() {
		return this.lable;
	}
	
	public List<Double> getLableVector() {
		return this.lableVector;
	}
}
