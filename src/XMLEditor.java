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
	private String defaultFile = "ANewFile7.xml";
	private String fileNameToRead;
	private String fileNameToWrite;

	private boolean pastDeleted = false;

	ArrayList<Dijstra.Edge> edges;
	ArrayList<Dijstra.Node> nodes;

	public XMLEditor() {
		this.fileNameToRead =  defaultFile;
		this.setFileNameToWrite(defaultFile);
		edges = new ArrayList<Dijstra.Edge>();
		nodes = new ArrayList<Dijstra.Node>();
		
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
	public Dijstra.Node initializeNewNode(int x, int y, int floor, String name) {
		

		Dijstra.Node newNode = new Dijstra.Node();



		newNode.setX(x);
		newNode.setY(y);

		newNode.setFloor(floor);
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
			this.writeEdges(this.edges, getFileNameToWrite());
		} catch (Exception e) {
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
		if (filename.equals("")) {
			System.out.println("invalid file name");
			return;
		}

		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		for (int i = 0; i < edges.size(); i++) {
			
			encoder.writeObject(edges.get(i)); // how to make this delete the current file and then recreate it?
		}
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






	public String getFileNameToWrite() {
		return fileNameToWrite;
	}



	public void setFileNameToWrite(String fileNameToWrite) {
		this.fileNameToWrite = fileNameToWrite;
	}
	


}