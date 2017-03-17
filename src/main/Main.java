package main;

import java.io.IOException;

public class Main {
	public static Integer startdate = 19990101;
	public static String driverPath = "F:/Users/Naveen/Downloads/";
	public static String downloadLocation = "F:/Users/Naveen/Downloads/";
	public static String outLocationIndex ="F:/Users/Naveen/Desktop/kg/NSE Data/Index Data";
	public static String outLocationRatio ="F:/Users/Naveen/Desktop/kg/NSE Data/Ratio Data";
	public static String outLocationTRI ="F:/Users/Naveen/Desktop/kg/NSE Data/TRI Data";
	public static void main(String[] args) throws InterruptedException, IOException {
		NSEScrapper nseScrapper = new NSEScrapper(driverPath);
		nseScrapper.ScrapHistoricalNSEData(startdate, driverPath,
				downloadLocation, outLocationRatio, 1);
//		nseScrapper.scrapAllIndicesRatios(startdate, driverPath,
//				downloadLocation, outLocationRatio);
//		nseScrapper.ScrapHistoricalVXData(startdate, driverPath,
//				downloadLocation, outLocationIndex);
		nseScrapper.ScrapHistoricalNSEDataTRI(startdate, driverPath,
		downloadLocation, outLocationTRI, 1);
		nseScrapper.close();
	}

}
