package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData1.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];//created for two dimension array which can store the data user and password
		
		for(int i=1;i<=totalrows;i++)  //1   //read the data from xl storing in two dimensional array
		{		
			for(int j=0;j<totalcols;j++)  //0    i is rows j is col
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  //1,0  i-1 because we minus header and store data
			}                                                              //getting data 1 index store in 0 index  //getting 2 in. store 1 in.
		}
	return logindata;//returning two dimension array
				
	}
	
	//DataProvider 2
	/*
	if we want to test another data with different data for different test cases
	same copy dataprovider1 just change method name(getData()  above method name ) and Data provider name("Login data")
	*/
	//DataProvider 3
	
	//DataProvider 4
}
