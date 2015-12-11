

public class Point {
	
	
	
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private int x,y;
	
	

	public int getX() {
		return x;
	}

	public Point setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Point setY(int y) {
		this.y = y;
		return this;
	}
	
	public Point addDx(int dx){
		this.x+=dx; 
		return this;
	}
	
	public Point addDy(int dy){
		this.y+=dy; 
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Point p = (Point)obj;
		
		return p.getX()==this.getX() && p.getY()==this.getY();
	}
	

}
