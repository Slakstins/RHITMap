import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;



public class XMLFileWriter {

    public static void main (String [] args) throws Exception{
    	ArrayList<Dijstra.Edge> edges = new ArrayList<Dijstra.Edge>();
    	ArrayList<Dijstra.Node> nodes = new ArrayList<Dijstra.Node>();


        


    	Dijstra.Edge testEdge1 = new Dijstra.Edge();
    	testEdge1.setCost(5);
    	testEdge1.setOutside(true);
        edges.add(testEdge1);

        Dijstra.Node testNode1 = new Dijstra.Node();
        testNode1.setX(69);
        testNode1.setY(69);
        testNode1.setEdges(edges);
    	
        Dijstra.Node testNode2 = new Dijstra.Node();
        testNode2.setY(420);
        testNode2.setY(420);
        
        
        
        
        
        
        testNode1.setEdges(edges);
        testNode2.setEdges(edges);
        
        nodes.add(testNode2);
        nodes.add(testNode1);
        
        
        
        testEdge1.setStartNode(testNode1);
        
        testEdge1.setEndNode(testNode2);
        
        
        
   
    	
    	



    	writeEdges(edges, "foo.xml");
    
        Dijstra.Edge l2 = read("foo.xml");
        System.out.println(l2.getStartNode().getEdges().get(0).getStartNode().getX());

    	
    }
    

    
    public static void writeEdges(ArrayList<Dijstra.Edge> E, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
       	for (int i = 0; i < E.size(); i++) {
       		encoder.writeObject(E.get(i));
       	}
        encoder.close();
    }
    


    public static Dijstra.Edge read(String filename) throws Exception {
        XMLDecoder decoder = 
        	new XMLDecoder(
        			new BufferedInputStream(
        					new FileInputStream(filename)));
        
        
        
        Dijstra.Edge E = (Dijstra.Edge) decoder.readObject();
        decoder.close();
        return E;
    }



}