
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;



public class Solver {
            
        private static final HashSet<Tray> s = new HashSet<>();  
                        
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// args[0] is the filename of the initial puzzle configuration
		// args[1] is the filename of the goal
              
                
//        //Step 1 (For Milestone 1 only, not useful starting from Milestone 2)        
//            FileReader initialConfigFile = new FileReader(args[0]);
//            BufferedReader br0 = new BufferedReader(initialConfigFile);
//            String sCurrentLine0;
//            while ((sCurrentLine0 = br0.readLine()) != null) {
//				System.out.println(sCurrentLine0);
//           }
//            
//            FileReader goalFile = new FileReader(args[1]);
//            BufferedReader br1 = new BufferedReader(goalFile);
//            String sCurrentLine1;
//            while ((sCurrentLine1 = br1.readLine()) != null) {
//				System.out.println(sCurrentLine1);
//            }



            
          // The following switch statement enables (multiple) '-o' command line arguments

            int arg_index = 0;
            
            boolean flag_totaltrayschecked = false;   //implemented  
            boolean flag_trays             = false;   //not yet implemented
            boolean flag_maxmemory         = false;   //not yet implemented
            boolean flag_possiblemoves     = false;   //not yet implemented
            
            while(args[arg_index].charAt(0) == '-' && args[arg_index].charAt(1) == 'o') {
                switch(args[arg_index]) {
                    case "-ototaltrayschecked":
                        flag_totaltrayschecked = true;
                        arg_index++;
                        break;
                    case "-otrays":
                        flag_trays = true;
                        arg_index++;
                        break;
                    case "-omaxmemory":
                        flag_maxmemory = true;
                        arg_index++;
                        break;
                    case "-opossiblemoves":
                        flag_possiblemoves = true;
                        arg_index++;
                        break;
                }
            }
            
            
        //Step 2 (For Milestone 2 and after)
            // read initial configuration
            FileReader TrayFile = new FileReader(args[arg_index]);
            arg_index++;
            BufferedReader br2 = new BufferedReader(TrayFile);
            String[] rcs = br2.readLine().split(" ");
            int rows = Integer.parseInt(rcs[0]);
            int cols = Integer.parseInt(rcs[1]);
            
            List<Block> blocks = new ArrayList<>();
            String sCurrentLine;
            
            while ((sCurrentLine = br2.readLine()) != null) {
                rcs = sCurrentLine.split(" ");
                int[] intArray = new int[4];
                for (int i = 0; i < rcs.length; i++){                   
                    intArray[i] = Integer.parseInt(rcs[i]);
                }
                blocks.add(new Block(intArray[0],intArray[1],intArray[2],intArray[3]));
            }
            // create an initial tray configuration
            Tray tray = new Tray(rows, cols, blocks);
            
            
            // read goal configuration
            FileReader goalFile = new FileReader(args[arg_index]);
            BufferedReader br3 = new BufferedReader(goalFile);
            
            List<Block> goalBlocks = new ArrayList<>();
            String sCurrentLine1;
            while ((sCurrentLine1 = br3.readLine()) != null) {
		rcs = sCurrentLine1.split(" ");
                int[] intArray = new int[4];
                for (int i = 0; i < rcs.length; i++){                   
                    intArray[i] = Integer.parseInt(rcs[i]);
                }
                goalBlocks.add(new Block(intArray[0],intArray[1],intArray[2],intArray[3]));
            }
            
            // create a goal tray configuration
            Tray goalTray = new Tray(rows, cols, goalBlocks);
            
            
            
        //Both DFS and BFS are implemented
        
            //Solver.DFS(tray, goalTray);
            Solver.BFS(tray, goalTray);
            
//          if (flag_trays == true)
//              System.out.println("See the result of multiple -o command line arguments");
            if (flag_totaltrayschecked == true)
                System.out.println("Total number of trays checked: " + Tray.getTrayNum());
            
	}
        
        
        public static void BFS(Tray input_tray, Tray goal_tray){
            Queue<Tray> q = new LinkedList<>();
            q.add(input_tray);
            s.add(input_tray);
            Tray current = input_tray;
            while(!q.isEmpty()){
                current = q.remove();
                if(current.goalCheck(goal_tray)){
                    current.printMoves();
                    //System.out.println("Puzzle solved!");
                    break;
                }
                
                
                
                List<Tray> possible_candidates = current.validMove();    //improved
                int len = possible_candidates.size();
                for (int i = 0; i < len; i++) {
                    Tray n = possible_candidates.get(i);
                    if(!s.contains(n)){
                        s.add(n);
                        n.setParent(current);
                        q.add(n);
                    }
                }
            }
            if(!current.goalCheck(goal_tray)) {
                System.exit(1);
                //System.out.println("No solution!");
            }
            
        }
        
        
        
        public static void DFS(Tray input_tray, Tray goal_tray){
            Stack<Tray> st = new Stack<>();
            st.push(input_tray);
            s.add(input_tray);
            Tray current = input_tray;
            
            while(!st.isEmpty()){
                current = st.pop();
                if(current.goalCheck(goal_tray)){
                    current.printMoves();
                    //System.out.println("Puzzle solved!");
                    break;
                }
                List<Tray> possible_candidates = current.validMove();
                int len = possible_candidates.size();
                for (int i = 0; i < len; i++) {
                    Tray n = possible_candidates.get(i);
                    if(!s.contains(n)){
                        s.add(n);
                        n.setParent(current);
                        st.push(n);
                    }
                }
            }
            if(!current.goalCheck(goal_tray)) {
                System.exit(1);
                //System.out.println("No solution!");
            }                      
        }

        
}
