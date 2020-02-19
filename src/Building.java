import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Building implements Serializable{
	
	private int floor;
	private ArrayList<BufferedImage> floors = new ArrayList<>();
	private String floorOne;
	private String floorTwo;
	private String floorThree;
	private String floorFour;
	
	private double x;
	private double y;
	
	public Building()
	{
		
	}
	
	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor) 
	{
		this.floor = floor;
	}
	
	public BufferedImage getFloorImage()
	{
		return floors.get(floor);
	}
	
	public void loadImages()
	{
		if(floorOne != null)
		{
			try {
				floors.add(ImageIO.read(new File(floorOne)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(floorTwo != null)
			{
				try {
					floors.add(ImageIO.read(new File(floorTwo)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(floorThree != null)
				{
					try {
						floors.add(ImageIO.read(new File(floorThree)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(floorFour != null)
					{
						try {
							floors.add(ImageIO.read(new File(floorFour)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void setFloorOne(String floor)
	{
		floorOne = floor;
	}
	
	public void setFloorTwo(String floor)
	{
		floorTwo = floor;
	}
	
	public void setFloorThree(String floor)
	{
		floorThree = floor;
	}
	
	public void setFloorFour(String floor)
	{
		floorFour = floor;
	}
	
	public String getFloorOne()
	{
		return floorOne;
	}
	
	public String getFloorTwo()
	{
		return floorTwo;
	}
	
	public String getFloorThree()
	{
		return floorThree;
	}
	
	public String getFloorFour()
	{
		return floorFour;
	}
	
	public boolean increaseFloor()
	{
		if((floor + 1) <= floors.size())
		{
			floor++;
			return true;
		}
		return false;
	}
	
	public boolean decreaseFloor()
	{
		if((floor - 1) >= 0)
		{
			floor--;
			return true;
		}
		return false;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getY()
	{
		return y;
	}
}
