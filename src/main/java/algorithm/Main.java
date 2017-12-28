package algorithm;

import java.util.*;
public class Main{
    static int index = 0;
    public static class Node{
        int cityId;

        public Node(int cityId) {
            this.cityId = cityId;
        }
    }
    public static class Line{
        int f;
        int t;

        public Line(int male, int female) {
            this.f = male;
            this.t = female;
        }
    }
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int n,m;
        while(cin.hasNextInt()) {
            n = cin.nextInt();
            m = cin.nextInt();
            int max = m > n ? m : n;
            int[][] map = new int[max+1][max+1];
            List<Line> lines = new ArrayList<>();
            for(int i = 1;i<=n;i++){
                int count = cin.nextInt();
                for(int j = 0;j<count;j++){
                    int feNum = cin.nextInt();
                    map[i][feNum] = 1;
                    map[feNum][i] = 1;
                    lines.add(new Line(i,feNum));
                }
            }
            for(int i = 1;i<=m;i++){
                int count = cin.nextInt();
                for(int j = 0;j<count;j++){
                    int mNum = cin.nextInt();
                    if(map[mNum][i]==0){
                        map[i][mNum] = 1;
                        map[mNum][i] = 1;
                        lines.add(new Line(i,mNum));
                    }
                }
            }
            int res = calculate(map,lines);
            System.out.println(res);
        }
    }
    private static int calculate(int[][] map,List<Line> lines){
        int round = 0;
        while(!lines.isEmpty()){
            Set<Integer> used = new HashSet<>();
            Iterator<Line> iterator = lines.iterator();
            while(iterator.hasNext()){
                Line line = iterator.next();
                round++;
                int f = line.f;
                int t = line.t;
                if(!used.contains(f) && !used.contains(t)){
                    iterator.remove();
                    used.add(f);
                    used.add(t);
                    map[f][t] = map[t][f] = 0;
                }
            }
        }
        return round;
    }

    private static int addMapIfExists(Map<String,Integer> map,String city){
        if(map.containsKey(city)) return map.get(city);
        map.put(city,index);
        return index++;
    }




}