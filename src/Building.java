import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Building {
	
	private ArrayList<BufferedImage> floors;
	private int floor;
	Building(ArrayList<BufferedImage> floors)
	{
		this.floors = floors;
		floor = 0;
	}
	
	public int getFloorNumber()
	{
		return floor;
	}
	
	public BufferedImage getFloorImage()
	{
		return floors.get(floor);
	}
}
