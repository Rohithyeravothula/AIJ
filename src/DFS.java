import java.util.ArrayList;
import java.util.Stack;

class Point{
    Integer x, y;
    public Point(Integer p1, Integer p2){
        x=p1;
        y=p2;
    }
}

public class DFS {

    public static void main(String[] args){

        DFS d = new DFS();
//        System.out.println(d.check_liz_ear(new Point(0, 0), new Point(1, 1)));
//        System.out.println(d.check_liz_ear(new Point(0, 0), new Point(0, 1)));
//        System.out.println(d.check_liz_ear(new Point(2, 4), new Point(3, 5)));
//        System.out.println(d.check_liz_ear(new Point(3, 69), new Point(5, 6)));

        Long startTime = System.currentTimeMillis();
        ArrayList<Point> p = d.dfs(18,18);
        Long endTime = System.currentTimeMillis() -  startTime;
        System.out.println("Took " + endTime.toString() + " milliseconds");
        Integer i, l = p.size();
        for(i=0; i<l;i++){
            System.out.println(p.get(i).x.toString() + " " + p.get(i).y.toString());
        }
    }


    public  Boolean check_liz_ear(Point p1, Point p2){
        Integer x1, x2, y1, y2;
        Double slope;
        x1 = p1.x;
        y1 = p1.y;
        x2 = p2.x;
        y2 = p2.y;

        if (p1.x == p2.x && p1.y == p2.y)
        return false;

        if (x1 == x2 || y1 == y2)
        return false;

        slope = ((y2-y1)*1.0)/(x2-x1);
        if (slope == 1.0 || slope == -1)
            return false;
        return true;
    }

    public Boolean issafe(ArrayList<Point> state, Point last){
        Integer i, l = state.size();
        for(i=0; i<l; i++){
            if (!check_liz_ear(state.get(i), last))
                return false;
        }
        return true;
    }

    public class Node{
        ArrayList<Point> state;
        Integer depth, lizard;
        public Node(ArrayList<Point> points, Integer curdepth, Integer curliz){
            state = points;
            depth = curdepth;
            lizard = curliz;
        }
    }

    public ArrayList<Point> clonePoint(ArrayList<Point> points){
        ArrayList<Point> clonedObj = new ArrayList<>();
        Integer i, l = points.size();
        for(i = 0; i<l;i++){
            clonedObj.add(points.get(i));
        }
        return clonedObj;
    }

    public ArrayList<Point> dfs(Integer n , Integer p){
        Stack<Node> stk = new Stack<>();
        Node initial = new Node(new ArrayList<Point>(),0, p);
        stk.push(initial);
        while (!stk.isEmpty()){
            Node current = stk.pop();
            if (current.depth+1 < n){
                stk.push(new Node(clonePoint(current.state), current.depth+1, current.lizard));
            }
            Integer i;
            for(i=0; i<n; i++){

                Point newPoint = new Point(current.depth, i);
                if(issafe(current.state, newPoint)){
                    if(current.lizard == 1){
                        current.state.add(newPoint);
                        return current.state;
                    }
                    else{
                        if(current.depth + 1 < n){
                            ArrayList<Point> newState = current.state;
                            newState.add(newPoint);
                            stk.push(new Node(newState, current.depth+1, current.lizard-1));
                        }
                    }
                }
            }
        }
        return new ArrayList<Point>();
    }

}
