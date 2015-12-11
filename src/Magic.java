
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Magic {

	private Robot robot;
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		Magic magic = new Magic();
		magic.start("paint");
		//zaki darha
	}

	public Magic() throws AWTException {
		robot = new Robot();
	}

	public void start(String prog) throws InterruptedException {
		slideMouseTo(new Point(23, 1004));
		robot.mouseMove(10, 1004);

		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(70);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		for (char a : prog.toCharArray()) {
			robot.keyPress(Character.toUpperCase(a));
			Thread.sleep(30);
			robot.keyRelease(Character.toUpperCase(a));
			Thread.sleep(70);
		}

		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(30);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Thread.sleep(1000);
//
//		Point p = new Point(800, 500);
//		slideMouseTo(p);
//
//		Thread.sleep(4000);
//		robot.mouseMove(p.getX(), p.getY());
//
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//
//		int rad = 100, angle = 363;
//		for (int i = 0; i < 100; i++)
//			rotateMouseArroundRadius(p, 2, rad, angle, 2);
//
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);

	}

	public void slideMouseTo(Point cible) throws InterruptedException {

		int difX = Math.abs(cible.getX()
				- MouseInfo.getPointerInfo().getLocation().x);
		int difY = Math.abs(cible.getY()
				- MouseInfo.getPointerInfo().getLocation().y);

		difX = difX == 0 ? 1 : difX;
		difY = difY == 0 ? 1 : difY;

		Point currentPosition = new Point(MouseInfo.getPointerInfo()
				.getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		Point tmp = new Point(currentPosition.getX(), currentPosition.getY());

		int[][] trajectory = new int[difX < difY ? difY : difX][2];

		int proportion = (difX > difY ? difX / difY : difY / difX) + 1;

		int xDirection = (currentPosition.getX() > cible.getX() ? -1 : 1);
		int yDirection = (currentPosition.getY() > cible.getY() ? -1 : 1);

		int propIndicator = 0;

		if (difX > difY) {

			for (int i = 0; i < difX; i++) {

				tmp.addDx(xDirection);

				if (propIndicator >= proportion) {
					tmp.addDy(yDirection);
					propIndicator = 0;

				}

				propIndicator++;

				trajectory[i][0] = tmp.getX();
				trajectory[i][1] = tmp.getY();

			}

		} else {

			for (int i = 0; i < difY; i++) {

				tmp.addDy(yDirection);

				if (propIndicator >= proportion) {
					tmp.addDx(xDirection);
					propIndicator = 0;

				}

				propIndicator++;

				trajectory[i][0] = tmp.getX();
				trajectory[i][1] = tmp.getY();

			}

		}

		int size = difX < difY ? difY : difX;

		for (int i = 0; i < size; i++) {

			robot.mouseMove(trajectory[i][0], trajectory[i][1]);
			Thread.sleep(1);
		}

		cible.setX(trajectory[size - 1][0]).setY(trajectory[size - 1][1]);

	}

	public void rotateMouseArroundRadius(Point currentPosition, int pvt,
			int radius, int degree, int direction) throws InterruptedException {

		// Point currentPosition = new Point(MouseInfo.getPointerInfo()
		// .getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

		double angle = Math.toRadians(degree);

		int size = Math.abs((int) (angle * radius));
		int subDiv = radius / 10;

		int[][] trajectory = new int[size / subDiv][2];

		Point pivot = null;

		switch (pvt) {
		case 1:
			pivot = new Point(currentPosition.getX() - radius,
					currentPosition.getY());
			break;
		case 2:
			pivot = new Point(currentPosition.getX(), currentPosition.getY()
					- radius);
			break;
		case 3:
			pivot = new Point(currentPosition.getX() + radius,
					currentPosition.getY());
			break;
		case 4:
			pivot = new Point(currentPosition.getX(), currentPosition.getY()
					+ radius);
			break;
		}

		Point point = new Point(currentPosition.getX(), currentPosition.getY());
		double step = (angle / size) * subDiv;
		for (int i = 0; i < (size / subDiv); i++) {

			point = rotatePoint(pivot, step, point, direction);
			trajectory[i][0] = point.getX();
			trajectory[i][1] = point.getY();
		}

		for (int i = 0; i < size / subDiv; i++) {

			robot.mouseMove(trajectory[i][0], trajectory[i][1]);
			Thread.sleep(1);
		}

		currentPosition.setX(point.getX()).setY(point.getY());

	}

	private Point rotatePoint(Point pivot, double angle, Point p, int direction) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);

		// translate point back to origin:
		p.addDx(-pivot.getX());
		p.addDy(-pivot.getY());

		double xnew = 0d;
		double ynew = 0d;

		if (direction == 1) {
			xnew = p.getX() * cos - p.getY() * sin;
			ynew = p.getX() * sin + p.getY() * cos;
		} else {
			xnew = p.getX() * cos + p.getY() * sin;
			ynew = -p.getX() * sin + p.getY() * cos;
		}

		return new Point((int) (xnew + pivot.getX()),
				(int) (ynew + pivot.getY()));
		// p.setX();
		// p.setY();
		// return p;
	}

}
