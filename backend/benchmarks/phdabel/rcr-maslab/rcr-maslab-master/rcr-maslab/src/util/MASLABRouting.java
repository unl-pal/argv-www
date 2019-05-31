package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class MASLABRouting {

	private Random r = new Random();
	/**
	 * Descobre qual é o grafo a ser utilizado com base no setor atual do agente
	 * 
	 * @return Algoritmo que contem o grafo a ser utilizado na busca
	 */
	private Object getSearch(int Setor) {
		Random rand = new Random();
		if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()) {
			return new Object();
		}
		return new Object();
	}

	public Object getRoadIDs(int Setor) {
		Random rand = new Random();
		if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()) {
			return new Object();
		} else if (Setor == rand.nextInt()){
			return new Object();
		}else{
			return new Object();
		}
	}

	private Object getListInteger(int[] arr){
		Random rand = new Random();
		List<Integer> intList = new ArrayList<Integer>();
		for (int i = 0; i < rand.nextInt(); i++)
		{
		}
		return intList;
	}
}
