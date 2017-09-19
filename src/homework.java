import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Point{
    Integer x, y;
    public Point(Integer p1, Integer p2){
        x=p1;
        y=p2;
    }
}

class InputNode{
    String methodName;
    Integer size;
    Integer lizCount;
    Integer[][] board;
    public InputNode(String mName, Integer n, Integer liz, Integer[][] b){
        methodName = mName;
        size = n;
        lizCount = liz;
        board = b;
    }
}


class homework {

    private void updateBoard(Integer board[][], Point p, Integer n){
        Integer i, j;

        board[p.x][p.y] = 1;

        // x axis
        i = p.x-1;
        while(i>=0 && board[i][p.y] != 2){
            board[i][p.y] = -1;
            i = i-1;
        }

        i = p.x+1;
        while(i<n && board[i][p.y] != 2){
            board[i][p.y] = -1;
            i = i+1;
        }

        // y axis
        i = p.y-1;
        while(i>=0 && board[p.x][i] != 2){
            board[p.x][i] = -1;
            i = i-1;
        }

        i = p.y+1;
        while(i<n && board[p.x][i] != 2){
            board[p.x][i] = -1;
            i = i+1;
        }

        //left diagnol
        i = p.x-1;
        j = p.y-1;
        while(i>=0 && j >=0 && board[i][j] != 2){
            board[i][j] = -1;
            i = i-1;
            j = j-1;
        }

        i = p.x+1;
        j = p.y+1;
        while(i<n && j<n && board[i][j] != 2){
            board[i][j] = -1;
            i = i+1;
            j = j+1;
        }

        // right diagnol
        i = p.x+1;
        j = p.y-1;
        while(i<n && j>=0 && board[i][j] != 2){
            board[i][j] = -1;
            i = i+1;
            j = j-1;
        }

        i = p.x-1;
        j = p.y+1;
        while(i>=0 && j<n && board[i][j] != 2){
            board[i][j] = -1;
            i = i-1;
            j = j+1;
        }
    }


    public void printBoard(Integer [][] board, Integer n){
        Integer i,j;
        for(i=0;i<n;i++){
            for(j=0;j<n;j++){
                System.out.print(board[i][j].toString() + "  ");
            }
            System.out.println();
        }
    }

    //has side effect
    public void zeroBoard(Integer [][] board, Integer n){
        if (board != null){
            Integer i, j;
            for(i=0; i<n; i++)
                for(j=0;j<n;j++)
                    if(board[i][j] == -1)
                        board[i][j] = 0;
        }
    }

    public Integer [][] boardClone(Integer [][] board, Integer n){
        Integer i,j;
        Integer [][] new_board = new Integer[n][n];
        for(i=0;i<n;i++){
            for(j=0;j<n;j++){
                new_board[i][j] = board[i][j];
            }
        }
        return new_board;
    }

    private Integer getStart(Integer [] row, Integer n){
        Integer i, stp=-1;
        for(i = n-1; i>=0; i--){
            if(row[i] == 2){
                stp = i+1;
            }
            else if (row[i] == 1){
                if(stp == -1)
                    return -1;
                else
                    return stp;
            }
        }
        return 0;
    }

    public class State{
        Integer depth, lizcount;
        Integer [][] board;
        public State(Integer [][] cur_board, Integer n, Integer p){
            board = cur_board;
            depth = n;
            lizcount = p;
        }
    }

    private Integer [][] matrixBFS(Integer [][] board, Integer n, Integer liz) {
        Integer i, stp, d;
        ArrayList<State> que = new ArrayList<>();
        State initial_state = new State(board, 0, liz);
        que.add(initial_state);
        while (!que.isEmpty()) {
            State cur_state = que.remove(0);
            d = cur_state.depth;
            if(cur_state.depth + 1 < n){
                Integer [][] skip_board = boardClone(cur_state.board, n);
                State skip_state = new State(skip_board, cur_state.depth+1, cur_state.lizcount);
                que.add(skip_state);
            }
            while (true) {
                stp = getStart(cur_state.board[d], n);
                if (stp == -1) {
                    d = d + 1;
                    if (d >= n)
                        break;
                } else {
                    for (i = stp; i < n; i++) {
                        if (cur_state.board[d][i] == 0) {
                            if (cur_state.lizcount == 1) {
                                cur_state.board[d][i] = 1;
                                return cur_state.board;
                            } else {
                                Integer[][] new_board = boardClone(cur_state.board, n);
                                Point newQ = new Point(d, i);
                                updateBoard(new_board, newQ, n); // check if the state is changing
                                State s = new State(new_board, d, cur_state.lizcount - 1);
                                que.add(s);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return null;
    }


    public Integer[][] matrixDFS(Integer board[][], Integer n, Integer liz){
        Integer i,stp,d;
        Stack<State> stk = new Stack<>();
        State initial_state = new State(board, 0 ,liz);
        stk.push(initial_state);
        while(!stk.isEmpty()){
            State cur_state = stk.pop();
            d=cur_state.depth;

            if(cur_state.depth + 1 < n){
                Integer [][] skip_board = boardClone(cur_state.board, n);
                State skip_state = new State(skip_board, d+1, cur_state.lizcount);
                stk.push(skip_state);
            }
            while(true){
                stp = getStart(cur_state.board[d], n);
                // row is filled with queens
                if(stp == -1){
                    d = d+1;
                    if(d>=n)
                        break;
                }

                // possible to place a queen on current row
                else{

                    for(i=stp;i<n;i++){
                        if(cur_state.board[d][i] == 0){
                            if(cur_state.lizcount == 1){
                                cur_state.board[d][i] = 1;
                                return cur_state.board;
                            }
                            else{
                                Integer [][] new_board = boardClone(cur_state.board, n);
                                Point newQ = new Point(d, i);
                                updateBoard(new_board, newQ, n); // check if the state is changing
                                State s = new State(new_board, d, cur_state.lizcount-1);
                                stk.push(s);
                            }
                        }
                    }
                    break;
                }
            }
        }
//        System.out.println("no solution found");
        return null;
    }

    public void write_to_file(Integer [][] board) throws IOException {
        String curDir = System.getProperty("user.dir");
        FileWriter writer = new FileWriter( curDir + "/output.txt");
        if(board == null){
            writer.write("FAIL");
        }
        else{
            Integer i, j, l = board.length;
            String s,d;
            s="";
            for(i=0; i<l; i++){
                d = "";
                for(j=0; j<l;j++)
                    d+=board[i][j].toString();
                d += "\n";
                s += d;
            }
//            System.out.println(s);
            l = s.length();
            writer.write("OK\n");
            writer.write(s.substring(0, l-1));
        }
        writer.close();
    }

    public InputNode readInput(){
        Stream<String> rawData;
        List<String> data;
        Integer d, n,p,i,j;
        String methodName, cur;
        Integer [][] board;
        try{
            String cur_dir = System.getProperty("user.dir");
            rawData = Files.lines(Paths.get(cur_dir + "/input.txt"));
            data = rawData.collect(Collectors.toList());
            methodName = data.remove(0);
            n = Integer.parseInt(data.remove(0));
            p = Integer.parseInt(data.remove(0));
            board = new Integer[n][n];
            for(i=0;i<n;i++){
                cur = data.remove(0);
                for(j=0;j<n;j++){
                    board[i][j] = Integer.parseInt(String.valueOf(cur.charAt(j)));
                }
            }

            return new InputNode(methodName, n, p, board);
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] hmm) {
        homework algo = new homework();
        InputNode input = algo.readInput();
        Integer [][] result;
        if(input.methodName == "BFS"){
            result = algo.matrixBFS(input.board, input.size, input.lizCount);
        }
        else{
            result = algo.matrixDFS(input.board, input.size, input.lizCount);
        }
        algo.zeroBoard(result, input.size);
        try {
            algo.write_to_file(result);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
