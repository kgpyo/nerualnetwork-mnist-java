package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Neuron implements IActivateFunction{
	List<Weight> inputWeight = new ArrayList<Weight>(); //prev
	List<Weight> outputWeight = new ArrayList<Weight>(); //next
	private double input = 0;
	private double output = 0.0;
	private boolean isInputNeuron = false;
	private List<Double> signal = new ArrayList<Double>();	// �н���ȣ
	private int size = 0;
	
	public Neuron(int size) {
		this(size, false);
	}
	
	public Neuron(int size, boolean isInputNeuron) {
		this.size = size;
		this.isInputNeuron = isInputNeuron;
	}
	
	/* Ȱ��ȭ �Լ�
	 * �ܱؼ� �ñ׸��̵�, �����Լ�
	 * */
	@Override
	public double sigmoid(double x) {
		double b = (1.0 + Math.exp(-x));
		return 1.0/b;
	}
	
	@Override
	public double sigmoidGrad(double x) {
		double f =sigmoid(x); 
		return f*(1-f);
	}
	
	@Override
	public double linearAct(double x) {
		return x;
	}
	
	@Override
	public double linearGrad(double x) {
		return 1.0;
	}
	
	// ����1 - ����2 ���� (�����ĸ� ���ؼ� �����)
	// ����1 - weight - ����2
	// weight.prev = ����1, weight.next = ����2, w
	public void connect(Neuron neuron) {
		Weight weight = new Weight(this, neuron, size);
		outputWeight.add(weight);
		neuron.inputWeight.add(weight);
	}
	
	//net �ڱ� ����
	public void addInput(double value) {
		this.input += value;
	}
	
	//���� �ʱ�ȭ
	public void clear() {
		this.signal.clear();
		this.input = 0.0;
		this.output = 0.0;
	}
	
	/* Forward
	 * �Է����� Ȱ��ȭ �Լ��� �����Լ�
	 * ������, ������� Ȱ��ȭ �Լ��� �ñ׸��̵� �Լ� ��� */
	public void feedForward() {
		if(isInputNeuron == true) {
			this.output = linearAct(this.input);
		}
		else {
			this.output = sigmoid(this.input);
		}
		for(int i=0;i<outputWeight.size();i++) {
			Weight edge = outputWeight.get(i);
			edge.nextNeuron.addInput(output * edge.getw());
		}
	}
	
	//��� �� ���
	public double y() {
		return this.sigmoid(this.input);
	}
	
	/* ������ BackPropagation
	 * lnR = learningRate
	 * �Է����� ���� (��°� - ��ǥ��) �� ���̾�� ����, ���⼭�� ��� X
	 * */	
	public void backpropagation(final double lnR) {
		double grad = 0.0;
		grad = this.sigmoidGrad(this.input);
		
		double sumError = this.sumSignal();
		for(int i=0;i<inputWeight.size();i++) {
			Weight weight = inputWeight.get(i);
			//�����ؾ� �ϴ� �κ�
			//������ �κ� �ٽ� �����ؼ� �����Ұ�...
			weight.prevNeuron.addSignal(grad*sumError*weight.getw());
			weight.update(lnR*grad*sumError*weight.prevNeuron.y());
		}
	}
	
	// ������(next Layer)���κ��� ���޹��� ��ȣ �հ�
	public double sumSignal() {
		double sum = 0;
		for(int i=0;i<signal.size();i++) {
			sum += signal.get(i);
		}
		return sum;
	}

	public void addSignal(double s) {
		this.signal.add(s);
	}
		
}
