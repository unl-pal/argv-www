import java.util.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello world");
		// int[] result = spiralCopy(new int[][] { { 1 } });
		// System.out.println("Result = " + Arrays.toString(result));
		// permute("ABC".toCharArray(), 0, 2);

		// System.out.println(findNumInRotatedArray(new int[] {3,1}, 1));
		// printLongestConseqSubsequence(new int[]{36, 41, 56, 35, 44, 33, 34,
		// 92, 43, 32, 42} );
		// System.out.println(numSquares(12));
		int[][] matrix = { { 0, 0, 1, 1, 0}, 
				          { 1, 0, 1, 1, 0},
				          { 0, 1, 0, 0, 0 }, 
				          { 0, 0, 0, 0, 1 } };
		System.out.println("max=" + findMaxArea(matrix));
		for(int i=0; i<matrix.length; ++i) {
			Arrays.toString(matrix[i]);
		}
	}

	public static int findMaxArea(int[][] matrix) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j) {
				System.out.println("i="+i +", j="+j +", max="+max);
				if (matrix[i][j] == 1) {
					if (i > 0 && j > 0) {
						int num = Math.max(matrix[i - 1][j], matrix[i - 1][j - 1]);
						if(j+1 < matrix[0].length) {
							num = Math.max(num, matrix[i-1][j+1]);
						}
						matrix[i][j] = Math.max(num, matrix[i][j - 1]) + 1;
					} else if (i > 0) {
						matrix[i][j] = matrix[i - 1][j] + 1;
					} else {
						matrix[i][j] = matrix[i][j - 1] + 1;
					}
					max = Math.max(max, matrix[i][j]);
				}
			}
		}
		return max;
	}

	public static int numSquares(int n) {
		int count = 0;
		while (n > 0) {
			int start = (int) Math.floor(Math.sqrt(n));
			n = n - (start * start);
			count++;
		}
		return count;
	}

	// Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some
	// elements appear twice and others appear once.
	// Find all the elements of [1, n] inclusive that do not appear in this
	// array.
	// Could you do it without extra space and in O(n) runtime? You may assume
	// the returned list does not count as extra space.
	public List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ret = new ArrayList<Integer>();

		for (int i = 0; i < nums.length; i++) {
			int val = Math.abs(nums[i]) - 1;
			if (nums[val] > 0) {
				nums[val] = -nums[val];
			}
		}

		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 0) {
				ret.add(i + 1);
			}
		}
		return ret;
	}

	public int maxProduct(int[] nums) {
		int max = 1;
		for (int i = 0; i < nums.length; ++i) {

		}
		return -1;
	}

	/**
	 * Given a set of candidate numbers (C) (without duplicates) and a target
	 * number (T), find all unique combinations in C where the candidate numbers
	 * sums to T.
	 * 
	 * @param candidates
	 * @param target
	 * @return
	 */

	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		// for(int i=0; i<candidates.length; ++i) {
		// int diff= target - candidates[i];
		// if(diff == 0) {
		//
		// }
		// }
		return null;
	}

	public static void printLongestConseqSubsequence(int[] arr) {
		int longest = 1;
		int longestStart = 0;
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < arr.length; ++i) {
			set.add(arr[i]);
		}
		for (int i = 0; i < arr.length; ++i) {
			// if the element is starting in squence?
			if (!set.contains(arr[i] - 1)) {
				int count = 0;
				int start = arr[i];
				while (set.contains(start)) {
					count++;
					start++;
				}
				if (count > longest) {
					longest = count;
					longestStart = arr[i];
				}
			}
		}

		while (longest > 0) {
			System.out.print((longestStart++) + ",");
			longest--;
		}
	}

	public static void permute(char[] str, int l, int r) {
		if (l == r) {
			System.out.println(str);
		} else {
			for (int i = l; i <= r; ++i) {
				swap(str, l, i);
				permute(str, l + 1, r);
				swap(str, l, i);
			}
		}
	}

	public static void swap(char[] str, int i, int j) {
		char c = str[i];
		str[i] = str[j];
		str[j] = c;
	}

	public static int findMinInRotatedArray(int[] nums) {
		int low = 0, high = nums.length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (nums[mid] > nums[high])
				low = mid + 1;
			else
				high = mid;
		}
		return nums[low];
	}

	public static int findNumInRotatedArray(int[] arr, int n) {
		int lo = 0;
		int hi = arr.length - 1;
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			if (arr[mid] == n) {
				return mid;
			}
			if (arr[lo] <= arr[mid]) {
				if (n >= arr[lo] && n <= arr[mid]) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			} else {
				if (n >= arr[mid] && n <= arr[hi]) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
		}

		return -1;
	}

	public static int hammingDistance(int x, int y) {
		int xor = x ^ y;
		int count = 0;
		while (xor > 0) {
			if ((xor & 1) == 1) {
				count++;
			}
			xor = xor >> 1;
		}
		return count;
	}

	/*
	 * Input: [7, 1, 5, 3, 6, 4] Output: 5 max. difference = 6-1 = 5 (not 7-1 =
	 * 6, as selling price needs to be larger than buying price)
	 */
	public int maxProfit(int[] prices) {
		if (prices.length < 2)
			return 0;
		int localmin = prices[0];
		int maxprofit = 0;
		for (int i = 1; i < prices.length; ++i) {
			if (prices[i] < localmin) {
				localmin = prices[i];
			} else {
				maxprofit = Math.max(maxprofit, prices[i] - localmin);
			}
		}
		return maxprofit;
	}

	// Given an array of size n, find the majority element. The majority element
	// is the element that appears more than ⌊ n/2 ⌋ times.
	public int majorityElement(int[] nums) {
		int major = nums[0], count = 1;
		for (int i = 1; i < nums.length; i++) {
			if (count == 0) {
				count++;
				major = nums[i];
			} else if (major == nums[i]) {
				count++;
			} else
				count--;
		}
		return major;
	}

	static int[] spiralCopy(int[][] inputMatrix) {
		int numRows = inputMatrix.length;
		int numCols = inputMatrix[0].length;

		int topRow = 0;
		int btmRow = numRows - 1;
		int leftCol = 0;
		int rightCol = numCols - 1;
		int[] result = new int[inputMatrix.length * inputMatrix[0].length];
		int k = 0;

		while (topRow <= btmRow && leftCol <= rightCol) {
			// copy the next top row
			for (int i = leftCol; i <= rightCol; i++) {
				result[k] = inputMatrix[topRow][i];
				k++;
			}
			topRow++;

			// copy the next right hand side column
			for (int i = topRow; i <= btmRow; ++i) {
				result[k++] = inputMatrix[i][rightCol];
			}
			rightCol--;

			// copy the next bottom row
			if (topRow <= btmRow) {
				for (int i = rightCol; i >= leftCol; --i) {
					result[k++] = inputMatrix[btmRow][i];
				}
				btmRow--;
			}

			// copy the next left hand side column
			if (leftCol <= rightCol) {
				for (int i = btmRow; i >= topRow; --i) {
					result[k++] = inputMatrix[i][leftCol];
				}
				leftCol++;
			}
		}
		return result;
	}

}
