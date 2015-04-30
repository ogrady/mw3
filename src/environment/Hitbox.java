package environment;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.geom.Shape;

/**
 * Holds a collection of {@link Shape} and generalizes access methods for combinations of shapes
 *
 * @author Fabian
 *
 */
public class Hitbox {
	private ArrayList<Shape> individualHitboxes;
	public Hitbox(Shape[] _individualHitboxes) {
		for(Shape s : _individualHitboxes) {
		}
		individualHitboxes = new ArrayList<Shape>(Arrays.asList(_individualHitboxes));
	}

	public Hitbox(Shape hitboxShape) {
		individualHitboxes = new ArrayList<Shape>();
		individualHitboxes.add(hitboxShape);
	}

	public void addShape(Shape s) {
		individualHitboxes.add(s);
	}

	public boolean contains(float x, float y) {
		for(Shape s : individualHitboxes) {
			if(s.contains(x,y)) {
				return true;
			}
		}

		return false;
	}

	public boolean intersects(Shape other) {
		boolean intersects = false;
		for(Shape s : individualHitboxes) {
			if(s.intersects(other)) intersects = true;
		}

		return intersects;
	}

	public float[] getPoints() {
		float[] points = new float[0];
		for(Shape s : individualHitboxes) {
			points = concatArrays(s.getPoints(),points);
		}
		return points;
	}

	public float[] getAllY() {
		float[] points = getPoints();
		float[] yValues = new float[points.length / 2];
		int j = 0;
		for(int i = 1; i < points.length; i=i+2) {
			if(i % 2 != 0) {
				yValues[j++] = points[i];
			}
		}

		return yValues;
	}

	public boolean intersects(Hitbox other) {
		boolean intersects = false;
		for(Shape s : individualHitboxes) {
			for(Shape s2 : other.getShapes()) {
				if(s.intersects(s2)) intersects = true;
			}
		}

		return intersects;
	}

	protected  static float[] concatArrays(float[] first, float[] second) {
		float[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public float getCenterX() {
		return getMinX() + (getWidth()/2);
	}

	public float getCenterY() {
		return getMinY() + (getHeight()/2);
	}

	public float getWidth() {
		return (getMaxX()-getMinX());
	}

	public float getHeight() {
		return (getMaxY()-getMinY());
	}

	public float getMinX() {
		float minX = Float.MAX_VALUE;
		for(Shape s : individualHitboxes) {
			if(minX > s.getMinX()) minX = s.getMinX();
		}

		return minX;
	}

	public float getMaxX() {
		float maxX = 0;
		for(Shape s : individualHitboxes) {
			if(maxX < s.getMaxX()) maxX = s.getMaxX();
		}

		return maxX;
	}

	public float getMinY() {
		float minY = Float.MAX_VALUE;
		for(Shape s : individualHitboxes) {
			if(minY > s.getMinY()) minY = s.getMinY();
		}

		return minY;
	}

	public float getMaxY() {
		float maxY = 0;
		for(Shape s : individualHitboxes) {
			if(maxY < s.getMaxY()) maxY = s.getMaxY();
		}

		return maxY;
	}

	public ArrayList<Shape> getShapes() {
		return individualHitboxes;
	}
}
