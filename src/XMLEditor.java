import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;


public class XMLEditor {

	// storing edges instead of nodes in the XML allows for faster retrieval of
	// nodes for restoring saved class path data, but
	// it will make Dijstra's slower.
	private String fileNameToRead = "ANewFile6.xml";
	private String fileNameToWrite = "ANewFile6.xml";

	private boolean pastDeleted = false;

	ArrayList<Dijstra.Edge> edges;
	ArrayList<Dijstra.Node> nodes;
	ArrayList<Building> buildings;

	public XMLEditor() {
		edges = new ArrayList<Dijstra.Edge>();
		nodes = new ArrayList<Dijstra.Node>();
		buildings = new ArrayList<>();
		
	
		// can get rid of this when the XML map file is done
		initializeBuildings();
		updateBuildingXML();
	}

//	public void initializeNodesEdges() {
//
//		// this method will become obsolete!
//
//		Dijstra.Edge testEdge12 = new Dijstra.Edge();
//		testEdge12.setCost(7);
//		testEdge12.setOutside(true);
//
//		Dijstra.Edge testEdge23 = new Dijstra.Edge();
//		testEdge23.setCost(10);
//		testEdge23.setOutside(true);
//
//		Dijstra.Edge testEdge13 = new Dijstra.Edge();
//		testEdge13.setCost(9);
//		testEdge13.setOutside(true);
//
//		Dijstra.Edge testEdge16 = new Dijstra.Edge();
//		testEdge16.setCost(14);
//		testEdge16.setOutside(true);
//
//		Dijstra.Edge testEdge36 = new Dijstra.Edge();
//		testEdge36.setCost(2);
//		testEdge36.setOutside(true);
//
//		Dijstra.Edge testEdge24 = new Dijstra.Edge();
//		testEdge24.setCost(15);
//		testEdge24.setOutside(true);
//
//		Dijstra.Edge testEdge34 = new Dijstra.Edge();
//		testEdge34.setCost(11);
//		testEdge34.setOutside(true);
//
//		Dijstra.Edge testEdge45 = new Dijstra.Edge();
//		testEdge45.setCost(6);
//		testEdge45.setOutside(true);
//
//		Dijstra.Edge testEdge56 = new Dijstra.Edge();
//		testEdge56.setCost(9);
//		testEdge56.setOutside(true);
//
//		Dijstra.Node testNode1 = new Dijstra.Node();
//		testNode1.setX(69);
//		testNode1.setY(700);
//		testNode1.setName("n1");
//
//		Dijstra.Node testNode2 = new Dijstra.Node();
//		testNode2.setX(500);
//		testNode2.setY(710);
//		testNode2.setName("n2");
//
//		Dijstra.Node testNode3 = new Dijstra.Node();
//		testNode3.setX(500);
//		testNode3.setY(350);
//		testNode3.setName("n3");
//
//		Dijstra.Node testNode4 = new Dijstra.Node();
//		testNode4.setX(950);
//		testNode4.setY(450);
//		testNode4.setName("n4");
//
//		Dijstra.Node testNode5 = new Dijstra.Node();
//		testNode5.setX(600);
//		testNode5.setY(70);
//		testNode5.setName("n5");
//
//		Dijstra.Node testNode6 = new Dijstra.Node();
//		testNode6.setX(100);
//		testNode6.setY(100);
//		testNode6.setName("n6");
//
//		testEdge12.setN1(testNode1);
//		testEdge12.setN2(testNode2);
//
//		testEdge13.setN1(testNode1);
//		testEdge13.setN2(testNode3);
//
//		testEdge16.setN1(testNode1);
//		testEdge16.setN2(testNode6);
//
//		testEdge36.setN1(testNode3);
//		testEdge36.setN2(testNode6);
//
//		testEdge23.setN1(testNode2);
//		testEdge23.setN2(testNode3);
//
//		testEdge24.setN1(testNode2);
//		testEdge24.setN2(testNode4);
//
//		testEdge45.setN1(testNode4);
//		testEdge45.setN2(testNode5);
//
//		testEdge56.setN1(testNode5);
//		testEdge56.setN2(testNode6);
//
//		testEdge34.setN1(testNode3);
//		testEdge34.setN2(testNode4);
//
//		ArrayList<Dijstra.Edge> n1Edges = new ArrayList<Dijstra.Edge>();
//		n1Edges.add(testEdge12);
//		n1Edges.add(testEdge13);
//		n1Edges.add(testEdge16);
//		testNode1.setEdges(n1Edges);
//
//		ArrayList<Dijstra.Edge> n2Edges = new ArrayList<Dijstra.Edge>();
//		n2Edges.add(testEdge12);
//		n2Edges.add(testEdge23);
//		n2Edges.add(testEdge24);
//		testNode2.setEdges(n2Edges);
//
//		ArrayList<Dijstra.Edge> n3Edges = new ArrayList<Dijstra.Edge>();
//		n3Edges.add(testEdge34);
//		n3Edges.add(testEdge23);
//		n3Edges.add(testEdge13);
//		n3Edges.add(testEdge36);
//		testNode3.setEdges(n3Edges);
//
//		ArrayList<Dijstra.Edge> n4Edges = new ArrayList<Dijstra.Edge>();
//		n4Edges.add(testEdge24);
//		n4Edges.add(testEdge34);
//		n4Edges.add(testEdge45);
//		testNode4.setEdges(n4Edges);
//
//		ArrayList<Dijstra.Edge> n5Edges = new ArrayList<Dijstra.Edge>();
//		n5Edges.add(testEdge56);
//		n5Edges.add(testEdge45);
//		testNode5.setEdges(n5Edges);
//
//		ArrayList<Dijstra.Edge> n6Edges = new ArrayList<Dijstra.Edge>();
//		n6Edges.add(testEdge36);
//		n6Edges.add(testEdge16);
//		n6Edges.add(testEdge56);
//		testNode6.setEdges(n6Edges);
//
//		nodes.add(testNode1);
//		nodes.add(testNode2);
//		nodes.add(testNode3);
//		nodes.add(testNode4);
//		nodes.add(testNode5);
//		nodes.add(testNode6);
//
//		edges.add(testEdge12);
//		edges.add(testEdge16);
//		edges.add(testEdge13);
//		edges.add(testEdge23);
//		edges.add(testEdge24);
//		edges.add(testEdge36);
//		edges.add(testEdge45);
//		edges.add(testEdge56);
//		edges.add(testEdge34);
//
//	}
	
	public void initializeBuildings()
	{
		Building temp = new Building();
		temp.setFloorOne("School Floor Plans/png/Branam Innovation Center.png");
		temp.setFloor(0);
		temp.setX(1330);
		temp.setY(310);
		temp.setDefaultZoom(.08);
		buildings.add(temp);
		
		Building temp2 = new Building();
		temp2.setFloorOne("School Floor Plans/png/Crapo Hall 1st Floor.png");
		temp2.setFloorTwo("School Floor Plans/png/Crapo Hall 2nd Floor.png");
		temp2.setFloorThree("School Floor Plans/png/Crapo Hall 3rd Floor.png");
		temp2.setFloor(1);
		temp2.setX(1000);
		temp2.setY(485);
		temp2.setDefaultZoom(.05);
		buildings.add(temp2);
		
//		Building temp3 = new Building();
//		temp3.setFloorOne("School Floor Plans/png/Facilities Admin Srvs Building.png");
//		temp3.setFloor(0);
//		buildings.add(temp3);
		
		Building temp4 = new Building();
		temp4.setFloorOne("School Floor Plans/png/Hadley Hall 1st Floor.png");
		temp4.setFloorTwo("School Floor Plans/png/Hadley Hall 2nd Floor.png");
		temp4.setFloor(0);
		temp4.setX(1050);
		temp4.setY(640);
		temp4.setDefaultZoom(.05);
		buildings.add(temp4);
		
		Building temp5 = new Building();
		temp5.setFloorOne("School Floor Plans/png/Hatfield Hall Lower Level.png");
		temp5.setFloorTwo("School Floor Plans/png/Hatfield Hall 1st Floor.png");
		temp5.setFloorThree("School Floor Plans/png/Hatfield Hall 2nd Floor.png");
		temp5.setX(1230);
		temp5.setY(840);
		temp5.setDefaultZoom(.05);
		temp5.setFloor(0);
		buildings.add(temp5);
		
		Building temp6 = new Building();
		temp6.setFloorOne("School Floor Plans/png/Kremer Innovation Center.png");
		temp6.setX(1335);
		temp6.setY(360);
		temp6.setDefaultZoom(.05);
		temp6.setFloor(0);
		buildings.add(temp6);
		
		Building temp7 = new Building();
		temp7.setFloorOne("School Floor Plans/png/Logan Library 1st Floor.png");
		temp7.setFloorTwo("School Floor Plans/png/Logan Library 2nd Floor.png");
		temp7.setFloorThree("School Floor Plans/png/Logan Library 3rd Floor.png");
		temp7.setX(960);
		temp7.setY(515);
		temp7.setDefaultZoom(.05);
		temp7.setFloor(0);
		buildings.add(temp7);
		
		Building temp8 = new Building();
		temp8.setFloorOne("School Floor Plans/png/Moench Hall Lower Level 2.png");
		temp8.setFloorTwo("School Floor Plans/png/Moench Hall Lower Level 1.png");
		temp8.setFloorThree("School Floor Plans/png/Moench Hall 1st Floor.png");
		temp8.setFloorFour("School Floor Plans/png/Moench Hall 2nd Floor.png");
		temp8.setX(1110);
		temp8.setY(490);
		temp8.setDefaultZoom(.05);
		temp8.setFloor(2);
		buildings.add(temp8);
		
		Building temp9 = new Building();
		temp9.setFloorOne("School Floor Plans/png/Myers Hall 1st Floor.png");
		temp9.setFloorTwo("School Floor Plans/png/Myers Hall 2nd Floor.png");
		temp9.setX(1200);
		temp9.setY(485);
		temp9.setDefaultZoom(.05);
		temp9.setFloor(0);
		buildings.add(temp9);
		
		Building temp10 = new Building();
		temp10.setFloorOne("School Floor Plans/png/Olin Hall 1st Floor.png");
		temp10.setFloorTwo("School Floor Plans/png/Olin Hall 2nd Floor.png");
		temp10.setX(880);
		temp10.setY(610);
		temp10.setDefaultZoom(.05);
		temp10.setFloor(0);
		buildings.add(temp10);
	}

	/**
	 * for use when creating a new node. Allows XMLEditor to track the node for
	 * addition of the edges to an XML file
	 * 
	 * @param x
	 * 
	 * @param y
	 * @param name
	 * @return
	 */
	public Dijstra.Node initializeNewNode(int x, int y, String name) {
		

		Dijstra.Node newNode = new Dijstra.Node();
		System.out.println("x: " + x);
		System.out.println("y: " + y);


		newNode.setX(x);
		newNode.setY(y);
		newNode.setName(name);
		newNode.setSelected(false);
		nodes.add(newNode);

		return newNode;
	}

	public Dijstra.Edge initializeNewEdge(Dijstra.Node node1, Dijstra.Node node2) {
		Dijstra.Edge newEdge = new Dijstra.Edge();
		newEdge.setWCA(true);
		newEdge.setOutside(true);
		newEdge.setN1(node1);
		newEdge.setN2(node2);
		newEdge.setCost((int)this.calculateCost(node1, node2));
		node1.getEdges().add(newEdge);
		node2.getEdges().add(newEdge);

		edges.add(newEdge);
		return newEdge;
	}
	
	private double calculateCost(Dijstra.Node node1, Dijstra.Node node2) {
		// TODO Auto-generated method stub
		double xdiff = node1.getX() - node2.getX();
		double ydiff = node1.getY() - node2.getY();
		
		return Math.sqrt((Math.pow(xdiff, 2) + Math.pow(ydiff, 2)));
	}

	public void setEdgeOutside(Dijstra.Edge e, boolean b) {
		for (Dijstra.Edge edge : edges) {
			if (edge.equals(e)) {
				e.setOutside(b);
			}
		}
	}
	
	public void setEdgeWCA(Dijstra.Edge e, boolean b) {
		for (Dijstra.Edge edge : edges) {
			if (edge.equals(e)) {
				e.setWCA(b);
			}
		}
	}

	public void updateMapXML() {
		if (!this.pastDeleted) {
			String stringPath = fileNameToRead;
			Path path = new File(stringPath).toPath();
			try {
				Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.pastDeleted = true;
		}

		try {
			this.writeEdges(this.edges, fileNameToWrite);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updateBuildingXML()
	{
		try
		{
			this.writeBuildings(buildings, "AllBuildings.xml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * write all of the edges to the XML file for retrieval with read
	 * 
	 * @param E
	 * @param filename
	 * @throws Exception
	 */
	public void writeEdges(ArrayList<Dijstra.Edge> edges, String filename) throws Exception {

		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		for (int i = 0; i < edges.size(); i++) {
			
			encoder.writeObject(edges.get(i)); // how to make this delete the current file and then recreate it?
		}
		encoder.close();
	}
	
	public static void writeBuildings(ArrayList<Building> buildings, String filename) throws Exception
	{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		encoder.writeObject(buildings);
		encoder.close();
	}

	/**
	 * return a list of the nodes in the XML doc generated above
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public void read(String filename) throws Exception {
		FileInputStream file;
		try {
		file = new FileInputStream(filename);
		} catch (Exception e) {
			this.writeEdges(edges, filename);
			file = new FileInputStream(filename);

		}
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(file));
		int edgeCount = 0;

		while (true) {
			try {
				Object tempEdgeMaybe = decoder.readObject();
				if (tempEdgeMaybe instanceof Dijstra.Edge) {
					this.edges.add((Dijstra.Edge) tempEdgeMaybe); // for some reason the size of edges increments when
																	// adding nodes??
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}

		decoder.close();
	}
	
	public ArrayList<Building> readBuildings(String filename) throws Exception
	{
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
		ArrayList<Building> buildings = new ArrayList<>();
		while(true)
		{
			try
			{
				buildings.addAll((ArrayList<Building>) decoder.readObject());
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				break;
			}
		}
		decoder.close();
		return buildings;
	}

	public HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> formatEdges(ArrayList<Dijstra.Edge> edges) {
		if (edges == null) {
			System.out.println("XML file does not contain edges");
			throw new IllegalArgumentException();
		}
		HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> output = new HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>>();
		for (int i = 0; i < edges.size(); i++) {
			output.put(edges.get(i).getN1(), edges.get(i).getN1().getEdges());
			output.put(edges.get(i).getN2(), edges.get(i).getN2().getEdges());

		}

		// set selected to false for all nodes
		Set<Dijstra.Node> keys = output.keySet();
		int edgeCount = 0;
		for (Dijstra.Node i : keys) {
			i.setSelected(false);
			for (Dijstra.Edge e : i.getEdges()) {
				edgeCount++;
			}
		}

		return output;
	}

	public HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> getNodeToEdgeMap() {
		try {
			read(fileNameToRead);


			return formatEdges(this.edges);

		} catch (Exception e) {
			System.out.println("either SomeEdges.xml is empty or does not exist");
			e.printStackTrace();
		}

		return null;

	}
	


}