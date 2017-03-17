package main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class NSEScrapper {
;
	public  WebDriver driver;
	public NSEScrapper(String driverPath){
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		driver = new ChromeDriver(options);
	}
	
//	
	public void ScrapHistoricalNSEData(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation, Integer index) throws InterruptedException, IOException {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_index_data.htm");
		Thread.sleep(5000);  // Give it time to load
		WebElement dropDown = driver.findElement(By.id("indexType"));
		Select dropdown= new Select(dropDown);
		dropdown.selectByIndex(index);
		String indexName = dropdown.getFirstSelectedOption().getAttribute("value");
		String outFileName = indexName+".csv";
		WebElement searchBox = driver.findElement(By.id("fromDate"));
		
		WebElement searchBox2 = driver.findElement(By.id("toDate"));
		WebElement searchBox3 = driver.findElement(By.id("get"));
		//Loop from startDate to End Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Integer yyyy = startdate/10000;
		Integer mm = (startdate%10000)/100-1;
		Integer dd = (startdate%100);
		calendar.set(yyyy	, mm, dd);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		Integer count = 0;
		while(true){
			searchBox.clear();
			searchBox.sendKeys(sm.format(calendar.getTime()));
			searchBox2.clear();
			calendar.add(Calendar.DAY_OF_YEAR, 364);
			if(calendar.compareTo(calendar2)>=0){
				calendar.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DATE));
			}
			searchBox2.sendKeys(sm.format(calendar.getTime()));
			searchBox3.click();
			Thread.sleep(5000);  // Let the user actually see something!
			try{
			WebElement searchBox4 =driver.findElement(By.xpath("//a[contains(text(),'Download file in csv format')]"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView()", searchBox4); 
			searchBox4.click();
			Thread.sleep(5000); 
			File finalFileLocation = new File(downloadLocation+File.separator+indexName+"-"+count+".csv");
			File lastdownLoadedFile = getLatestFilefromDir(downloadLocation);
			lastdownLoadedFile.renameTo(finalFileLocation);
			count++;}
			catch(Exception e){
				//Handle if no data available simply move on
				//To do nothing donot increase no of files
				
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(calendar.compareTo(calendar2)>0){
				break;
			}
		}
		CSVCombiner cc = new CSVCombiner(count,downloadLocation ,outLocation , outFileName);
		cc.combineSameName(indexName);
		cc.sortCSV(outLocation+File.separator+outFileName, true, true, "dd-mmm-yyyy");
	}
	
	public void ScrapHistoricalVXData(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation) throws InterruptedException, IOException {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_vix.htm");
		Thread.sleep(5000);  // Give it time to load
		String indexName = "INDIAVIX";
		String outFileName = indexName+".csv";
		WebElement searchBox = driver.findElement(By.id("fromDate"));
		WebElement searchBox2 = driver.findElement(By.id("toDate"));
		WebElement searchBox3 = driver.findElement(By.id("getButton"));
		//Loop from startDate to End Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Integer yyyy = startdate/10000;
		Integer mm = (startdate%10000)/100-1;
		Integer dd = (startdate%100);
		calendar.set(yyyy	, mm, dd);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		Integer count = 0;
		while(true){
			searchBox.clear();
			searchBox.sendKeys(sm.format(calendar.getTime()));
			searchBox2.clear();
			calendar.add(Calendar.DAY_OF_YEAR, 364);
			if(calendar.compareTo(calendar2)>=0){
				calendar.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DATE));
			}
			searchBox2.sendKeys(sm.format(calendar.getTime()));
			searchBox3.click();
			Thread.sleep(5000);  // Let the user actually see something!
			try{
			WebElement searchBox4 =driver.findElement(By.xpath("//a[contains(text(),'Download file in csv format')]"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView()", searchBox4); 
			searchBox4.click();
			
			//Have a long wait
			Thread.sleep(60000); 
			File finalFileLocation = new File(downloadLocation+File.separator+indexName+"-"+count+".csv");
			File lastdownLoadedFile = getLatestFilefromDir(downloadLocation);
			lastdownLoadedFile.renameTo(finalFileLocation);
			count++;}
			catch(Exception e){
				//Handle if no data available simply move on
				//To do nothing donot increase no of files
				
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(calendar.compareTo(calendar2)>0){
				break;
			}
		}
		CSVCombiner cc = new CSVCombiner(count,downloadLocation ,outLocation , outFileName);
		cc.combineSameName(indexName);
		cc.sortCSV(outLocation+File.separator+outFileName, true, true, "dd-mmm-yyyy");
	}
	
	
	public void ScrapHistoricalNSEDataTRI(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation, Integer index) throws InterruptedException, IOException {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_total_return.htm");
		Thread.sleep(5000);  // Give it time to load
		WebElement dropDown = driver.findElement(By.id("indexType"));
		Select dropdown= new Select(dropDown);
		dropdown.selectByIndex(index);
		String indexName = dropdown.getFirstSelectedOption().getAttribute("value")+"TRI";
		String outFileName = indexName+".csv";
		WebElement searchBox = driver.findElement(By.id("fromDate"));
		
		WebElement searchBox2 = driver.findElement(By.id("toDate"));
		WebElement searchBox3 = driver.findElement(By.id("get"));
		//Loop from startDate to End Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Integer yyyy = startdate/10000;
		Integer mm = (startdate%10000)/100-1;
		Integer dd = (startdate%100);
		calendar.set(yyyy	, mm, dd);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		Integer count = 0;
		while(true){
			searchBox.clear();
			searchBox.sendKeys(sm.format(calendar.getTime()));
			searchBox2.clear();
			calendar.add(Calendar.DAY_OF_YEAR, 364);
			if(calendar.compareTo(calendar2)>=0){
				calendar.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DATE));
			}
			searchBox2.sendKeys(sm.format(calendar.getTime()));
			searchBox3.click();
			Thread.sleep(5000);  // Let the user actually see something!
			try{
			WebElement searchBox4 =driver.findElement(By.xpath("//a[contains(text(),'Download file in csv format')]"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView()", searchBox4); 
			searchBox4.click();
			Thread.sleep(5000); 
			File finalFileLocation = new File(downloadLocation+File.separator+indexName+"-"+count+".csv");
			File lastdownLoadedFile = getLatestFilefromDir(downloadLocation);
			lastdownLoadedFile.renameTo(finalFileLocation);
			count++;}
			catch(Exception e){
				//Handle if no data available simply move on
				//To do nothing donot increase no of files
				
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(calendar.compareTo(calendar2)>0){
				break;
			}
		}
		CSVCombiner cc = new CSVCombiner(count,downloadLocation ,outLocation , outFileName);
		cc.combineSameName(indexName);
		cc.sortCSV(outLocation+File.separator+outFileName, true, true, "dd-mmm-yyyy");
	}
	
	
	
	
	
	public void ScrapHistoricalNSEDataRatio(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation, Integer index) throws InterruptedException, IOException {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_pepb.htm");
		Thread.sleep(5000);  // Give it time to load
		WebElement dropDown = driver.findElement(By.id("IndexName"));
		Select dropdown= new Select(dropDown);
		dropdown.selectByIndex(index);
		if ( !driver.findElement(By.id("yield4")).isSelected() )
		{
		     driver.findElement(By.id("yield4")).click();
		}
		String indexName = dropdown.getFirstSelectedOption().getAttribute("value");
		String outFileName = indexName+".csv";
		WebElement searchBox = driver.findElement(By.id("fromDate"));
		
		WebElement searchBox2 = driver.findElement(By.id("toDate"));
		WebElement searchBox3 = driver.findElement(By.id("get"));
		//Loop from startDate to End Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Integer yyyy = startdate/10000;
		Integer mm = (startdate%10000)/100-1;
		Integer dd = (startdate%100);
		calendar.set(yyyy	, mm, dd);
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		Integer count = 0;
		while(true){
			searchBox.clear();
			searchBox.sendKeys(sm.format(calendar.getTime()));
			searchBox2.clear();
			calendar.add(Calendar.DAY_OF_YEAR, 364);
			if(calendar.compareTo(calendar2)>=0){
				calendar.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DATE));
			}
			searchBox2.sendKeys(sm.format(calendar.getTime()));
			searchBox3.click();
			Thread.sleep(5000);  // Let the user actually see something!
			try{
			WebElement searchBox4 =driver.findElement(By.xpath("//a[contains(text(),'Download file in csv format')]"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView()", searchBox4); 
			searchBox4.click();
			Thread.sleep(5000); 
			File finalFileLocation = new File(downloadLocation+File.separator+indexName+"Ratios-"+count+".csv");
			File lastdownLoadedFile = getLatestFilefromDir(downloadLocation);
			lastdownLoadedFile.renameTo(finalFileLocation);
			count++;}
			catch(Exception e){
				//Handle if no data available simply move on
				//To do nothing donot increase no of files
				
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(calendar.compareTo(calendar2)>0){
				break;
			}
		}
		CSVCombiner cc = new CSVCombiner(count,downloadLocation ,outLocation , outFileName);
		cc.combineSameName(indexName+"Ratios");
		cc.sortCSV(outLocation+File.separator+outFileName, true, true, "dd-mmm-yyyy");
	}
	
	public void scrapAllIndices(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation) throws InterruptedException, IOException{
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_index_data.htm");
		Thread.sleep(5000);  // Give it time to load
		WebElement dropDown = driver.findElement(By.id("indexType"));
		Select dropdown= new Select(dropDown);
		List<WebElement> l = dropdown.getOptions();
		l.size();
		for(int i = 1; i<l.size(); i++){
			//Names start from location 1
			ScrapHistoricalNSEData(startdate, driverPath,
					downloadLocation, outLocation, i);
		}
	}
	
	public void scrapAllIndicesRatios(Integer startdate, String driverPath, 
			String downloadLocation, String outLocation) throws InterruptedException, IOException{
		driver.get("https://www.nseindia.com/products/content/equities/indices/historical_pepb.htm");
		Thread.sleep(5000);  // Give it time to load
		WebElement dropDown = driver.findElement(By.id("IndexName"));
		Select dropdown= new Select(dropDown);
		List<WebElement> l = dropdown.getOptions();
		l.size();
		for(int i = 0; i<l.size(); i++){
			//Drop down box starts from 0
			ScrapHistoricalNSEDataRatio(startdate, driverPath,
					downloadLocation, outLocation, i);
		}
	}
	private File getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	public void close(){
		driver.quit();
	}
}