package io.github.kgpyo.NeuralNetwork;
import java.util.*;

public class Neuron implements IActivateFunction{
	List<Weight> inputWeight = new ArrayList<Weight>(); //prev
	List<Weight> outputWeight = new ArrayList<Weight>(); //next
	private double input = 0;
	private double output = 0.0;
	private boolean isInputNeuron = false;
	private List<Double> signal = new ArrayList<Double>();	// 오차 신호
	
	public Neuron() {
		this(false);
	}
	
	public Neuron(boolean isInputNeuron) {
		this.isInputNeuron = isInputNeuron;
	}
	
	/* 활성화 함수
	 * 단극성 시그모이드, 선형함수
	 * 자극이 누적되어 일정 분계점을 넘어서면 활것화
	 * 시그모이드 = 로지스틱함수라고도함
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
	public void connect(Neuron neuron, int input) {
		Weight weight = new Weight(this, neuron, input);
		outputWeight.add(weight);
		neuron.inputWeight.add(weight);
	}
	
	//net 계산
	public void addInput(double value) {
		this.input += value;
	}
	
	// 뉴런 초기화
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
	// 출력값은 (0,1) 범위이기 때문에 레이블 벡터값을 0.01, 0.99 초기화 한다.
	public double y() {
		if(this.isInputNeuron == true)
			return this.linearAct(this.input);
		return this.sigmoid(this.input);
	}
	
	/* 역전파 BackPropagation
	 * lnR = learningRate
	 * 입력층의 오차 -(목표값 - 출력값)은 Layer에서 addSignal로 전달
	 * 
	 * 은직층에서는 목표로 하는 출력값이 없기 때문에 이전 뉴런에서의 오차신호를 이용한다.
	 * 경사하강법 이용, 이동거리를 기울기의 크기에 비례 하도록 조정한다. (가중치 초기값을 서로 다르기 지정함)
	 * 편의를 의해 제곱오차에서의 미분값인 0.5를 제거한다.
	 * dE / dWjk = 가중치 Wjk가 E에 미치는 영향을 계산(변화율)
	 * 
	 * 시그모이드 함수는 값이 매우 작거나 매우 크면 0 또는 1로 수렴하기 때문에 기울기가 0에 가까워진다.
	 * -> 학습이 잘 되지 않음 -> 초기 입력값을 (0.01 ~ 1.0) 사이의 값으로 지정한다.
	 * -> 이전 뉴런의 출력이 0이면 학습이 안될수 있음
	 * 학습률 * 학습 신호 * 이전 뉴런의 출력 = 0
	 * */	
	public void backpropagation(final double lnR) {
		double grad = 0.0;
		grad = this.sigmoidGrad(this.input);
		
		double sumError = this.sumSignal();
		for(int i=0;i<inputWeight.size();i++) {
			Weight edge = inputWeight.get(i);
			edge.prevNeuron.addSignal(grad*sumError*edge.getw());
			edge.update(lnR*grad*sumError*edge.prevNeuron.y());
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
