

public class MyClass2 {
	// The method you need tests for
	private int myMethod2(int x, int y) {
		int jpfIfCounter = 0;
		int z = x + y;
		if (z > 0) {
			jpfIfCounter++;
			z = 1;
		}
		if (x < 5) {
			jpfIfCounter++;
			z = -z;
		}
		return z;
	}
}