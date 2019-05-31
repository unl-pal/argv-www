public class Part {
	
	int left;
	int right;
	int top;
	int bottom;
	
	Part(int x0, int y0, int width, int height) {
		left = x0;
		right = x0 + width - 1;
		top = y0;
		bottom = y0 + height - 1;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;
        Part key = (Part) o;
        return left == key.left && right == key.right && top == key.top && bottom == key.bottom;
    }

    /*
    @Override
    public int hashCode() {
        int result = left;
        result = 61 * result + right;
        result = 61 * result + top;
        result = 61 * result + bottom;
        return result;
    }*/
	
	boolean contains(int x, int y) {
		return x >= left && x <= right && y >= top && y <= bottom;
	}
	
	void print() {
		System.out.print("{left=" + left + ", right=" + right + ", top=" + top + ", bottom=" + bottom + "}");
	}
}