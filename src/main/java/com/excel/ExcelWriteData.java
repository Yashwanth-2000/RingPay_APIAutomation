package com.excel;

import java.io.FileOutputStream;
import java.io.IOException;

	import java.io.File;
	import java.io.FileInputStream;
	 
	import org.apache.poi.ss.usermodel.CellType;
	import org.apache.poi.xssf.usermodel.XSSFCell;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.response.ValidatableResponse;
	 
	 
	public class ExcelWriteData {
		
			public static void excelWrite(String xlsPath,String sSheetName ,String sData,int nRow,int nCell) throws IOException {

	        try {
	 
	            // Create an object of FileInputStream class to read excel file
	            FileInputStream fis = new FileInputStream(new File(xlsPath));
	 
	            // Create object of XSSFWorkbook class
	            XSSFWorkbook workbook = new XSSFWorkbook(fis);
	 
	            // Read excel sheet by sheet name
	            XSSFSheet sheet = workbook.getSheet(sSheetName);
	 
	            // Print data present at row 0 column 2
//	            System.out.println(sheet.getRow(nRow).getCell(nCell).getCellValue());
	 
	            // Get the Cell at index 3 from the above row
	            XSSFCell cell = sheet.getRow(nRow).getCell(nCell);
	 
	            cell.setCellType(CellType.STRING);
	            cell.setCellValue(sData);
	 
	            // Write the output to the file
	            FileOutputStream fileOut = new FileOutputStream(new File(xlsPath));
	            workbook.write(fileOut);
	 
	            System.out.println("Write Excel is updated successfully");
	            fileOut.close();
	 
	            // Closing the workbook
	            workbook.close();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	    }
			
			
			public static void IntegerExcelWrite(String xlsPath,String sSheetName ,Integer sData,int nRow,int nCell) throws IOException {

		        try {
		 
		            // Create an object of FileInputStream class to read excel file
		            FileInputStream fis = new FileInputStream(new File(xlsPath));
		 
		            // Create object of XSSFWorkbook class
		            XSSFWorkbook workbook = new XSSFWorkbook(fis);
		 
		            // Read excel sheet by sheet name
		            XSSFSheet sheet = workbook.getSheet(sSheetName);
		 
		            // Print data present at row 0 column 2
//		            System.out.println(sheet.getRow(nRow).getCell(nCell).getCellValue());
		 
		            // Get the Cell at index 3 from the above row
		            XSSFCell cell = sheet.getRow(nRow).getCell(nCell);
		 
		            cell.setCellType(CellType.STRING);
		            cell.setCellValue(sData);
		 
		            // Write the output to the file
		            FileOutputStream fileOut = new FileOutputStream(new File(xlsPath));
		            workbook.write(fileOut);
		 
		            System.out.println("Write Excel is updated successfully");
		            fileOut.close();
		 
		            // Closing the workbook
		            workbook.close();
		 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		 
		    }		
	
	
	}

	

