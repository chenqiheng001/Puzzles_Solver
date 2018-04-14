public class Block {
    private final int row;
    private final int col;
    private int x_coord;
    private int y_coord;
    
    
    public Block(int row, int col, int x, int y) {      
        this.row = row;
        this.col = col;
        x_coord = x;
        y_coord = y;
    }
    
    // copy constructor
    public Block(Block block) {
        row = block.row;
        col = block.col;
        x_coord = block.x_coord;
        y_coord = block.y_coord;        
    }
    
    public void moveUp(){
        x_coord = x_coord - 1;
    }
    
    public void moveDown(){
        x_coord = x_coord + 1;
    }
    
    public void moveLeft(){
        y_coord = y_coord - 1;
    }
    
    public void moveRight(){
        y_coord = y_coord + 1;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getX(){
        return x_coord;
    }
    
    public int getY(){
        return y_coord;
    }
    
    
    // a class method that compares the contents of two blocks rather than the references
    public static boolean equals(Block b1, Block b2){
        return b1.row == b2.row && b1.col == b2.col && b1.x_coord == b2.x_coord && b1.y_coord == b2.y_coord;
            
    }
    
    
    // a method that compares the content (rather than the reference) of Object o with the current Block object
    @Override
    public boolean equals(Object o){
        if(o instanceof Block)
                return row == ((Block)o).row && col == ((Block)o).col && x_coord == ((Block)o).x_coord && y_coord == ((Block)o).y_coord;
        else
            return this == o;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.col;
        hash = 97 * hash + this.x_coord;
        hash = 97 * hash + this.y_coord;
        return hash;
    }

    
}
