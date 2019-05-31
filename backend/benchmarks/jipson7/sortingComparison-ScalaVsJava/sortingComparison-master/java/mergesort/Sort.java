package mergesort;

import java.util.*;

public class Sort <T extends Comparable<T>> implements Comparator<T> {

	public ArrayList<T> list;

	Object[] temp;

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

		temp = new Object[length];

		MergeSort(0, length - 1);

	}

	private void MergeSort(int low, int high) {

		if (low < high) {

			int mid = low + ((high - low) / 2);

			MergeSort(low, mid);

			MergeSort(mid + 1, high);

			MergeLists(low, mid, high);

		}

	}

	@SuppressWarnings("unchecked")
	private void MergeLists(int low, int mid, int high) {

		for (int i = low; i <= high; i++) {

			temp[i] = list.get(i);

		}

		int i = low;
		int j = mid + 1;
		int k = low;

		while ((i <= mid) && (j <= high)) {

			if (compare((T) temp[i], (T) temp[j]) > 0) {

				list.set(k, (T) temp[i]);
				i++;

			} else {

				list.set(k, (T) temp[j]);
				j++;

			}

			k++;

		}

		while (i <= mid) {

			list.set(k, (T) temp[i]);
			k++;
			i++;
		}

	}

}
