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
	
	/* 학습
	 * 오차를 이용하여 개선 
	 * -> 학습데이터가 잘못되어 있을 수 있고, 학습데이터에만 만춰진 결과를 반영
	 * -> 학습률을 이용하요 조금만 반영
	 * */
	public void train(int epoch) {
		// 순차 입력 했을 때의 인식률 < 랜덤 입력 했을 때의 인식률
		// 현재 학습데이터는 이미 랜덤으로 섞여 있음
		//dataSet.setDataShuffle();
		for(int r=0;r<epoch;r++) {
			for(int j=0;j<dataSet.size();j++) {
				this.clear();
				this.feedFoward(dataSet.getDataVector(j));
				layers.get(output).backPropagation(learningRate, dataSet.getDataLableVector(j));
				for(int k=output-1;k>=1;k--) {
					layers.get(k).backPropagation(learningRate);
				}
				if(j%2000 == 0)System.out.println(j);
			}
			this.evaluation(r+1);
		}
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
	
	// 제곱 오차 이용
	@Override
	public double mse(double y, double d) {
		return Math.pow(d-y, 2);
	}

	@Override
	public double mse(List<Double> y, List<Double> d) {
		double result = 0;
		if(y.size() != d.size()) return result;
		
		for(int i=0;i<y.size();i++) {
			result += Math.pow(d.get(i) - y.get(i), 2);
		}
		
		return result / y.size();
	}
}
