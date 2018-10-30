package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	public static int maxn = 16;
	//所需存储空间太大,只能转换为邻接表的结构
	//int [][]E = new int[maxn][100];
	static int []vis = new int[maxn];
	static int []dfn = new int[maxn];
	static int []low = new int[maxn];
	static int []subnets = new int[maxn];
	static int nodes;
	static int deep;
	static int rson;
	//邻接表
	static HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	
	public void init(int rootIndex) throws Exception{
		//标记初始节点已经搜索过,初始第一个节点先弄好
		vis[rootIndex]=1;low[rootIndex]=dfn[rootIndex]=1;
		deep=1;rson=0;
//		File file = new File("data\\number_number_xiuzheng_buchong.txt");
//		File file = new File("data\\OClinks_w-1893_xiuzheng.txt");
		File file = new File("data\\test.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = "";
		while((str = br.readLine())!=null){
			str = str.trim();
			String []ss = str.split("--&&--");
//			String []ss = str.split(",");
			int num1 = Integer.parseInt(ss[0]);
			int num2 = Integer.parseInt(ss[1]);
			if(map.containsKey(num1)){
				map.get(num1).add(num2);
			}else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(num2);
				map.put(num1, list);
			}
		}
		nodes = map.size();
		return ;
	}

	public static void dfs(int u)
	{
		for(int v=0;v<=nodes;v++)
		{
			if(map.get(u).contains(v))
			{
				if(vis[v]==0)
				{
					//标记节点已经搜索过
					vis[v]=1;
					++deep;
					//第一次搜索到这个节点时,初始化dfn,low
					dfn[v]=low[v]=deep;
					dfs(v);
					//回溯之后维护low,low[u] = min(low[u],low[v])
					low[u]=Math.min(low[u],low[v]);
					if(low[v]>=dfn[u])
					{
						if(u==1) rson++;
						else subnets[u]++;
					}
				}
				else low[u]=Math.min(low[u],dfn[v]);
			}
		}
		return ;
	}

	public static void main(String[] args) throws Exception {
		Main m = new Main();
		int rootIndex = 0;
		m.init(rootIndex);
		//这里采用贪心的思想选择一个标号过的最大值点进行遍历
		dfs(12);
		if(rson>=2) subnets[1]=rson-1;
		for(int i=1;i<=nodes;i++)
		{
			if(subnets[i]!=0)
			{
				System.out.println("SPF node "+i+" leaves "+subnets[i]+1+" subnets ");
			}
		}
	}
}
