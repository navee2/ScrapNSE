package csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

	// Varaibles
	BufferedWriter writer;
	String splitChar;

	// Constructor
	public CSVWriter(String fileName, boolean writeMode, String splitChar)
			throws IOException {
		writer = new BufferedWriter(new FileWriter(fileName, writeMode));
		this.splitChar = splitChar;
	}

	// Write Line
	public void writeLine(String[] line) throws IOException {
		String outLine = line[0];
		for (int i = 1; i < line.length; i++)
			outLine = outLine + splitChar + line[i];
		writer.write(outLine);
		writer.write("\n");
	}
	
	public void write(String str) throws IOException{
		writer.write(str);
	}
	// Close File Writer
	public void close() throws IOException {
		writer.flush();
		writer.close();
	}

	// Close File Writer
	public void flush() throws IOException {
		writer.flush();
	}

	public void writeLine(ArrayList<String> readLine) throws IOException {
		String outLine = "";
		for(String token:readLine){
			if(outLine.equals("")) outLine = token;
			else outLine = outLine + "," + token;
		}
		writer.write(outLine);
		writer.write("\n");
		
	}
}
