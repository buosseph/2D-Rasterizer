public class Vertex {
	private float x;
	private float y;
	private int r;
	private int g;
	private int b;
	private int a;

	// xy
	public Vertex(float _x, float _y) {
		x = _x;
		y = _y;
		r = g = b = a =255;
	}

	// xyc & xyrgb
	public Vertex(float _x, float _y, int _r, int _g, int _b) {
		x = _x;
		y = _y;
		r = _r;
		g = _g;
		b = _b;
		a = 255;
	}

	// xyca & xyrgba
	public Vertex(float _x, float _y, int _r, int _g, int _b, int _a) {
		x = _x;
		y = _y;
		r = _r;
		g = _g;
		b = _b;
		a = _a;
	}

	public void setX(float _x) { x = _x; }
	public void setY(float _y) { y = +y; }

	public float getX() { return x; }
	public float getY() { return y; }
	public int getR() { return r; }
	public int getG() { return g; }
	public int getB() { return b; }
	public int getA() { return a; }
}