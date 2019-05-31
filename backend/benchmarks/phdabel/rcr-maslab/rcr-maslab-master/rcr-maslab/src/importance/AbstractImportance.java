package importance;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class that stores importance calculation stuff
 * @author anderson
 *
 */
public abstract class AbstractImportance {
	
	/**
	 * Maps a sector (Integer) - see util.Setores - to its
	 * importance (Integer)
	 */
	protected Map<Integer, Integer> sectorImportances;
	
	/**
	 * Returns the importace of a sector
	 * @param sector number of the sector (see util.Setores)
	 * @return int
	 */
	public int getSectorImportance(int sector){
		Random rand = new Random();
		return rand.nextInt();
	}
	
	/**
	 * Calculates the importances of all sectors
	 * @return void
	 */
	public abstract void calculateImportances(boolean isPreProcessing);
}
