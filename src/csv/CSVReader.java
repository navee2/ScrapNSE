package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	// Varaibles
	BufferedReader reader;
	char splitChar;

	// Constructor
	public CSVReader(String fileName, char splitChar, int startIdx)
			throws IOException {
		reader = new BufferedReader(new FileReader(fileName));
		this.splitChar = splitChar;
		for (int i = 0; i < startIdx; i++)
			reader.readLine();
	}

	// Read Line
	public ArrayList<String> readLine() throws IOException {
		String line = reader.readLine();
		if (line == null)
			return null;
		return split(line, splitChar);
	}

	// Read Line
	public String[] readLineAsArray() throws IOException {
		String line = reader.readLine();
		if (line == null)
			return null;
		return splitAsArray(line, splitChar);
	}

	// Read Line Normal
	public String readLineAsString() throws IOException {
		String line = reader.readLine();
		return line;
	}

	// Read All Lines
	public ArrayList<String[]> readAll() throws IOException {
		String[] line;
		ArrayList<String[]> allLines = new ArrayList<String[]>();
		while ((line = readLineAsArray()) != null)
			allLines.add(line);
		return allLines;
	}

	// Close Buffered Reader
	public void close() throws IOException {
		reader.close();
	}

	// Optimized String Splitter
	public static ArrayList<String> split(String line, char splitChar) {
		ArrayList<String> str = new ArrayList<String>();
		int index = line.indexOf(splitChar);
		int prevIndex = index;
		if (!(index < 0))
			str.add(line.substring(0, index));
		while (true) {
			index = line.indexOf(splitChar, index + 1);
			if (index < 0)
				break;
			str.add(line.substring(prevIndex + 1, index));
			prevIndex = index;
		}
		str.add(line.substring(prevIndex + 1));
		return str;
	}

	// Optimized String Splitter
	public static String[] splitAsArray(String line, char splitChar) {
		ArrayList<String> str = new ArrayList<String>();
		int index = line.indexOf(splitChar);
		int prevIndex = index;
		if (!(index < 0))
			str.add(line.substring(0, index));
		while (true) {
			index = line.indexOf(splitChar, index + 1);
			if (index < 0)
				break;
			str.add(line.substring(prevIndex + 1, index));
			prevIndex = index;
		}
		str.add(line.substring(prevIndex + 1));

		String[] outArray = new String[str.size()];
		for (int i = 0; i < str.size(); i++)
			outArray[i] = str.get(i);

		return outArray;
	}
	
	public void mark() throws IOException{
		reader.mark(1000);
	}
	public void reset() throws IOException{
		reader.reset();
	}
}
