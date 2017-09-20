import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    // thersold for max run
    Long therashold = 280 * 1000L;
    Long currentTime, startTime;

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
        startTime = System.currentTimeMillis();
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
                currentTime = System.currentTimeMillis();
                if(currentTime - startTime >= therashold)
                    return null;
            }
        }
        return null;
    }

    public Integer[][] matrixDFS(Integer board[][], Integer n, Integer liz){
        Integer i,stp,d;
        Stack<State> stk = new Stack<>();
        State initial_state = new State(board, 0 ,liz);
        stk.push(initial_state);
        startTime = System.currentTimeMillis();
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
                currentTime = System.currentTimeMillis();
                if(currentTime - startTime >= therashold){
                    return null;
                }
            }
        }
//        System.out.println("no solution found");
        return null;
    }


    // simulated annealing start
    private Double start_temperature = 4000d;
    private Double temperature;
    private Double temperature_diff = 0.5;



    /**
     *
     * @param board
     * @param size
     * @return
     * returns arraylist which contsin row, col of lizard with max
     * and last element in arraylist is the number of lizards conflicting
     */
    private ArrayList<Integer> max_conflicts(Integer board[][], Integer size){
        Integer i, j, k, l, c, row, col, local_conflicts, conflicts=0, total_conflicts=0;
        row = -1;
        col = -1;
        for(i=0;i<size;i++){
            for(j=0;j<size;j++){
                if(board[i][j] == 1){
                    local_conflicts = 0;

                    for(k = i-1; k>=0; k--){
                        if(board[k][j] == 1)
                            local_conflicts += 1;
                        if(board[k][j] == 2)
                            break;
                    }

                    for(k = i+1; k<size; k++){
                        if(board[k][j]==1)
                            local_conflicts += 1;
                        if(board[k][j] == 2)
                            break;
                    }

                    for(k = j-1; k>=0; k--){
                        if(board[i][k] == 1)
                            local_conflicts += 1;
                        if(board[i][k] == 2)
                            break;
                    }

                    for(k = j+1; k<size; k++){
                        if(board[i][k] == 1)
                            local_conflicts += 1;
                        if(board[i][k] == 2)
                            break;
                    }

                    for(k = i-1, l = j-1; k>=0 && l >= 0; k--, l--){
                        if(board[k][l] == 1)
                            local_conflicts +=1;
                        if(board[k][l] == 2)
                            break;
                    }

                    for(k = i+1, l = j-1; k<size && l >=0; k++, l--){
                        if(board[k][l] == 1)
                            local_conflicts+=1;
                        if(board[k][l] == 2)
                            break;
                    }

                    for(k = i-1, l = j+1; k>=0 && l < size; k--, l++){
                        if(board[k][l] == 1)
                            local_conflicts += 1;
                        if(board[k][l] == 2)
                            break;
                    }

                    for(k = i+1, l = j+1; k<size && l<size; k++, l++){
                        if(board[k][l] == 1)
                            local_conflicts +=1;
                        if(board[k][l] == 2)
                            break;
                    }

                    if(local_conflicts != 0)
                        total_conflicts +=1;

                    if(local_conflicts > conflicts){
                        conflicts = local_conflicts;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        if(row != -1 && col != -1)
            return new ArrayList<>(Arrays.asList(row, col, total_conflicts));
        else
            return new ArrayList<>(Arrays.asList(null, null, total_conflicts));
    }

    private Integer heuristic(Integer board[][], Integer size){
        ArrayList<Integer> ans = max_conflicts(board, size);
        return ans.get(2);
    }

    private Double get_temperature(Double old_temperature){
        temperature = old_temperature - temperature_diff;
        Double t = 10 * Math.log(temperature);
        if(t <= 0)
            return 0d;
        return t;
    }

    private Double chance_value(Integer energy_change, Double temp){
        return Math.exp((energy_change)/temp);
    }

    private Boolean probability(Double f){
        Random rand = new Random();
        if(f >= rand.nextDouble())
            return true;
        else
            return false;
    }

    private Integer [][] initial_setup(Integer [][] board, Integer size, Integer lizMax){
        Integer i, j, lizCount=0, random_count = 0;
        Random rand = new Random();
        while (lizCount < lizMax){
            random_count += 1;
            i =  rand.nextInt(size-1);
            j =  rand.nextInt(size-1);
            if(board[i][j] == 0){
                board[i][j] = 1;
                lizCount += 1;
            }
            if(random_count > 100000)
                break;
        }

        if(lizCount != lizMax){
            for(i = 0; i< size; i++)
                for(j = 0; j< size; j++){
                    if(board[i][j] == 0){
                        board[i][j] = 1;
                        lizCount += 1;
                    }
                    if(lizCount == lizMax){
                        i = size;
                        j = size;
                    }
                }
        }

        return board;
    }

    private Integer [][] build_new_state(Integer [][] board, Integer size){
        Integer i, j, row, col, k, l, count=0;
        Random rand = new Random();
        ArrayList<Integer> ans = max_conflicts(board, size);
        i = ans.get(0);
        j = ans.get(1);

        if(i == null && j == null)
            return board;

        count = 0;
        while (true) {
            row = (i + rand.nextInt(3*size))%size;
            col = (j + rand.nextInt(size*3))%size;
            if(row.intValue() != i.intValue() && col.intValue() != j.intValue() && board[row][col] == 0)
                break;
            count += 1;
            // it tried of generating proper case of row and column
            // and couldn't get one for sufficiently long

            if(count > 100000)
            {
                for(k=0; k<size; k++)
                    for(l=0;l<size;l++)
                    {
                        if(board[k][l] == 0){
                            row = k;
                            col = l;
                        }
                    }
                    break;
            }
        }

        Integer [][] new_board = new Integer[size][size];
        for(k=0;k<size;k++){
            for(l=0;l<size;l++){
                new_board[k][l] = board[k][l];
            }
        }
        new_board[i][j] = 0;
        new_board[row][col] = 1;
        return new_board;
    }

    private Integer[][] simulated_annehealing(Integer [][] board, Integer size, Integer lizCount){

        Integer [][] new_state, cur_state, board_state;
        Integer  cur_index, new_energy, cur_energy;
        Double cur_temp_val;

        Long currentTime, startTime= System.currentTimeMillis();

        cur_index = 0;
        while(cur_index <= 100000){
            cur_index += 1;
            board_state = boardClone(board, size);
            cur_state = initial_setup(board_state, size, lizCount);
            cur_energy = heuristic(cur_state, size);
            temperature = start_temperature;

            while (true){
                new_state = build_new_state(cur_state, size);
                new_energy = heuristic(new_state, size);
                cur_temp_val = get_temperature(temperature);

                if(new_energy == 0){
                    cur_state = new_state;
                    cur_energy = new_energy;
                    break;
                }

                if(temperature == 0d){
                    break;
                }

                Integer energy_change = cur_energy - new_energy;
                if (energy_change >= 0){
                    cur_state = new_state;
                    cur_energy = new_energy;
                }
                else{
                    Double chance = chance_value(energy_change, cur_temp_val);
                    if (probability(chance)){
                        cur_state = new_state;
                        cur_energy = new_energy;
                    }
                }

                currentTime = System.currentTimeMillis();
                if(currentTime - startTime > (290 * 1000))
                {
                    //ran out of time
//                    System.out.println("ran out of time");
                    return null;
                }
            }

            if(cur_energy == 0)
                return cur_state;
        }
        // didn't find the solution
        return null;
    }

    public Integer [][] check_small(Integer [][] board, Integer p){
        if(board[0][0] == 0 && p == 1){
            board[0][0] = 1;
            return board;
        }
        else
            return null;
    }


    // simulated annealing end



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
        if(input.size == 1)
            result = algo.check_small(input.board, input.lizCount);
        else if(input.methodName.equals("BFS") && input.size < 9){
            System.out.println("BFS");
            result = algo.matrixBFS(input.board, input.size, input.lizCount);
        }
        else if(input.methodName.equals("SA") || input.size > 32){
            System.out.println("SA he he he");
            result = algo.simulated_annehealing(input.board, input.size, input.lizCount);
        }
        else {
            System.out.println("DFS");
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
