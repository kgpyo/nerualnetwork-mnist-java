package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Layer {
	private List<Neuron> neurons = new ArrayList<Neuron>();
	private int length = 0;
	private boolean _isInputLayer = false;
	
	//layer에 뉴런 생성
	public Layer(int size, boolean isInputLayer) {
		this.length = size;
		this._isInputLayer = isInputLayer;
		for(int i=0;i<size;i++) {
			neurons.add(new Neuron(length,(this._isInputLayer)? true : false));
		}		
	}
	
	public Layer(int size) {
		this(size, false);
	}
	
	public int size() {
		return this.length;
	}
	
	public boolean isInputLayer() {
		return this._isInputLayer;
	}
	
	// 현재 계층에 있는 모든 뉴런의 값들을 초기화한다.
	public void clear() {
		for(int i=0;i<length;i++) {
			neurons.get(i).clear();
		}
	}
	
	// layer간의 연결, 뉴런과 뉴런을 연결한다.
	public void connect(Layer layer) {
		for(int i=0;i<length;i++) {
			Neuron neuron = neurons.get(i);
			for(int j=0;j<layer.length;j++) {
				neuron.connect(layer.neurons.get(j));
			}
		}
	}
	
	/* feedFoward
	 * 입력층의 경우 외부에서 데이터를 입력받는다. 
	 * */
	public void feedForward(List<Double> data) {
		for(int i=0;i<length;i++) {
			neurons.get(i).addInput(data.get(i));
			neurons.get(i).feedForward();
		}
	}
	
	public void feedForward() {
		for(int i=0;i<length;i++) {
			neurons.get(i).feedForward();
		}
	}
	
	// 출력층의 결과값 받기
	public int predict() {
		int _predict = 0;
		double hypothesis = 0.0;
		for(int i=0;i<length;i++) {
			if(Double.compare(hypothesis, neurons.get(i).y()) < 0) {
				hypothesis = neurons.get(i).y();
				_predict = i;
			}
		}
		return _predict;
	}
	
	public List<Double> y() {
		List<Double> _y = new ArrayList<Double>();
		for(int i=0;i<length;i++) {
			_y.add(neurons.get(i).y());
		}
		return _y;
	}
	
	/* BackPropagation
	 * 출력층의 경우 외부에서 오차를 입력받는다.
	 * lnr = learning rate
	 * */
	public void backPropagation(final double lnR, List<Double> target) {
		for(int i=0;i<length;i++) {
			Neuron neuron = neurons.get(i);
			neuron.addSignal(neuron.y() - target.get(i));
		}
		this.backPropagation(lnR);
	}
	
	public void backPropagation(final double lnR) {
		for(int i=0;i<length;i++) {
			neurons.get(i).backpropagation(lnR);
		}
	}
}
