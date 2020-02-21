import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Floor {
	private static ArrayList<BufferedImage> floors;
	public static int floor;
	
	public Floor()
	{
		floors = new ArrayList<>();
		try {
			floors.add(ImageIO.read(new File("School Floor Plans/Floor 0.png")));
			floors.add(ImageIO.read(new File("School Floor Plans/Floor 1.png")));
			floors.add(ImageIO.read(new File("School Floor Plans/Floor 2.png")));
			floors.add(ImageIO.read(new File("School Floor Plans/Floor 3.png")));
		} catch (IOException e) {
			System.out.println("Floor image loading failed");
			e.printStackTrace();
		}
		floor = 2;
	}
	
	public static int getFloor()
	{
		return floor;
	}
	
	public static void increaseFloor(GUI gui)
	{
		if(floor + 1 < floors.size())
		{
			floor++;
			gui.repaint();
		}
	}
	
	public static void decreaseFloor(GUI gui)
	{
		if(floor - 1 >= 0)
		{
			floor--;
			gui.repaint();
		}
	}
	
	public BufferedImage getImage()
	{
		return floors.get(floor);
	}
}
