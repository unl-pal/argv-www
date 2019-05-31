import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Strings {
public static void main(String[] args){
//	findduplicates("eabcdeabce".toCharArray());
//	int arr[] = {6,3,2,1,4,7,0};
//	quicksort(arr, 0, 6);
//	for(int i=0; i<arr.length; ++i){
//		System.out.print(arr[i]+",");
//	}
//	Integer[] values = { 1, 2, 2, 3, 3, 17 };
//	System.out.println(values);
//	int[] trancatedValues = removeDuplicates(values);
//	System.out.println(trancatedValues);
//	System.out.println(Arrays.toString(trancatedValues));
//	UniqueIterator<Integer> it = new UniqueIterator(values);
//	while(it.hasNext()){
//		System.out.print(it.next()+",");
//	}
//	int arr[] = new int[] {7, 6,5,4,3,2,1};
//	heapSort(arr);
//	for(int i=0; i<arr.length; ++i){
//		System.out.print(arr[i]+",");
//	}
	System.out.println(convertRomanToInt(convertIntToRoman(44)));
}

private static String convertIntToRoman(int n){
	StringBuilder sb = new StringBuilder();
	String[] hundreds = {
		"",
		"C",
		"CC",
		"CCC",
		"CD",
		"D",
		"DC",
		"DCC",
		"DCCC",
		"CM",
	};
	String[] tens = {
	"",
	"X",
	"XX",
	"XXX",
	"XL",
	"L",
	"LX",
	"LXX",
	"LXXX",
	"XC",
	};
	String[] ones = {
			"",
			"I",
			"II",
			"III",
			"IV",
			"V",
			"VI",
			"VII",
			"VIII",
			"IX",
	};
	while(n>=1000){
		sb.append("M");
		n = n-1000;
	}
	if(n >= 100){
		sb.append(hundreds[n/100]);
		n = n % 100;
	}
	if(n >= 10){
		sb.append(tens[n/10]);
		n= n%10;
	}
	if(n>0){
		sb.append(ones[n]);
	}
	return sb.toString();
}

private static int convertRomanToInt(String roman){
	int n = 0;
	char[] romanChars = roman.toCharArray();
	int prev = 1000;
	for(int i=0; i<romanChars.length;++i){
		switch(romanChars[i]){
		case 'M':
			    n+=1000;
			    if(prev < 1000){
			    	n = n-2*prev;
			    }
			    prev = 1000;
			    break;
		case 'D':
			    n+=500;
			    if(prev < 500){
			    	n = n-2*prev;
			    }
			    prev = 500;
			    break;
		case 'C':
		    n+=100;
		    if(prev < 100){
		    	n = n-2*prev;
		    }
		    prev = 100;
		    break;
		case 'L':
		    n+=50;
		    if(prev < 50){
		    	n = n-2*prev;
		    }
		    prev = 50;
		    break;
		case 'X':
		    n+=10;
		    if(prev < 10){
		    	n = n-2*prev;
		    }
		    prev = 10;
		    break;
		case 'V':
		    n+=5;
		    if(prev < 5){
		    	n = n-2*prev;
		    }
		    prev = 5;
		    break;
		case 'I':
		    n+=1;
		    prev = 1;
		    break;
		}
	}
	return n;
}

private static void findduplicates(char[] s){
	int bitmap = 0;
	for(int i=0;i<s.length; ++i){
		int val = s[i]-'a';
		if((bitmap & (1 << val)) > 0){
			System.out.println("duplicate="+s[i]);
		}
		else{
			bitmap = bitmap | (1<<val);
		}
	}
}

static int[] removeDuplicates(int[] values){
	
	if(values == null || values.length <= 1){
		return values;
	}
	
    int write = 1;
    int read = 1;
    
    while(read < values.length){
    	if(values[read] != values[read-1]){
    		//Copy next unique value and write it at write index
    		values[write] = values[read];
    		write++;
    	}
    	read++;
    }
    //Create new copy of array. Java you cannot truncate the size of array.
    return Arrays.copyOf(values, write);
}

private static void heapSort(int arr[]){
	buildHeap(arr);
	int n = arr.length-1;
	for(int i = n; i>0; i--){
		swap(arr, 0, n);
		n = n-1;
		maxHeap(arr, 0, n);
	}
}
private static void buildHeap(int arr[]){
	int n = arr.length-1;
	for(int i = n/2; i>=0; i--){
		maxHeap(arr, i, n);
	}
}

private static void maxHeap(int arr[], int i, int n){
	int left = 2*i;
	int right = 2*i+1;
	int max = i;
	
	if(left<=n && arr[left] > arr[i]){
		max = left;
	}
	if(right <= n && arr[right] > arr[max]){
		max = right;
	}
	if(i != max){
	swap(arr, i, max);
	maxHeap(arr, max, n);
	}
}

private static void swap(int[] arr, int i, int j){
	int temp = arr[i];
	arr[i] = arr[j];
	arr[j] = temp;
}

static int partition(int[] arr, int left, int right){
	int pivot = arr[(left+right/2)];
	while(left < right){
		if(arr[left] < pivot) left++;
		if(arr[right] > pivot) right--;
		if(arr[left] > arr[right]){
			int temp = arr[left];
			arr[left] = arr[right];
			arr[right] = temp;
			left++;
			right--;
		}
	}
	return left;
}

private static void quicksort(int[] arr, int left, int right){
	if(left < right){
		int pivotindex = partition(arr, left, right);
		if(left < pivotindex-1){
			quicksort(arr, left, pivotindex-1);
		}
		if(right > pivotindex+1){
			quicksort(arr, pivotindex+1, right);
		}
		
	}
}
static double square_root(double a, double epsilon){
if (Math.abs(a) < epsilon){
	return 0;
}

double x = 1.5;
while (true){
		if (Math.abs(x * x - a) < epsilon){
			return x;
		}
		double y = a / x;
		x = (x + y)/2;
	}
 } 
}

