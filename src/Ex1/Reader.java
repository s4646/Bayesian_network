package Ex1;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Reader {
	
	/**
	 * Given xmlFile and a Network, set the Network's basic BNodes
	 * @param xmlFile
	 * @param network
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void setNetworkNodes(File xmlFile, Network network) throws SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    
	    NodeList nList = doc.getElementsByTagName("NAME");
	    BNode[] net = new BNode[nList.getLength()];
	    
	    for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            net[i] = new BNode(nNode.getTextContent(), "", nList.getLength());
        }
	    network.setNodes(net);
	}
	/**
	 * Given xmlFile and a network, set the network's connections of its BNodes
	 * @param xmlFile
	 * @param network
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void setNetworkConnections(File xmlFile, Network network) throws SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
		
		NodeList nList = doc.getElementsByTagName("DEFINITION");
		   
	    for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            
            String s = nNode.getTextContent(); // get content of "DEFINITION"
            
            String[] test = s.split("\n"); // split string by '\n'
            
            BNode n = network.getNode(test[1].split("\t")[1]); // get Node from "FOR"
            
            BNode[] fathers = new BNode[test.length-2]; // set fathers array
            
            for (int j = 1; j < fathers.length; j++) {
				fathers[j-1]=network.getNode(test[j+1].split("\t")[1]);
			}
            
            n.setFathers(fathers); // set fathers of BNode
	    }
	    
	    BNode[] net = network.getNetwork();
        for (int j = 0; j < net.length; j++) {
			BNode bnode = network.getNetwork()[j]; // for each BNode, set the BNode as its father's kid
			
			BNode[] tmp = bnode.getFathers();
			
			for (int k = 0; k < tmp.length; k++) {
				if(tmp[k]==null)
					continue;
				else tmp[k].setKid(bnode);
			}
		}
	}
	/**
	 * Given mxlFile and a network, set the network's probabilities.
	 * @param xmlFile
	 * @param network
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void setNetworkProbas(File xmlFile, Network network) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    
	    NodeList nList1 = doc.getElementsByTagName("TABLE");
	    NodeList nList2 = doc.getElementsByTagName("NAME");
	    
	    for (int i = 0; i < nList1.getLength(); i++) {
            Node nNode1 = nList1.item(i);
            Node nNode2 = nList2.item(i);
            
            String s = nNode1.getTextContent();
            //System.out.println(s);
            String[] test = s.split(" ");
            //System.out.println(test.length);
            
            network.getNode(nNode2.getTextContent()).setProbs(s);
            network.getNode(nNode2.getTextContent()).setProbasTable(s, test.length);
	    }
	}
	
	
	public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException {
		
		File xmlFile = new File("data/alarm_net.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    
	    NodeList nList = doc.getElementsByTagName("NAME");
	    Network network = new Network(nList.getLength());
	    
	    setNetworkNodes(xmlFile, network);
	    
	    setNetworkConnections(xmlFile, network);
	    
	    setNetworkProbas(xmlFile, network);
	    
//	    for (int i = 0; i < network.getNetwork().length; i++) {
//			BNode n = network.getNetwork()[i];
//			System.out.println();
//		}
	    System.out.println(network);
	}	    
}