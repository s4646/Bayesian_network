import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Ex1 {

	
	public static void WriteBB(BayesBall b, File input, File output, FileOutputStream ops) throws IOException {
		String res = b.readQueries(Reader.readInputBB(input));
		byte[] strToBytes = res.getBytes();
		ops.write(strToBytes);
		System.out.print("Bayes Ball content written successfully\n");
	}
	public static void WriteVE(VariableElimination ve, File input, File output, FileOutputStream ops) throws IOException {
		String res = ve.readQueries(Reader.readInputVE(input));
		byte[] strToBytes = res.getBytes();
		ops.write(strToBytes);
		System.out.print("Variable Elimination content written successfully\n");
	}
	
	public static void main(String[] args) throws IOException, SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub
		File input = new File("input.txt");
	    File output = new File("output.txt");
	    if (output.createNewFile())
	    	System.out.println("Output file created: " + output.getName()); 
	    else
	    	System.out.println("Output file already exists.");
	    
	    FileOutputStream outputStream = new FileOutputStream(output);
	    
	    Scanner sc = new Scanner(input);
	    File xmlFile = new File(sc.next());
	    sc.close();
	    
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = factory.newDocumentBuilder();
	    Document doc = dBuilder.parse(xmlFile);
	    
	    NodeList nList = doc.getElementsByTagName("NAME");
	    Network network = new Network(nList.getLength());
	    
	    Reader.setNetworkNodes(xmlFile, network);
	    Reader.setNetworkConnections(xmlFile, network);
	    Reader.setNetworkValues(xmlFile, network);
	    Reader.setNetworkProbas(xmlFile, network);
	    System.out.println(network);
	    
	    BayesBall b = new BayesBall(network);
	    WriteBB(b, input, output, outputStream);
	    VariableElimination ve = new VariableElimination(network);
	    ve.setFactors();
	    WriteVE(ve, input, output,outputStream);
	    outputStream.close();
	}
}
