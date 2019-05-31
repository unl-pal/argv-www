package quicksort;

import java.util.*;

public class Sort <T extends Comparable<T>> implements Comparator<T> {

	public ArrayList<T> list;

	public int compare(T a, T b) {

		return a.compareTo(b);

	}

	public Sort(){

		list = new ArrayList<T>();

	}

	@Override
	public String toString(){

		String listOut = "";

		listOut += "-->";

		for (int i = 0; i < list.size(); i++) {

			listOut += list.get(i) + " ";

		}

		return listOut;

	}

	public void MainSort() {

		int length = list.size();

		if (length == 0) {

			return;

		}


		QuickSort(0, length - 1);

	}

	private void QuickSort(int left, int right) {

		int i = left;
		int j = right;

		T pivot = list.get((right + left)/2);

		while (i <= j) {

			while (compare(list.get(i), pivot) > 0) {

				i++;

			}

			while (compare(list.get(j), pivot) < 0) {

				j--;

			}

			if (i <= j) {

				swap(i, j);

				i++;
				j--;

			}

		}

		if (left < j) {

			QuickSort(left, j);

		}

		if (i < right) {

			QuickSort(i, right);

		}
		
	}

	private void swap(int a, int b){

		T holder = list.get(a);
		list.set(a, list.get(b));
		list.set(b, holder);

	}

}
