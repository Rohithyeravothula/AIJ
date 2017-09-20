//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//public class Simulated {
//
//    private Double start_temperature = 4000d;
//    private Double temperature;
//    private Double temperature_diff = 0.5;
//    private Long threshold = 290 * 1000L;
//
//
//    /**
//     *
//     * @param board
//     * @param size
//     * @return
//     * returns arraylist which contsin row, col of lizard with max
//     * and last element in arraylist is the number of lizards conflicting
//     */
//    private ArrayList<Integer> max_conflicts(Integer board[][], Integer size){
//        ArrayList<ArrayList<Integer>> queens = new ArrayList<>();
//        Integer i, j, k, l, c, row, col, local_conflicts, conflicts=0, total_conflicts=0;
//        row = -1;
//        col = -1;
//        for(i=0;i<size;i++){
//            for(j=0;j<size;j++){
//                if(board[i][j] == 1){
//                    local_conflicts = 0;
//
//                    for(k = i-1; k>=0; k--){
//                        if(board[k][j] == 1)
//                            local_conflicts += 1;
//                        if(board[k][j] == 2)
//                            break;
//                    }
//
//                    for(k = i+1; k<size; k++){
//                        if(board[k][j]==1)
//                            local_conflicts += 1;
//                        if(board[k][j] == 2)
//                            break;
//                    }
//
//                    for(k = j-1; k>=0; k--){
//                        if(board[i][k] == 1)
//                            local_conflicts += 1;
//                        if(board[i][k] == 2)
//                            break;
//                    }
//
//                    for(k = j+1; k<size; k++){
//                        if(board[i][k] == 1)
//                            local_conflicts += 1;
//                        if(board[i][k] == 2)
//                            break;
//                    }
//
//                    for(k = i-1, l = j-1; k>=0 && l >= 0; k--, l--){
//                        if(board[k][l] == 1)
//                            local_conflicts +=1;
//                        if(board[k][l] == 2)
//                            break;
//                    }
//
//                    for(k = i+1, l = j-1; k<size && l >=0; k++, l--){
//                        if(board[k][l] == 1)
//                            local_conflicts+=1;
//                        if(board[k][l] == 2)
//                            break;
//                    }
//
//                    for(k = i-1, l = j+1; k>=0 && l < size; k--, l++){
//                        if(board[k][l] == 1)
//                            local_conflicts += 1;
//                        if(board[k][l] == 2)
//                            break;
//                    }
//
//                    for(k = i+1, l = j+1; k<size && l<size; k++, l++){
//                        if(board[k][l] == 1)
//                            local_conflicts +=1;
//                        if(board[k][l] == 2)
//                            break;
//                    }
//
//                    if(local_conflicts != 0)
//                        total_conflicts +=1;
//
//                    if(local_conflicts > conflicts){
//                        conflicts = local_conflicts;
//                        row = i;
//                        col = j;
//                    }
//                }
//            }
//        }
//        if(row != -1 && col != -1)
//        return new ArrayList<>(Arrays.asList(row, col, total_conflicts));
//        else
//        return new ArrayList<>(Arrays.asList(null, null, total_conflicts));
//    }
//
//    private Integer heuristic(Integer board[][], Integer size){
//        ArrayList<Integer> ans = max_conflicts(board, size);
//        return ans.get(2);
//    }
//
//    private Double get_temperature(Double old_temperature){
//        temperature = old_temperature - temperature_diff;
//        Double t = 10 * Math.log(temperature);
//        if(t <= 0)
//            return 0d;
//        return t;
//    }
//
//    private Double chance_value(Integer energy_change, Double temp){
//        return Math.exp((energy_change)/temp);
//    }
//
//    private Boolean probability(Double f){
//        Random rand = new Random();
//        if(f >= rand.nextDouble())
//            return true;
//        else
//        return false;
//    }
//
//    private Integer [][] initial_setup(Integer [][] board, Integer size, Integer lizMax){
//        Integer i, j, lizCount=0, random_count = 0;
//        Random rand = new Random();
//        while (lizCount < lizMax){
//            random_count += 1;
//            i =  rand.nextInt(size-1);
//            j =  rand.nextInt(size-1);
//            if(board[i][j] == 0){
//                board[i][j] = 1;
//                lizCount += 1;
//            }
//            if(random_count > 1000000)
//                break;
//        }
//
//        if(lizCount != lizMax){
//            for(i = 0; i< size; i++)
//                for(j = 0; j< size; j++){
//                    if(board[i][j] == 0){
//                     board[i][j] = 1;
//                     lizCount += 1;
//                    }
//                    if(lizCount == lizMax){
//                        i = size;
//                        j = size;
//                    }
//                }
//        }
//
//        return board;
//    }
//
//    private Integer [][] build_new_state(Integer [][] board, Integer size){
//        Integer i, j, row, col, k, l, u;
//        Random rand = new Random();
////        ArrayList<ArrayList<Integer>> lizards = new ArrayList<>();
////        for(k=0;k<size;k++)
////            for(l=0;l<size;l++)
////                if(board[k][l] == 1)
////                    lizards.add(new ArrayList<>(Arrays.asList(k,l)));
//
//
////        u = lizards.size();
////        ArrayList<Integer> ans = lizards.get(rand.nextInt(u-1));
//        ArrayList<Integer> ans = max_conflicts(board, size);
//        i = ans.get(0);
//        j = ans.get(1);
//
//        if(i == null && j == null)
//            return board;
//
//        while (true) {
//            row = (i + rand.nextInt(3*size))%size;
//            col = (j + rand.nextInt(size*3))%size;
//            if(row.intValue() != i.intValue() && col.intValue() != j.intValue() && board[row][col] == 0)
//                break;
//        }
//
//        Integer [][] new_board = new Integer[size][size];
//        for(k=0;k<size;k++){
//            for(l=0;l<size;l++){
//                new_board[k][l] = board[k][l];
//            }
//        }
//        new_board[i][j] = 0;
//        new_board[row][col] = 1;
//        return new_board;
//    }
//
//    private Integer[][] simulated_annehealing(Integer [][] board, Integer size, Integer lizCount){
//
//        homework how = new homework();
//
//        Integer [][] new_state, cur_state, board_state;
//        Integer  cur_index, new_energy, cur_energy;
//        Double cur_temp_val;
//
//        Long currentTime, startTime= System.currentTimeMillis();
//
//        cur_index = 0;
//        while(cur_index <= 100000){
//            cur_index += 1;
//            board_state = how.boardClone(board, size);
//            cur_state = initial_setup(board_state, size, lizCount);
//            cur_energy = heuristic(cur_state, size);
//            temperature = start_temperature;
//
//
//            while (true){
//                new_state = build_new_state(cur_state, size);
//                new_energy = heuristic(new_state, size);
//                cur_temp_val = get_temperature(temperature);
//
//                if(new_energy == 0){
//                    cur_state = new_state;
//                    cur_energy = new_energy;
//                    break;
//                }
//
//                if(temperature == 0d){
//                    break;
//                }
//
//                Integer energy_change = cur_energy - new_energy;
//                if (energy_change >= 0){
//                    cur_state = new_state;
//                    cur_energy = new_energy;
//                }
//                else{
//                    Double chance = chance_value(energy_change, cur_temp_val);
//                    if (probability(chance)){
//                        cur_state = new_state;
//                        cur_energy = new_energy;
//                    }
//                }
//
//                currentTime = System.currentTimeMillis();
//                if(currentTime - startTime > (290 * 1000))
//                {
//                    System.out.println("ran out of time");
//                    return null;
//                }
//            }
//
//            if(cur_energy == 0)
//                return cur_state;
//        }
//        // didn't find the solution
//        return null;
//    }
//
//    public Integer [][] check_small(Integer [][] board, Integer p){
//        if(board[0][0] == 0 && p == 1){
//            board[0][0] = 1;
//            return board;
//        }
//        else
//            return null;
//    }
//
//    public static void main(String [] args){
//
//        homework how = new homework();
//        InputNode input = how.readInput();
//        Integer [][] state;
//
//        Simulated sol = new Simulated();
//
//        if(input.size == 1)
//            state = sol.check_small(input.board, input.lizCount);
//        else
//            state = sol.simulated_annehealing(input.board, input.size, input.lizCount);
//
//        if(state != null){
//            System.out.println("found solution");
//            how.printBoard(state, input.size);
//        }
//        else
//            System.out.println("no solution found");
//    }
//}
