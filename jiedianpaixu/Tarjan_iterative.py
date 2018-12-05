# encoding: utf-8
N = 7057
next = 0 # Next index.
index = [None] * N
lowlink = [None] * N
onstack = [False] * N
stack = []
nextgroup = 0 # Next SCC ID.
groups = [] # SCCs: list of vertices.
groupid = {} # Map from vertex to SCC ID.
adj = [[] for i in range(N)]
father = [None] * N

def init():
#     fp = open("data\\newdata2018-10-22\\gemsec_deezer_dataset\\HR_edges.csv",'r')
#     fp = open("data\\newdata2018-10-22\\facebook\\facebook_combined.txt",'r')
    fp = open("data\\newdata2018-10-22\\gemsec_facebook_dataset\\government_edges.csv",'r')
    # fp = open("data\\newdata\\2220\\number_number.txt",'r')
    # fp = open("data\\Twitter mentions and retweets_\\number_number.txt",'r')
    # fp = open("data\\newdata2018-10-22\\test.txt",'r')
    
    for line in fp.readlines():
        datas = line.split(",")
        num1 = int(datas[0])
        num2 = int(datas[1])
        #不能重复添加元素，这里考虑的只是网络，并没有考虑权重的问题，所以可以这样写
        if num2 not in adj[num1]:
            adj[num1].append(num2)
        #在这处理一步,按无向图处理,就不用处理源文件了(源文件中1,0 但是没有0,1这一项)
        if num1 not in adj[num2]:
            adj[num2].append(num1)
    


# Tarjan's algorithm.
def sconnect_dfs(v):
    global next, nextgroup
    index[v] = next
    lowlink[v] = next
    next += 1
    stack.append(v)
    onstack[v] = True
    for w in adj[v]:
        if index[w] == None:
            sconnect_dfs(w)
            lowlink[v] = min(lowlink[v], lowlink[w])
        elif onstack[w]:
            lowlink[v] = min(lowlink[v], index[w])
    if index[v] == lowlink[v]:
        com = []
        while True:
            w = stack[-1]
            del stack[-1]
            onstack[w] = False
            com.append(w)
            groupid[w] = nextgroup
            if w == v: break
        groups.append(com)
        nextgroup += 1


# Tarjan's algorithm, iterative version.
def sconnect(v):
    global next, nextgroup
    work = [(v, 0)] # NEW: Recursion stack.
    while work:
        v, i = work[-1] # i is next successor to process.
        del work[-1]
        #如果v是第一次访问
        if i == 0: # When first visiting a vertex:
            index[v] = next
            lowlink[v] = next
            next += 1
            stack.append(v)
            onstack[v] = True
        recurse = False
        for j in range(i, len(adj[v])):
            #搜索下一个邻接点
            w = adj[v][j]
            #邻接点没有访问过
            if index[w] == None:
                # 入栈,标记之后要访问的节点,记录父节点
                father[w]=v
                work.append((v, j+1))
                work.append((w, 0))
                recurse = True
                break
            #邻接点已经访问过
            elif onstack[w]:
                lowlink[v] = min(lowlink[v], index[w])
        #recurse为False表示节点的所有子节点已经访问过
        if recurse: continue
        #如果v是一个根节点,将根以及所有子节点退栈
        if index[v] == lowlink[v]:
            com = []
            while True:
                w = stack[-1]
                del stack[-1]
                onstack[w] = False
                com.append(w)
                groupid[w] = nextgroup
                if w == v: break
            groups.append(com)
            nextgroup += 1
        #在递归的过程时递归一个节点就更新一次,虽然在迭代的时候只更新一次但是因为放入了栈中,
        #栈不空就更新,所以最后会全部更新
        if work: 
            w = v
            v, _ = work[-1]
            lowlink[v] = min(lowlink[v], lowlink[w])
            
#线调用一下初始化的方法
init() 
for v in xrange(N):
#     print v
    if index[v] == None:
        sconnect(v)
    
k=0    
for i in range(len(father)):
    if father[i] == None:
        continue
    
    if lowlink[i]>=index[father[i]]:
        k+=1
        print "节点"+str(father[i])+"是割点"
print k
#     lowlink = map(str,lowlink)
#     k=0;
#     for i in lowlink:
#         k+=1;
#         if k%100==0:
#             print 
    
#         print lowlink
#         print index