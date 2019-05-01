package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Layer {
	private List<Neuron> neurons = new ArrayList<Neuron>();
	private int length = 0;
	private boolean _isInputLayer = false;
	
	//layer�� ���� ����
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
	
	// ���� ������ �ִ� ��� ������ ������ �ʱ�ȭ�Ѵ�.
	public void clear() {
		for(int i=0;i<length;i++) {
			neurons.get(i).clear();
		}
	}
	
	// layer���� ����, ������ ������ �����Ѵ�.
	public void connect(Layer layer) {
		for(int i=0;i<length;i++) {
			Neuron neuron = neurons.get(i);
			for(int j=0;j<layer.length;j++) {
				neuron.connect(layer.neurons.get(j));
			}
		}
	}
	
	/* feedFoward
	 * �Է����� ��� �ܺο��� �����͸� �Է¹޴´�. 
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
	
	// ������� ����� �ޱ�
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
	 * ������� ��� �ܺο��� ������ �Է¹޴´�.
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
