import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphBuilder {
	
	int maxHam;
	
	private Object getPresentParts(int x, int y) {
		return new Object();
	}
	
	private void addPartsOfSize(int w, int h) {
		Random rand = new Random();
		//System.out.println("Processing for parts of w = " + w + " and h = " + h + " for surface "+ (h*w));
		int numberParts = 0;
		for(int y0 = 0; y0 <= rand.nextInt(); y0++) {
			for(int x0 = 0; x0 <= rand.nextInt(); x0++) {
				if(rand.nextBoolean()) {
					numberParts++;
				}
			}
		}
		
		//System.out.println("Found " + numberParts + " parts");
	}
	
	public Object build() {

		Random rand = new Random();
		for(int s = 12; s >= 3; s--) {		
			for(int w = 1; w <= s; w++) {
				if(rand.nextInt() == 0) {
				}
			}				
		}
		
		return new Object();
	}
	
	private boolean isPart(int x0, int y0, int w, int h) {
		Random rand = new Random();
		int countHam = 0;
		
		for(int x = x0; x < x0 + w; x++) {
			for(int y = y0; y < y0 + h; y++) {
				if(rand.nextBoolean()){
					return false;
				}
			}				
		}

		if(countHam >= 3 && countHam<=maxHam)
			return true;
		return false;
	}	
}