
public class RobotImageComponent extends ImageComponent{

	private static final long serialVersionUID = 5944902400245422120L;

	public RobotImageComponent(String path, int width, int height) {
		super(path, width, height);
		// TODO Auto-generated constructor stub
		
	}
	
	public void moveRight(int pixel) {
		int x = (int) super.getLocation().getX();
		int y = (int) super.getLocation().getY();
		if (x + pixel < Constant.WIDTH - Constant.GRIDWIDTH/2 - super.getWidth()) {
			setLocation(x + pixel, y);
		}
	}
	
	public void moveLeft(int pixel) {
		int x = (int) super.getLocation().getX();
		int y = (int) super.getLocation().getY();
		if (x - pixel >= Constant.MARGINLEFT + Constant.GRIDWIDTH/2) {
			setLocation(x - pixel, y);
		}
	}
	
	public void moveUp(int pixel) {
		int x = (int) super.getLocation().getX();
		int y = (int) super.getLocation().getY();
		if (y - pixel >= Constant.MARGINTOP + Constant.GRIDHEIGHT/2) {
			setLocation(x, y - pixel);
		}
	}
	
	public void moveDown(int pixel) {
		int x = (int) super.getLocation().getX();
		int y = (int) super.getLocation().getY();
		if (y  + pixel < Constant.HEIGHT - Constant.GRIDHEIGHT/2 - super.getHeight()) {
			setLocation(x, y + pixel);
		}
	}

}
