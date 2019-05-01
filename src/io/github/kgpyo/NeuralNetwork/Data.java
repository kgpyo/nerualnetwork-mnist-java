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
			if(i==lable) lableVector.add(1.0);
			else lableVector.add(0.0);
		}
	}
	//wrapper class (Integer : int를 참조 데이터로 변환)
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
