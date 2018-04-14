
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;



public class Tray {
  //  private final Tray predecessor;
    private final int rows;
    private final int cols;
    private List<Block> blocks;
    private Tray parent; 
    private String move;
    private static int tray_num = 0;

    
    public Tray(int rows, int cols, List<Block> blocks){
        this.rows = rows;
        this.cols = cols;
        this.blocks = blocks; 
        this.parent = null;
    }
    

    //copy constructor
    public Tray(Tray tray){
        this.rows = tray.rows;
        this.cols = tray.cols;
        List<Block> l = new LinkedList<>();
        for (int i = 0; i < tray.blocks.size(); i++) {
            Block b = new Block(tray.blocks.get(i));
            l.add(b);
        }
        this.blocks = l;
        this.parent = null;
    }
    

    public boolean isOK() {                           
            // if a particular block is in a position that exceeds the limit of the tray bound
        int len = blocks.size();
        for (int i = 0; i < len; i++) {
            if (blocks.get(i).getRow() + blocks.get(i).getX() > rows || blocks.get(i).getCol() + blocks.get(i).getY() > cols
                    || blocks.get(i).getX() < 0 || blocks.get(i).getY() < 0)
                return false;
            // if two blocks overlap
            for (int j = i + 1; j < blocks.size(); j++) {
                if(blocks.get(i).getX() + blocks.get(i).getRow() > blocks.get(j).getX()
                        && blocks.get(i).getY() + blocks.get(i).getCol() > blocks.get(j).getY()
                        && blocks.get(j).getX() + blocks.get(j).getRow() > blocks.get(i).getX()
                        && blocks.get(j).getY() + blocks.get(j).getCol() > blocks.get(i).getY())
                    return false;
            }        
        }
       // System.out.println("Intial Configuration is valid. OK to proceed.");
        return true;         
    }
    
    // if the current tray configuration doesn't satisfy the goal, return false. Otherwise true.
    public boolean goalCheck(Tray goalTray) {
        boolean flag = true;
        int i = 0;        
        while (flag == true && i < goalTray.getBlocks().size()) {
            int j = 0;
            while (j < blocks.size() ) {               
                if(Block.equals(goalTray.getBlocks().get(i), blocks.get(j)))
                    break;                
                j++;                
                if (j == blocks.size())
                    return false;
            }
            i++;
            
            if (i == goalTray.getBlocks().size()){               
                return true;
            }                
        }            
        return false;
    }
    
    //return a list of all valid moves starting from the current tray configuration
    public LinkedList<Tray> validMove(){
        
        Tray temp1;
        Tray temp2;
        Tray temp3;
        Tray temp4;
       
        LinkedList<Tray> candidates = new LinkedList<>();
        int ll = this.blocks.size();
        for (int i = 0; i < ll; i++ ) {
                      
            temp1 = new Tray(this);
            temp2 = new Tray(this);
            temp3 = new Tray(this);
            temp4 = new Tray(this);

            List<Block> tempBlock1=temp1.blocks;
            List<Block> tempBlock2=temp2.blocks;
            List<Block> tempBlock3=temp3.blocks;
            List<Block> tempBlock4=temp4.blocks;
           
            tempBlock1.get(i).moveUp();  
            tempBlock2.get(i).moveDown();
            tempBlock3.get(i).moveLeft();
            tempBlock4.get(i).moveRight();
            

            if (temp1.isOK()) {
                 candidates.add(temp1);
                 temp1.parent = this;
                 temp1.move = this.blocks.get(i).getX() + " " + this.blocks.get(i).getY() + " " + temp1.blocks.get(i).getX() + " " + temp1.blocks.get(i).getY();               
             }
             if (temp2.isOK()) {
                 candidates.add(temp2);
                 temp2.parent = this;
                 temp2.move = this.blocks.get(i).getX() + " " + this.blocks.get(i).getY() + " " + temp2.blocks.get(i).getX() + " " + temp2.blocks.get(i).getY();               
             }
             if (temp3.isOK()) {
                 candidates.add(temp3);
                 temp3.parent = this;
                 temp3.move = this.blocks.get(i).getX() + " " + this.blocks.get(i).getY() + " " + temp3.blocks.get(i).getX() + " " + temp3.blocks.get(i).getY();               
             }
             if (temp4.isOK()) {
                 candidates.add(temp4);
                 temp4.parent = this;
                 temp4.move = this.blocks.get(i).getX() + " " + this.blocks.get(i).getY() + " " + temp4.blocks.get(i).getX() + " " + temp4.blocks.get(i).getY();               
             }   
        }
        tray_num += 4;
        return candidates;
            
    }

    //print the moves that lead to the goal configuration
    public void printMoves(){                          //improved
        if (parent == null) {                        
            //System.out.println("Initial configuration is the solution!");
            return;
        }
        else if (parent.getParent() != null) parent.printMoves();
        System.out.println(move);           
        }
            
    
    
    
   

     
 
    public int getRow(){
        return rows;
    }
    
    public int getCol(){
        return cols;
    }
    
    public List<Block> getBlocks(){
        return blocks;
    }
    
    public Tray getParent(){
        return parent;
    }
    
    public void setParent(Tray p){
        this.parent = p;
    }
    
    public void setBlocks(List<Block> blocks){
        this.blocks = blocks;
    }
    
    public int getArea(){
        return rows * cols;
    }
    
    public static int getTrayNum(){
        return tray_num;
    }
    
    
    
    //override the equals method so that it compares the contents of the trays rather than the references
    @Override
    public boolean equals(Object o){      
            boolean flag = true;
            if(o instanceof Tray){
                for(int i = 0; i < blocks.size(); i++){
                    if(!(Block.equals(blocks.get(i), ((Tray)o).blocks.get(i))))
                        flag = false;
                }
                return rows == ((Tray)o).rows && cols == ((Tray)o).cols && flag;
            }
            else
                return this == o;
    }
    
    
    //our own design of the hashcode of a tray, which treats the four int tuple of a tray as a 4-digit number 
    @Override
     public int hashCode() {
        int sum = 0;
        for(int i = 0; i < blocks.size(); i++) {
            sum += blocks.get(i).getRow() * 1000 + blocks.get(i).getCol() * 100 
                    + blocks.get(i).getX() * 10 + blocks.get(i).getY();
        }     
        return sum;
    }


//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 43 * hash + this.rows;
//        hash = 43 * hash + this.cols;
//        hash = 43 * hash + Objects.hashCode(this.blocks);
//        return hash;
//    }
    
    
    
}
