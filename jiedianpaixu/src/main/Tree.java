package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Tree {
	public static int maxn;
	//所需存储空间太大,只能转换为邻接表的结构
	//int [][]E = new int[maxn][100];
	static int []vis = null;
	static int []dfn = null;
	static int []low = null;
	static int []subnets = null;
	static int nodes;
	static int deep = 1;
	static int rson = 0;
	//邻接表
	static HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	
	public void init(int rootIndex) throws Exception{
		File file = new File("data\\newdata2018-10-22\\gemsec_deezer_dataset\\HR_edges.csv");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = "";
		while((str = br.readLine())!=null){
			str = str.trim();
			String []ss = str.split(",");
			int num1 = Integer.parseInt(ss[0]);
			int num2 = Integer.parseInt(ss[1]);
			if(map.containsKey(num1)){
				ArrayList<Integer> list = map.get(num1);
				if(!list.contains(num2)) {
					list.add(num2);					
				}
			}else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(num2);
				map.put(num1, list);
			}
			
			if(map.containsKey(num2)){
				ArrayList<Integer> list = map.get(num2);
				if(!list.contains(num1)) {
					list.add(num1);					
				}
			}else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(num1);
				map.put(num2, list);
			}
		}
		nodes = map.size();
		System.out.println(nodes);
		maxn = nodes;
		vis = new int[maxn];
		dfn = new int[maxn];
		low = new int[maxn];
		subnets = new int[maxn];
		//标记初始节点已经搜索过
		vis[rootIndex]=1;
		low[rootIndex]=dfn[rootIndex]=1;
		return ;
	}

	public static void dfs(int u)
	{
		for(int v=0;v<=nodes;v++)
		{
			ArrayList<Integer> sub = map.get(u);
			if(sub.contains(v))
			{
				//如果节点没有搜索过，就搜索
				if(vis[v]==0)
				{
					//标记节点已经搜索过
					vis[v]=1;
//					System.out.println(deep);
					++deep;	
					//第一次搜索到这个节点时,初始化dfn,low
					dfn[v]=low[v]=deep;
					dfs(v);
					//回溯之后维护low,low[u] = min(low[u],low[v])
					low[u]=Math.min(low[u],low[v]);
					if(low[v]>=dfn[u])
					{
						if(u==1) rson++;
						//subnets用来保存子网的个数
						else subnets[u]++;
					}
				}
				else low[u]=Math.min(low[u],dfn[v]);
			}
		}
		return ;
	}

	
	public static ArrayList<Integer> getBiaohao() throws Exception{
		ArrayList<Integer> list = new ArrayList<Integer>();
		File file = new File("data\\newdata2018-10-22\\gemsec_deezer_dataset\\HR_edges_biaohao.txt");
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
		Tree t = new Tree();
		int rootIndex = 43244;
		t.init(rootIndex);
		//这里选择一个标号过的最大值点进行遍历
		dfs(rootIndex);
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list_top10 = new ArrayList<Integer>();
		if(rson>=2) subnets[1]=rson-1;
		for(int i=0;i<nodes;i++)
		{
			if(subnets[i]!=0)
			{
				list.add(i);
			}
		}
		System.out.println(list.size());
		ArrayList<Integer> biaohao = getBiaohao();
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
