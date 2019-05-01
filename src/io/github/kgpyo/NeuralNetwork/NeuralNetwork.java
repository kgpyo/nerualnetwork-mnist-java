package io.github.kgpyo.NeuralNetwork;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class NeuralNetwork implements IErrorFunction{
	private DataSet dataSet = new DataSet();
	private DataSet testDataSet = new DataSet();
	private int pattern = 0;
	private double learningRate = 0.01;
	private List<Layer> layers = null;
	private int layerSize = 0;
	private int input = 0;
	private int output = 0;
	
	
	public NeuralNetwork(String filename, String testSet,int pattern) throws IOException, FileNotFoundException {
		this.pattern = pattern;
		dataSet.readFile(filename, this.pattern);
		testDataSet.readFile(testSet, this.pattern);
	}
	
	public void setLaerningRate(double rate) {
		this.learningRate = rate;
	}
	
	public void setLayers(int[] layerNumber) {
		layers = new ArrayList<Layer>();
		//계층 생성
		layerSize = layerNumber.length;
		this.input = 0;
		this.output = layerSize - 1;
		for(int i=0;i<layerSize;i++) {
			if(i == input)
				layers.add(new Layer(layerNumber[i], true));
			else
				layers.add(new Layer(layerNumber[i]));
		}
		
		//계층 연결
		for(int i=0;i<layerSize-1;i++) {
			layers.get(i).connect(layers.get(i+1));
		}
	}
	
	// 가중치 제외 모든 뉴런 초기화
	private void clear() {
		for(int i=0;i<layerSize;i++) {
			layers.get(i).clear();
		}
	}
	
	//전파
	private List<Double> feedFoward(List<Double> data) {
		layers.get(0).feedForward(data);
		for(int i=1;i<layerSize-1;i++) {
			layers.get(i).feedForward();
		}
		return layers.get(layerSize-1).y();
	}
	
	//학습
	public void train(int epoch) {
		System.out.println("학습");
		// 입력데이터가 0, 0, 0, 일때보다 0, 5, 1, 0, 순서가 섞일 때가 학습률이 높음
		// 순차 입력(약 63%),랜덤(약 80%)
		dataSet.setDataShuffle();
		this.evaluation(0);
		for(int r=0;r<epoch;r++) {
			for(int j=0;j<dataSet.size();j++) {
				this.clear();
				this.feedFoward(dataSet.getDataVector(j));
				layers.get(output).backPropagation(learningRate, dataSet.getDataLableVector(j));
				for(int k=output-1;k>=1;k--) {
					layers.get(k).backPropagation(learningRate);
				}
			}
			if(true)this.evaluation(r+1);
		}
		this.evaluation(dataSet.size());
	}
	
	private void evaluation(int iter) {
		int correct = 0;
		double error = 0.0;
		int length  = testDataSet.size();
		for(int i=0;i<length;i++) {
			this.clear();
			List<Double> y = this.feedFoward(testDataSet.getDataVector(i));
			error += mse(y, testDataSet.getDataLableVector(i));
			if(layers.get(layerSize-1).predict() == testDataSet.getDataLable(i))
				correct++;
		}
		System.out.printf("iter: %d, error: %f,  recognition rate: %f\n", iter, error/length, (double)correct/length);
	}
	
	@Override
	public double mse(double y, double d) {
		// TODO Auto-generated method stub
		// 제곱 오차.. 이게 맞나..
		return 0.5 * Math.pow(d-y, 2);
	}

	@Override
	public double mse(List<Double> y, List<Double> d) {
		// TODO Auto-generated method stub
		// 이부분 다시 검토
		double result = 0;
		if(y.size() != d.size()) return result;
		
		for(int i=0;i<y.size();i++) {
			result += Math.pow(d.get(i) - y.get(i), 2);
		}
		
		return result / y.size();
	}
}
