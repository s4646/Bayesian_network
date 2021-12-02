package Ex1;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

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
            network.getNode(nNode2.getTextContent()).setCPT(s);
	    }
	}
	/**
	 * Given mxlFile and a network, set the network's nodes' values.
	 * @param xmlFile
	 * @param network
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void setNetworkValues(File xmlFile, Network network) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    
	    NodeList nList = doc.getElementsByTagName("VARIABLE");
	    
	    for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            String s = nNode.getTextContent();
            String[] test = s.split("\n");
            for (int j = 0; j < test.length; j++) {
            	network.getNode(test[1].split("\t")[1]).setValues(test);
            }
	    }
	}
//	public static void setNodesNumOfBools(File xmlFile, Network network) throws SAXException, IOException, ParserConfigurationException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
//	    Document doc = dBuilder.parse(xmlFile);
//	    NodeList nList = doc.getElementsByTagName("VARIABLE");
//	    
//	    for (int i = 0; i < nList.getLength(); i++) {
//            Node nNode = nList.item(i);
//            
//            
//	    }
//	}
	
	/**
	 * Read queries from input file for Bayes Ball.
	 * @return queries Array of Strings
	 * @throws FileNotFoundException
	 */
	public static String[] readInputBB() throws FileNotFoundException{
		File f = new File("data/input.txt");
		Scanner sc = new Scanner(f);
		String s = "";
		int len = 0;
		while (sc.hasNextLine()) {
			s = sc.nextLine();
	    	if(s.contains("|") && !s.contains("P("))
	    		len++;
	    }
		
		sc.close();
		sc = new Scanner(f);
		s = "";
		String[] queries = new String[len];
		
		for (int i = 0; sc.hasNextLine();) {
			s = sc.nextLine();
	    	if(s.contains("|") && !s.contains("P(")) {
	    		queries[i]=s;
	    		i++;
	    	}
	    }
		sc.close();
		return queries;
	    
	}
	/**
	 * Read queries from input file for Variable Elimination.
	 * @return queries Array of Strings
	 * @throws FileNotFoundException
	 */
	public static String[] readInputVE() throws FileNotFoundException{
		File f = new File("data/input.txt");
		Scanner sc = new Scanner(f);
		String s = "";
		int len = 0;
		while (sc.hasNextLine()) {
			s = sc.nextLine();
	    	if(s.contains("|") && s.contains("P("))
	    		len++;
	    }
		
		sc.close();
		sc = new Scanner(f);
		s = "";
		String[] queries = new String[len];
		
		for (int i = 0; sc.hasNextLine();) {
			s = sc.nextLine();
	    	if(s.contains("|") && s.contains("P(")) {
	    		queries[i]=s;
	    		i++;
	    	}
	    }
		sc.close();
		return queries;
	    
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
	    
	    setNetworkValues(xmlFile, network);

	    setNetworkProbas(xmlFile, network);
	    
	    
	    System.out.println(network);
	    
	    //BNode n = network.getNode("A");
	    //Factor a = new Factor(network.getNode("J"));
	    //a.setVariables();
	    //a.setFactorBooleans();
	    //Utils.printBooleans(a.getFactorBooleans());
	    //Utils.printDoubleArr(a.getFactorProbas());
	    //Utils.printTogether(a.getFactorBooleans(), a.getFactorProbas());
	    //VariableElimination ve = new VariableElimination(network);
	    //ve.setFactors();
	    //ve.readQueries(readInputVE());
	    //Factor[] f = ve.getFactors();
	    //for (int i = 0; i < f.length; i++) {
//	    	Utils.printHashMapArray(f[i].getTable());
//	    	System.out.println("***");
//		}
	    //BNode b = network.getNode("J");
	    //Utils.printHashMapArray(b.getCPT());
	    //System.out.println(b.getCPT().get(0).keySet());
	    //Utils.printHashMapArray(a.getTable());
	    //Variable[] v = {new Variable(network.getNode("E")),new Variable(network.getNode("B")),new Variable(network.getNode("A"))};
//	    Factor x = f[2];
//	    Utils.printHashMapArray(x.getTable());
//	    ve.eliminate(x, new Variable(network.getNode("A")));
//	    ve.normalise(x);
//	    Utils.printHashMapArray(x.getTable());
	}	    
}