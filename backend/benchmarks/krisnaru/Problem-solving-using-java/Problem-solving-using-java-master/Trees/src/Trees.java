import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Trees {
	public static void main(String[] args) {
		TreeNode root = constructBST();
		InorderIterator it = new InorderIterator(root);
		while(it.hasNext()){
			System.out.println(it.next().data);
		}
		// printPreorderIterative(root);
		//System.out.println(diameter(root));
	}

	public static TreeNode constructBST() {
		TreeNode root = insertBST(null, 4);
		insertBST(root, 2);
		insertBST(root, 6);
		insertBST(root, 1);
		insertBST(root, 3);
		insertBST(root, 5);
		insertBST(root, 7);

		return root;
	}

	static TreeNode insertBST(TreeNode root, int data) {
		if (root == null) {
			return new TreeNode(data);
		}
		if (data < root.data) {
			root.left = insertBST(root.left, data);
		} else {
			root.right = insertBST(root.right, data);
		}
		return root;
	}

	public static void printInorder(TreeNode root) {
		if (root != null) {
			printInorder(root.left);
			System.out.print(root.data + ",");
			printInorder(root.right);
		}
	}

	public static void printPreorder(TreeNode root) {
		if (root != null) {
			System.out.print(root.data + ",");
			printInorder(root.left);
			printInorder(root.right);
		}
	}

	public static void printPostorder(TreeNode root) {
		if (root != null) {
			printInorder(root.left);
			printInorder(root.right);
			System.out.print(root.data + ",");
		}
	}

	static void printLevelorder(TreeNode root) {
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		while (!q.isEmpty()) {
			root = q.remove();
			System.out.print(root.data + ",");
			if (root.left != null) {
				q.add(root.left);
			}
			if (root.right != null) {
				q.add(root.right);
			}
		}
	}

	static void printInorderIterative(TreeNode root) {
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);

		while (!s.isEmpty()) {
			if (root.left != null) {
				s.push(root.left);
				root = root.left;
			} else {
				root = s.pop();
				System.out.print(root.data + ",");
				if (root.right != null) {
					s.push(root.right);
					root = root.right;
				}
			}
		}
	}

	static void printPreorderIterative(TreeNode root) {
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		while (!s.isEmpty()) {
			root = s.pop();
			System.out.print(root.data + ",");

			if (root.right != null)
				s.push(root.right);

			if (root.left != null)
				s.push(root.left);
		}
	}

	static TreeNode findLCAofBST(TreeNode root, int n1, int n2) {
		if (root == null)
			return null;
		if (root.data < n1 && root.data < n2) {
			return findLCAofBST(root.right, n1, n2);
		} else if (root.data > n1 && root.data > n2) {
			return findLCAofBST(root.left, n1, n2);
		} else {
			return root;
		}
	}

	static TreeNode findLCAofBT(TreeNode root, int n1, int n2) {
		if (root == null)
			return null;
		if (root.data == n1 || root.data == n2) {
			return root;
		}
		TreeNode lca_left = findLCAofBT(root.left, n1, n2);
		TreeNode lca_right = findLCAofBT(root.right, n1, n2);
		if (lca_left != null && lca_right != null) {
			return root;
		}
		if (lca_left != null) {
			return lca_left;
		} else {
			return lca_right;
		}
	}
	
	static TreeNode prev = null;
	static boolean isTreeBST(TreeNode root){
		if(root == null) return true;
		if(!isTreeBST(root.left))
			return false;
		
		if(prev == null){
			prev = root;
		}
		else{
			if(prev.data > root.data){
				return false;
			}
			prev = root;
		}
		return isTreeBST(root.right);
	}
	
	static int height(TreeNode root){
		if(root == null){
			return 0;
		}
		else
		{
			return 1+Math.max(height(root.left), height(root.right));
		}
	}
	static int diameter(TreeNode root){
		if(root == null) return 0;
		int lh = height(root.left);
		int rh = height(root.right);
		int ld = diameter(root.left);
		int rd = diameter(root.right);
		return Math.max(lh+rh+1, Math.max(ld, rd));
	}
}

class TreeNode {
	int data;
	TreeNode left, right;

	public TreeNode(int data) {
		this.data = data;
	}
}

class InorderIterator implements Iterator{
	Stack<TreeNode> s = new Stack<TreeNode>();

	private void pushLeft(TreeNode root){
		while(root != null){
			s.push(root);
			root = root.left;
		}
	}
	
	public InorderIterator(TreeNode root){
		pushLeft(root);
	}
	
	@Override
	public boolean hasNext() {
		return !s.isEmpty();
	}

	@Override
	public TreeNode next() {
		TreeNode curr = s.pop();
		pushLeft(curr.right);
		return curr;
	}
}
