import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Simulated {

    Double initial_temperature, temperature_diff;

    public Simulated(Double initial_temp, Double temp_var){
        initial_temperature = initial_temp;
        temperature_diff = temp_var;
    }



    /**
     *
     * @param board
     * @param size
     * @return
     * returns arraylist which contsin row, col of lizard with max
     * and last element in arraylist is the number of lizards conflicting
     */
    private ArrayList<Integer> max_conflicts(Integer board[][], Integer size){
        ArrayList<ArrayList<Integer>> queens = new ArrayList<>();
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

    private Double temperature(Double last_temperature){
//        System.out.println("last temperature " + last_temperature.toString());
        Double cur = last_temperature * temperature_diff;
        if(cur <= 0.000000001)
            return 0d;
        return cur;
    }

//    private Double temperature(Integer iter){
//        Double cur = initial_temperature - temperature_diff*iter;
//        if(cur <= 0)
//            return 0d;
//        return Math.log(cur);
//    }

    private Double chance_value(Integer energy_change, Double temp){
        return Math.exp((energy_change*1000.0)/temp);
    }

    private Boolean probability(Double f){
        Random rand = new Random();
        if(f >= rand.nextDouble())
            return true;
        else
        return false;
    }

    private Integer [][] initial_setup(Integer [][] board, Integer size, Integer lizMax){
        Integer i, j, lizCount=0;
        Random rand = new Random();
        while (lizCount < lizMax){
            i =  rand.nextInt(size-1);
            j =  rand.nextInt(size-1);
            if(board[i][j] == 0){
                board[i][j] = 1;
                lizCount += 1;
            }
        }
        return board;
    }

    private Integer [][] build_new_state(Integer [][] board, Integer size){
        Integer i, j, row, col, k, l, u;
        Random rand = new Random();
//        ArrayList<ArrayList<Integer>> lizards = new ArrayList<>();
//        for(k=0;k<size;k++)
//            for(l=0;l<size;l++)
//                if(board[k][l] == 1)
//                    lizards.add(new ArrayList<>(Arrays.asList(k,l)));


//        u = lizards.size();
//        ArrayList<Integer> ans = lizards.get(rand.nextInt(u-1));
        ArrayList<Integer> ans = max_conflicts(board, size);
        i = ans.get(0);
        j = ans.get(1);

        if(i == null && j == null)
            return board;

        while (true) {
            row = (i + rand.nextInt(3*size))%size;
            col = (j + rand.nextInt(size*3))%size;
            if(row.intValue() != i.intValue() && col.intValue() != j.intValue() && board[row][col] == 0)
                break;
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

        homework how = new homework();

        Integer [][] new_state, cur_state, board_state;
        Integer count, new_energy, cur_energy, iterantions = 10000000;

        board_state = how.boardClone(board, size);

        Double temp_val = initial_temperature;
        count = 0;
        cur_state = initial_setup(board, size, lizCount);
        cur_energy = heuristic(cur_state, size);

        while (true){

//            how.printBoard(cur_state, size);
            new_state = build_new_state(cur_state, size);

            new_energy = heuristic(new_state, size);
            temp_val = temperature(temp_val);

//            System.out.println();
//            how.printBoard(new_state, size);


//            System.out.println("current energy: " + cur_energy.toString());
//            System.out.println("current temperature : " + temp_val.toString());
//            System.out.println("new energy: " + new_energy.toString());

            // check this condition
            if(temp_val == 0d)
                break;


            if(new_energy == 0){
                cur_state = new_state;
                cur_energy = new_energy;
                break;
            }

            Integer energy_change = cur_energy - new_energy;

            if (energy_change >= 0){
                cur_state = new_state;
                cur_energy = new_energy;
            }
            else{
                Double chance = chance_value(energy_change, temp_val);
//                System.out.println("chance value " + chance.toString());
                if (probability(chance)){
//                    System.out.println("unfortunately choosing the opp side");
                    cur_state = new_state;
                    cur_energy = new_energy;
                }
            }
            count += 1;

            if(count % 300000 == 0){
            System.out.println("current energy: " + cur_energy.toString());
            System.out.println("current temperature : " + temp_val.toString());
//            System.out.println("new energy: " + new_energy.toString());
            how.printBoard(cur_state, size);
            }

        }
        if(cur_energy == 0)
            return cur_state;
        else
            return null;
    }

    public static void main(String [] args){
        Double temp_start, temp_change;
        homework how = new homework();
        InputNode input = how.readInput();

        temp_start = 30000d;
//        temp_start = 30d*Math.pow(Math.PI, (input.size - 3));
        temp_change = 0.999999d;

        Simulated sol = new Simulated(temp_start, temp_change);
        Integer [][] state = sol.simulated_annehealing(input.board, input.size, input.lizCount);
        if(state != null)
        how.printBoard(state, input.size);
        else
        System.out.println("no solution found");
    }
}
