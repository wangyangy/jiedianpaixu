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
//	private Map<Integer,ArrayList<Integer> > graph;//图
	private List< ArrayList<Integer> > result;//保存极大强连通图
	private boolean[] inStack;//节点是否在栈内，因为在stack中寻找一个节点不方便。这种方式查找快
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
		Arrays.fill(dfn, -1);//将dfn所有元素都置为-1，其中dfn[i]=-1代表i还有没被访问过。
		Arrays.fill(low, -1);
		//存放的是极大连通子图
		result = new ArrayList<ArrayList<Integer>>();
	}

	//这个函数返回的是极大连通图，具体的图
	public List< ArrayList<Integer> > run(){
		//每个节点都要遍历一遍，因为如果只是遍历一遍的话，可能整个图没有遍历完
		for(int i=0;i<numOfNode;i++){
			if(dfn[i]==-1){
				tarjan(i);
			}
		}
		return result;
	}

	//这个函数返回的是极大连通图，具体的图
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
			if(dfn[next]==-1){//-1代表没有被访问
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
			//该节点以及以后入栈的节点，出栈
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