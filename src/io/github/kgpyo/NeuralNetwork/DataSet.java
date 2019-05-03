package io.github.kgpyo.NeuralNetwork;
import java.util.*;
import java.io.*;

public class DataSet {
	private List<Data> dataSet = new ArrayList<Data>();
	
	//데이터 파일 읽기
	public void readFile(String filename, int pattern) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String input = null;
		
		//기존의 데이터를 모두 제거하고 새로운 데이터로 교체
		dataSet.clear();
		
		// [ ],lable  형식 읽기
		while((input = br.readLine()) != null) {
			List<Double> data = new ArrayList<Double>();
			int lable = 0;
			String[] group = input.split(",");
			if(group.length <= 1) continue;
			
			// [ ~ ] 부분 숫자로변환하여 처리하는 과정
			group[0] = group[0].substring(1,group[0].length()-1);
			// 배열 담기
			StringTokenizer st = new StringTokenizer(group[0]);
			while(st.hasMoreTokens()) {
				// 0 ~ 255 --> 0.01 ~ 1.0 사이의 값으로 변환
				Double element = Double.parseDouble(st.nextToken());
				data.add((element/255.0)*0.99 + 0.01 );
			}
			
			// lable 값 숫자로 변환
			lable = Integer.parseInt(group[1]);
			dataSet.add(new Data(data,lable,pattern));
			
		}
		br.close();
		System.out.printf("%s Data Size: %d\n", filename, dataSet.size());
	}
	
	//데이터 초기화
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
	
	//인덱스 접근
	public Data getData(int i) {
		return this.dataSet.get(i);
	}
	
	//데이터 레이블 값
	public int getDataLable(int i) {
		return this.dataSet.get(i).getLable();
	}
	
	//데이터 Integer의 벡터값
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
