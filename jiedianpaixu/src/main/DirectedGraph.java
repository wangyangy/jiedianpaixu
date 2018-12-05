package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DirectedGraph {

	static HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	public static int maxn;
	static int []vis = null;
	static int []dfn = null; 
	static int []low = null;
	static int []subnets = null;
	static int nodes;
	static int deep = 1;
	static int rson = 0;

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

	//深度非递归查询
	public static void DFS1(HashMap<Integer,ArrayList<Integer>> map, int v){
		Stack<Integer> s = new Stack<>();
		vis[v] = 1;
		s.push(v);
		while(!s.empty()){
			int i=-1, j=-1;
			i = s.peek();
			for(j = 0; j < nodes; j++){
				if(map.get(i).contains(j) && vis[j] == 0){
					vis[j] = 1;
					deep++;
					s.push(j);
					break;
				}
			}
			if(j == nodes) {
				s.pop();			
			}
		}
	}


}

//
//typedef struct
//{
//    int edges[MAX][MAX];
//    int n;
//    int e;
//}MGraph;   //图的定义
//
//bool visited[MAX];
//
//void craeteMGraph(MGraph &G)   //初始化图
//{
//    int i, j;
//    int s, t;
//    int v;
//    for(i = 0; i < G.n; i++)   //将边清空
//    {
//        for(j = 0; j < G.n; j++)
//        {
//            G.edges[i][j] = 0;
//        }
//        visited[i] = false;
//    }
//
//    for(i = 0; i < G.e; i++)  // 输入边和其对应的权值
//    {
//        scanf("%d %d %d", &s, &t, &v);
//        G.edges[s][t] = v;
//    }
//}
//
//void DFS(MGraph G, int v)  //深度递归查询
//{
//    int i;
//    printf("%d ", v);
//    visited[v] = true;
//    for(i = 0; i < G.n; i++)
//    {
//        if(G.edges[v][i] != 0 && visited[i] == false)
//        {
//            DFS(G, i);
//        }
//    }
//}
//
//void DFS1(MGraph G, int v)  //深度非递归查询
//{
//    stack<int> s;
//    printf("%d ", v);
//    visited[v] = true;
//    s.push(v);
//    while(!s.empty())
//    {
//        int i, j;
//        i = s.top();
//        for(j = 0; j < G.n; j++)
//        {
//            if(G.edges[i][j] != 0 && visited[j] == false)
//            {
//                printf("%d ", j);
//                visited[j] = true;
//                s.push(j);
//                break;
//            }
//        }
//        if(j == G.n)
//            s.pop();
//    }
//}
//
//void BFS(MGraph G, int v)  //广度查询
//{
//    queue<int> Q;
//    printf("%d ", v);
//    visited[v] = true;
//    Q.push(v);
//    while(!Q.empty())
//    {
//        int i, j;
//        i = Q.front();
//        Q.pop();
//        for(j = 0; j < G.n; j++)
//        {
//            if(G.edges[i][j] != 0 && visited[j] == false)
//            {
//                printf("%d ", j);
//                visited[j] = true;
//                Q.push(j);
//            }
//        }
//    }
//}
//
//int main()
//{
//    int n, e;
//    while(scanf("%d %d", &n, &e) == 2 && n > 0)
//    {
//        MGraph G;
//        G.n = n;
//        G.e = e;
//        craeteMGraph(G);
//        //DFS(G, 0);
//        //DFS1(G, 0);
//        BFS(G, 0);
//        printf("\n");
//    }
//}

/*  参考输入：
8 9
0 1 1
0 2 1
1 3 1
1 4 1
2 5 1
2 6 1
3 7 1
4 7 1
5 6 1

 */

