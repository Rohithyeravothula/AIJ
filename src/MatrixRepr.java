import sun.rmi.server.InactiveGroupException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixRepr {

    public void updateBoard(Integer board[][], Point p, Integer n){
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

    public Integer getStart(Integer [] row, Integer n){
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

    public Integer [][] matrixBFS(Integer [][] board, Integer n, Integer liz) {
        Integer i, stp, d;
        ArrayList<State> que = new ArrayList<State>();
        State initial_state = new State(board, 0, liz);
        que.add(initial_state);
        while (!que.isEmpty()) {
            State cur_state = que.remove(0);
            d = cur_state.depth;
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
        return null;
    }

    public void readInput(){
        Stream<String> rawData = null;
        List<String> data = null;
        Integer d, n,p,i,j;
        String methodName, cur;
        Integer [][] board;
        try{
            ArrayList<String> lines = new ArrayList<>();
            rawData = Files.lines(Paths.get("input.txt"));
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

//            Integer [][] result = matrixDFS(board, n, p);
            Integer [][] result = matrixBFS(board, n, p);

            if(result != null)
            {
                for(i=0;i<n;i++) {
                    for (j = 0; j < n; j++) {
                        if(result[i][j] == -1)
                            result[i][j] = 0;
                    }
                }

                printBoard(result, n);
            }

            else
                System.out.println("no solution");

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] hmm) {
        MatrixRepr m = new MatrixRepr();
        m.readInput();
    }


}
