package com.hust.edu.graph;

import java.util.*;

/**
 * locate com.hust.edu.graph
 * Created by mastertj on 2018/11/2.
 */
public class Dijkstra {
    //创建地图
/*
 * S——16——>C—— 2——>D
 * | \     ^ ^     ^
 * 4  8    |  \    |
 * |    \  7   5   6
 * v     v |    \  |
 * A—— 3——>B—— 1——>E
 */

public static class Graph {

    Map<Character,List<Node>> map=new HashMap<Character,List<Node>>();//输出的地图

    public Graph() {
        List<Node> list=new ArrayList<Node>();
        list.add(new Node('S','A',4));
        list.add(new Node('S','B',8));
        list.add(new Node('S','C',16));
        list.add(new Node('A','B',3));
        list.add(new Node('B','C',7));
        list.add(new Node('B','E',1));
        list.add(new Node('C','D',2));
        list.add(new Node('E','C',5));
        list.add(new Node('E','D',6));

        for(int i=0;i<list.size();i++) {
            List<Node> temp = map.get(list.get(i).getSrc());
            if(temp==null)
                temp=new ArrayList<Node>();
            temp.add(list.get(i));
            map.put(list.get(i).getSrc(), temp);
        }
    }
}

public static class Node implements Comparable<Node>{
    private Character src;//起点
    private Character des;//终点
    private int len;      //距离长度
    private int path=Integer.MAX_VALUE;//初始设置为无穷长
    boolean known=false;    //访问过一次后就死亡

    public Node(){}

    public Node(Character src,Character des,int len) {
        this.src=src;
        this.des=des;
        this.len=len;
    }
    void setPath(int path) {
        this.path=path;
    }
    int getPath() {
        return path;
    }
    Character getSrc() {
        return src;
    }
    Character getDes() {
        return des;
    }
    int getLen() {
        return len;
    }

    @Override
    public int compareTo(Node o) {
        return this.len-o.len;
    }
}

    public static Map<Character,Integer> dijkstra(Map<Character,List<Node>> map,Character c) {
        //Queue<Node> heap=new LinkedList<Node>();
        Queue<Node> heap=new PriorityQueue<>();
        //初始节点
        Node root=new Node(c,c,0);
        root.setPath(0);
        heap.add(root);

        Map<Character,Integer> result=new HashMap<Character,Integer>();

        while(!heap.isEmpty()) {
            Node x=heap.poll(),y = null;
            List<Node> temp=map.get(x.getDes());
            x.known=true;

            if(temp==null)
                continue;

            for(int i=0;i<temp.size();i++) {
                y=temp.get(i);
                heap.add(y);

                if(!y.known) {
                    if(y.getPath() > x.getPath()+y.getLen())
                        y.setPath(x.getPath()+y.getLen());
                }

                if(result.get(y.getDes())==null) {
                    result.put(y.getDes(),y.getPath());
                }
                if(result.get(y.getDes()) > y.getPath()) {
                    result.put(y.getDes(),y.getPath());
                }
            }
        }
        return result;
    }

    public static void main(String[] argc)
    {
        Dijkstra.Graph graph=new Dijkstra.Graph();
        Map<Character,Integer> result=dijkstra(graph.map,'S');
        for(Map.Entry<Character,Integer> entry:result.entrySet())
        {
            System.out.println("S-->"+entry.getKey()+" 长度"+entry.getValue());
        }
    }
}
