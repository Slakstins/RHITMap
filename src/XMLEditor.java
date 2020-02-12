import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class XMLEditor {
	// storing edges instead of nodes in the XML allows for faster retrieval of
	// nodes for restoring saved class path data, but
	// it will make Dijstra's slower.
	// Saves are not yet implemented, but the map can be generated using a temporary
	// java file to create the XML to be stored.

	ArrayList<Dijstra.Edge> edges;
	ArrayList<Dijstra.Node> nodes;

	public XMLEditor() {
		edges = new ArrayList<Dijstra.Edge>();
		nodes = new ArrayList<Dijstra.Node>();
		initializeNodesEdges();
		updateMapXML();

	}

	public void initializeNodesEdges() {


		Dijstra.Edge testEdge1 = new Dijstra.Edge();
		testEdge1.setCost(5);
		testEdge1.setOutside(true);
		edges.add(testEdge1);

		Dijstra.Node testNode1 = new Dijstra.Node();

		testNode1.setEdges(edges);
        testNode1.setY(10);
        testNode1.setX(420);
		
		
		
		

		Dijstra.Node testNode2 = new Dijstra.Node();

        testNode2.setY(70);
        testNode2.setX(420);

		testNode1.setEdges(edges);
		testNode2.setEdges(edges);

		nodes.add(testNode2);
		nodes.add(testNode1);

		testEdge1.setStartNode(testNode1);

		testEdge1.setEndNode(testNode2);
		
		
		
		

	}
	
	public void updateMapXML() {
		try {
			this.writeEdges(edges, "AllEdges.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * write all of the edges to the XML file for retreival with read
	 * 
	 * @param E
	 * @param filename
	 * @throws Exception
	 */
	public void writeEdges(ArrayList<Dijstra.Edge> edges, String filename) throws Exception {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		for (int i = 0; i < edges.size(); i++) {
			encoder.writeObject(edges.get(i));
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
	public ArrayList<Dijstra.Edge> read(String filename) throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
		ArrayList<Dijstra.Edge> edges = new ArrayList<Dijstra.Edge>();
		while (true) {
			try {
				edges.add((Dijstra.Edge) decoder.readObject());

			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}
		decoder.close();
		return edges;
	}

	public HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> formatEdges(ArrayList<Dijstra.Edge> edges) {
		if (edges == null) {
			System.out.println("XML file does not contain edges");
			throw new IllegalArgumentException();
		}
		HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> output = new HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>>();
		for (int i = 0; i < edges.size(); i++) {
			output.put(edges.get(i).getStartNode(), edges.get(i).getStartNode().getEdges());
			output.put(edges.get(i).getEndNode(), edges.get(i).getEndNode().getEdges());

		}
		
		Set<Dijstra.Node> keys = output.keySet();
		for (Dijstra.Node i : keys) {
			i.addNodeEllipseToDraw(); //calls to make the node drawable
		}
		
		
		return output;
	}

	public HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> getNodeToEdgeMap() {
		ArrayList<Dijstra.Edge> edges = null;
		try {
			edges = read("AllEdges.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatEdges(edges);

	}

}