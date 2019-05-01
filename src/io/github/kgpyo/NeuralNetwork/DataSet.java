package io.github.kgpyo.NeuralNetwork;
import java.util.*;
import java.io.*;

public class DataSet {
	private List<Data> dataSet = new ArrayList<Data>();
	
	//������ ���� �б�
	public void readFile(String filename, int pattern) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String input = null;
		
		//������ �����͸� ��� �����ϰ� ���ο� �����ͷ� ��ü
		dataSet.clear();
		
		// [ ],lable ���� �б�
		while((input = br.readLine()) != null) {
			List<Double> data = new ArrayList<Double>();
			int lable = 0;
			String[] group = input.split(",");
			if(group.length <= 1) continue;
			
			// [ ~ ] �κ� ���ڷ� ��ȯ�Ͽ� ó���ϴ� ����
			group[0] = group[0].substring(1,group[0].length()-1);
			//��������� �и�
			StringTokenizer st = new StringTokenizer(group[0]);
			while(st.hasMoreTokens()) {
				data.add(Double.parseDouble(st.nextToken()));
			}
			
			// lable �� ���ڷ� ��ȯ
			lable = Integer.parseInt(group[1]);
			dataSet.add(new Data(data,lable,pattern));
			
		}
		br.close();
	}
	
	//������ �ʱ�ȭ
	public void clearData() {
		if(dataSet != null) {
			dataSet.clear();
		}
	}
	
	public int size() {
		return this.dataSet.size();
	}
	
	public List<Data> getDataSet() {
		return this.dataSet;
	}
	
	//�ε��� ����
	public Data getData(int i) {
		return this.dataSet.get(i);
	}
	
	//������ ���̺� ��
	public int getDataLable(int i) {
		return this.dataSet.get(i).getLable();
	}
	
	//������ Integer�� ���Ͱ�
	public List<Double> getDataVector(int i) {
		return this.dataSet.get(i).getData();
	}
	
	public List<Double> getDataLableVector(int i) {
		return this.dataSet.get(i).getLableVector();
	}
	
	public void setDataShuffle() {
		Collections.shuffle(dataSet, new Random(System.nanoTime()));
	}
}
