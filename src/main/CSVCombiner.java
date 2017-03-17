package main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import csv.CSVReader;
import csv.CSVWriter;

public class CSVCombiner {
	private int noOfFiles;
	private String source;
	private String destination;
	private String fileName;
	public CSVCombiner(int count, String source, String dest, String fileName){
		this.noOfFiles = count;
		this.source = source;
		this.destination = dest;
		this.fileName = fileName;
	}

	public void combineSameName(String sameFileName) throws IOException{
		File folder = new File(source);
		File[] listOfFiles = folder.listFiles();
		int max = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getName();

			if(fileName.length()>(4+sameFileName.length()) && fileName.substring(0,sameFileName.length()).equals(sameFileName)&& fileName.substring(fileName.length()-4,fileName.length()).equals(".csv")){
				String[] splitFileName = fileName.split("-");
				if(splitFileName.length==1){
					//do nothing
				}else{
					int val = Integer.parseInt(splitFileName[1].substring(0, splitFileName[1].length()-4));
					if(val>max){
						max = val;
					}
				}
			}
		}
		
		CSVWriter writer = new CSVWriter(destination+File.separator+fileName, false, ",");
		CSVReader reader;
		String filesuffix = "";
		for(int i = 0; i<noOfFiles; i++){
				filesuffix = "-"+(max-i);
			reader = new CSVReader(source+File.separator+sameFileName+filesuffix+".csv", ',', 0);
			//Handling the header
			if(i==0){
				writer.writeLine(reader.readLine());
			}else{
				reader.readLine();
			}
			String[] line;
			while((line=reader.readLineAsArray())!=null)
				writer.writeLine(line);
			reader.close();
		}
		writer.close();
	}
	
	public void sortCSV(String source, Boolean isquotes, Boolean isDateFirst, String dateFormat) throws IOException{
		CSVWriter writer = new CSVWriter(source+"temp", false, ",");
		CSVReader reader = new CSVReader(source, ',',0);
		writer.writeLine(reader.readLine());
		TreeMap<Integer, ArrayList<String>> csvMap = new TreeMap<>();
		String[] line;
		while((line=reader.readLineAsArray())!=null){ 
			Integer key=0;
			 try {
				key = getInteger(line[0], isquotes, isDateFirst, dateFormat);
			} catch (java.text.ParseException e) {
				System.err.println("Error in parsing "+ line[0]+
						" with quotes "+ isquotes+ " with dateFirst "+ isDateFirst + " dateFormat "+ dateFormat);
				e.printStackTrace();
			}
			 ArrayList<String> mapValue = new ArrayList<>();
			 for(int i = 1; i<line.length; i++){
				 if(isquotes)
				 mapValue.add(line[i].substring(1, line[i].length()-1));
				 else  mapValue.add(line[i]);
			 }
			 csvMap.put(key, mapValue);
		}
		reader.close();
		for(Integer key:csvMap.keySet()){
			writer.write(key+",");
			writer.writeLine(csvMap.get(key));
		}
		writer.close();
		File initialFile = new File(source);
		File tempFile = new File(source+"temp");
		initialFile.delete();
		tempFile.renameTo(initialFile);
	}
	private Integer getInteger(String key, Boolean isquotes, Boolean isDateFirst, String dateFormat) throws java.text.ParseException {
		if(isquotes){
			key = key.substring(1, key.length() -1);
		}
		if(isDateFirst){
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new SimpleDateFormat("dd-MMM-yyyy").parse(key)); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return Integer.parseInt(sdf.format(cal.getTime()));
		}else{
			return Integer.parseInt(key);
		}
	}

	public static void main(String[] args) throws IOException{
		CSVCombiner cc = new CSVCombiner(5,"F:/Users/Naveen/Downloads" , "F:/Users/Naveen/Desktop/kg", "out.csv");
		cc.combineSameName("NIFTY 50");
		cc.sortCSV("F:/Users/Naveen/Desktop/kg/out.csv", true, true, "dd-mmm-yyyy");
	}
}
