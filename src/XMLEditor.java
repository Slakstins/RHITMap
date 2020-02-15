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
		
		//can get rid of this when the XML map file is done
		updateMapXML();

	}

	public void initializeNodesEdges() {
		
		//this method will become obsolete!


		Dijstra.Edge testEdge12 = new Dijstra.Edge();
		testEdge12.setCost(7);
		testEdge12.setOutside(true);
		
		Dijstra.Edge testEdge23 = new Dijstra.Edge();
		testEdge23.setCost(10);
		testEdge23.setOutside(true);
		
		Dijstra.Edge testEdge13 = new Dijstra.Edge();
		testEdge13.setCost(9);
		testEdge13.setOutside(true);
		
		Dijstra.Edge testEdge16 = new Dijstra.Edge();
		testEdge16.setCost(14);
		testEdge16.setOutside(true);
		
		Dijstra.Edge testEdge36 = new Dijstra.Edge();
		testEdge36.setCost(2);
		testEdge36.setOutside(true);
		
		Dijstra.Edge testEdge24 = new Dijstra.Edge();
		testEdge24.setCost(15);
		testEdge24.setOutside(true);
		
		Dijstra.Edge testEdge34 = new Dijstra.Edge();
		testEdge34.setCost(11);
		testEdge34.setOutside(true);
		
		Dijstra.Edge testEdge45 = new Dijstra.Edge();
		testEdge45.setCost(6);
		testEdge45.setOutside(true);
		
		Dijstra.Edge testEdge56 = new Dijstra.Edge();
		testEdge56.setCost(9);
		testEdge56.setOutside(true);
		
		Dijstra.Node testNode1 = new Dijstra.Node();
        testNode1.setX(69);
        testNode1.setY(700);
        testNode1.setName("n1");
        
        Dijstra.Node testNode2 = new Dijstra.Node();
        testNode2.setX(500);
        testNode2.setY(710);
        testNode2.setName("n2");
        
        Dijstra.Node testNode3 = new Dijstra.Node();
        testNode3.setX(500);
        testNode3.setY(350);
        testNode3.setName("n3");
        
        Dijstra.Node testNode4 = new Dijstra.Node();
        testNode4.setX(950);
        testNode4.setY(450);
        testNode4.setName("n4");
        
        Dijstra.Node testNode5 = new Dijstra.Node();
        testNode5.setX(600);
        testNode5.setY(70);
        testNode5.setName("n5");
        
        Dijstra.Node testNode6 = new Dijstra.Node();
        testNode6.setX(100);
        testNode6.setY(100);
        testNode6.setName("n6");
        
        
		
		testEdge12.setN1(testNode1);
		testEdge12.setN2(testNode2);
		
		testEdge13.setN1(testNode1);
		testEdge13.setN2(testNode3);
		
		testEdge16.setN1(testNode1);
		testEdge16.setN2(testNode6);
		
		testEdge36.setN1(testNode3);
		testEdge36.setN2(testNode6);
		
		testEdge23.setN1(testNode2);
		testEdge23.setN2(testNode3);
		
		testEdge24.setN1(testNode2);
		testEdge24.setN2(testNode4);
		
		testEdge45.setN1(testNode4);
		testEdge45.setN2(testNode5);

		testEdge56.setN1(testNode5);
		testEdge56.setN2(testNode6);
		
		testEdge34.setN1(testNode3);
		testEdge34.setN2(testNode4);
		
		
		
        ArrayList<Dijstra.Edge> n1Edges = new ArrayList<Dijstra.Edge>();
        n1Edges.add(testEdge12);
        n1Edges.add(testEdge13);
        n1Edges.add(testEdge16);
        testNode1.setEdges(n1Edges);

        ArrayList<Dijstra.Edge> n2Edges = new ArrayList<Dijstra.Edge>();
        n2Edges.add(testEdge12);
        n2Edges.add(testEdge23);
        n2Edges.add(testEdge24);
        testNode2.setEdges(n2Edges);
        
        ArrayList<Dijstra.Edge> n3Edges = new ArrayList<Dijstra.Edge>();
        n3Edges.add(testEdge34);
        n3Edges.add(testEdge23);
        n3Edges.add(testEdge13);
        n3Edges.add(testEdge36);
        testNode3.setEdges(n3Edges);
       
        ArrayList<Dijstra.Edge> n4Edges = new ArrayList<Dijstra.Edge>();
        n4Edges.add(testEdge24);
        n4Edges.add(testEdge34);
        n4Edges.add(testEdge45);
        testNode4.setEdges(n4Edges);
        
        ArrayList<Dijstra.Edge> n5Edges = new ArrayList<Dijstra.Edge>();
        n5Edges.add(testEdge56);
        n5Edges.add(testEdge45);
        testNode5.setEdges(n5Edges);
        
        ArrayList<Dijstra.Edge> n6Edges = new ArrayList<Dijstra.Edge>();
        n6Edges.add(testEdge36);
        n6Edges.add(testEdge16);
        n6Edges.add(testEdge56);
        testNode6.setEdges(n6Edges);
		



		


		nodes.add(testNode1);
		nodes.add(testNode2);
		nodes.add(testNode3);
		nodes.add(testNode4);
		nodes.add(testNode5);
		nodes.add(testNode6);
		

		
		edges.add(testEdge12);
		edges.add(testEdge16);
		edges.add(testEdge13);
		edges.add(testEdge23);
		edges.add(testEdge24);
		edges.add(testEdge36);
		edges.add(testEdge45);
		edges.add(testEdge56);
		edges.add(testEdge34);

	}
	
	public void updateMapXML() {
		try {
			
			this.writeEdges(edges, "AllEdges.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * write all of the edges to the XML file for retrieval with read
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
		for (int i = 0; i < edges.size(); i++) {
			edges.get(i).addEdgeLineToDraw();
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
			output.put(edges.get(i).getN1(), edges.get(i).getN1().getEdges());
			output.put(edges.get(i).getN2(), edges.get(i).getN2().getEdges());

		}
		
		Set<Dijstra.Node> keys = output.keySet();

		
		
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