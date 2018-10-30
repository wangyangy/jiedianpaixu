package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Tarjan {
	private int numOfNode;
	private static HashMap<Integer,ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
//	private Map<Integer,ArrayList<Integer> > graph;//ͼ
	private List< ArrayList<Integer> > result;//���漫��ǿ��ͨͼ
	private boolean[] inStack;//�ڵ��Ƿ���ջ�ڣ���Ϊ��stack��Ѱ��һ���ڵ㲻���㡣���ַ�ʽ���ҿ�
	private Stack<Integer> stack;
	private int[] dfn;
	private int[] low;
	private int[] subnets;
	private int time;//

	public Tarjan(Map<Integer,ArrayList<Integer> > graph,int numOfNode){
		this.graph = (HashMap<Integer, ArrayList<Integer>>) graph;
		this.numOfNode = numOfNode;
		this.inStack = new boolean[numOfNode];
		this.stack = new Stack<Integer>();
		dfn = new int[numOfNode];
		low = new int[numOfNode];
		subnets = new int [numOfNode];
		Arrays.fill(dfn, -1);//��dfn����Ԫ�ض���Ϊ-1������dfn[i]=-1����i����û�����ʹ���
		Arrays.fill(low, -1);
		//��ŵ��Ǽ�����ͨ��ͼ
		result = new ArrayList<ArrayList<Integer>>();
	}

	//����������ص��Ǽ�����ͨͼ�������ͼ
	public List< ArrayList<Integer> > run(){
		//ÿ���ڵ㶼Ҫ����һ�飬��Ϊ���ֻ�Ǳ���һ��Ļ�����������ͼû�б�����
		for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}
		return result;
	}

	//����������ص��Ǽ�����ͨͼ�������ͼ
	public int[] run_gedian(){
		for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}
		return subnets;
	}
	
	public void tarjan(int current){
		dfn[current]=low[current]=time++;
		inStack[current]=true;
		stack.push(current);
		for(int i=0;i<graph.get(current).size();i++){
			int next = graph.get(current).get(i);
			if(dfn[next]==-1){//-1����û�б�����
				tarjan(next);
				low[current]=Math.min(low[current], low[next]);
				if(low[next]>=dfn[current])
				{
					subnets[current]++;
				}
			}else if(inStack[next]){
				low[current]=Math.min(low[current], dfn[next]);
			}
		}

		if(low[current]==dfn[current]){
			ArrayList<Integer> temp =new ArrayList<Integer>();
			int j = -1;
			//�ýڵ��Լ��Ժ���ջ�Ľڵ㣬��ջ
			while(current!=j){
				j = stack.pop();
				inStack[j]=false;
				temp.add(j);
			}
			result.add(temp);			
		}
	}
	
	public static HashMap<Integer,ArrayList<Integer>> init() throws Exception{
		File file = new File("data\\newdata2018-10-22\\gemsec_deezer_dataset\\HR_edges.csv");
//		File file = new File("data\\newdata2018-10-22\\facebook\\facebook_combined.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = "";
		while((str = br.readLine())!=null){
			str = str.trim();
			String []ss = str.split(",");
			int num1 = Integer.parseInt(ss[0]);
			int num2 = Integer.parseInt(ss[1]);
			if(graph.containsKey(num1)){
				ArrayList<Integer> list = graph.get(num1);
				if(!list.contains(num2)) {
					list.add(num2);					
				}
			}else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(num2);
				graph.put(num1, list);
			}
			
			if(graph.containsKey(num2)){
				ArrayList<Integer> list = graph.get(num2);
				if(!list.contains(num1)) {
					list.add(num1);					
				}
			}else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(num1);
				graph.put(num2, list);
			}
		}
//		System.out.println(graph.size());
		return graph;
	}
	
	public static ArrayList<Integer> getBiaohao() throws Exception{
		ArrayList<Integer> list = new ArrayList<Integer>();
		File file = new File("data\\newdata2018-10-22\\\\gemsec_deezer_dataset\\HR_edges_biaohao.txt");
//		File file = new File("data\\newdata2018-10-22\\gemsec_facebook_dataset\\government_edges_biaohao.txt");
//		File file = new File("data\\newdata2018-10-22\\facebook\\facebook_combined_biaohao.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = "";
		while((str = br.readLine())!=null){
			str = str.trim();
			int num = Integer.parseInt(str);
			list.add(num);
		}
		br.close();
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		int numOfNode = 54573;
		HashMap<Integer, ArrayList<Integer>> graph = Tarjan.init();
		Tarjan tarjan = new Tarjan(graph, numOfNode);
		int []subnet = tarjan.run_gedian();
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list_top10 = new ArrayList<Integer>();
		//list_top10.add(rootIndex);
		for(int i=0;i<numOfNode;i++)
		{
			if(subnet[i]!=0)
			{
				//System.out.println("SPF node "+i+" leaves "+subnets[i]+1+" subnets ");
				list.add(i);
			}
		}
		//System.out.println(list.size());  171
		ArrayList<Integer> biaohao = getBiaohao();
		//System.out.println(biaohao.size()); 3656
		for(int i=0;i<biaohao.size();i++){
			int num = biaohao.get(i);
			if(list.contains(num)){
				list_top10.add(num);
				if(list_top10.size()>=10){
					break;
				}
			}		
		}
		for(int i=0;i<list_top10.size();i++){
			System.out.println(list_top10.get(i));
		}

	}

}