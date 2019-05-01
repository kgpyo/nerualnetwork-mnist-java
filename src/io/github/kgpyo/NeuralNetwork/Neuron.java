package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Neuron implements IActivateFunction{
	List<Weight> inputWeight = new ArrayList<Weight>(); //prev
	List<Weight> outputWeight = new ArrayList<Weight>(); //next
	private double input = 0;
	private double output = 0.0;
	private boolean isInputNeuron = false;
	private List<Double> signal = new ArrayList<Double>();	// �н���ȣ
	
	public Neuron() {
		this(false);
	}
	
	public Neuron(boolean isInputNeuron) {
		this.isInputNeuron = isInputNeuron;
	}
	
	/* 활성화 함수
	 * 단극성 시그모이드, 선형함수
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
	
	// 뉴런1 - 뉴런2 연결 (역전파를 위해서 양방향)
	// 뉴런1 - weight - 뉴런2
	// weight.prev = 뉴런1, weight.next = 뉴런2, w
	public void connect(Neuron neuron, int here, int output) {
		Weight weight = new Weight(this, neuron, here, output);
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
	 * 입력층의 활성화 함수는 선형함수
	 * 은닉층, 출력층의 활성화 함수는 시그모이드 함수 사용 */
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
	
	//출력값 얻기
	public double y() {
		return this.sigmoid(this.input);
	}
	
	/* 역전파 BackPropagation
	 * lnR = learningRate
	 * 입력층의 오차 (출력값 - 목표값) 은 레이어에서 전달, 여기서는 고려 X
	 * */	
	public void backpropagation(final double lnR) {
		double grad = 0.0;
		grad = this.sigmoidGrad(this.input);
		
		double sumError = this.sumSignal();
		for(int i=0;i<inputWeight.size();i++) {
			Weight weight = inputWeight.get(i);
			//수정해야 하는 부분
			//역전파 부분 다시 공부해서 수정할것...
			weight.prevNeuron.addSignal(grad*sumError*weight.getw());
			weight.update(lnR*grad*sumError*weight.prevNeuron.y());
		}
	}
	
	// 다음층(next Layer)으로부터 전달받은 신호 합계
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
