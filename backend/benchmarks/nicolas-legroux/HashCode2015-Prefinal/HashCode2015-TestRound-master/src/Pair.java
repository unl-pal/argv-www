public class Pair {

    private final int x;
    private final int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair key = (Pair) o;
        if(x == key.x && y == key.y){        	
        	return true;
        }
        else{        	
        	return false;
        }    
    }  
    
    @Override 
    public int hashCode(){
    	return y*60+x;
    }
}