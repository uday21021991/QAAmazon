package main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsReader {
	
	public static String filename = System.getProperty("user.dir") + "\\src\\config\\testcases\\TestData.xlsx";
	public String path;

	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * This method is used to read the excel data and storing in hash map
	 * @param Excelpath : We have to pass the path of the excelsheet
	 * @param sheetname : We have to pass the sheet name which sheet data we have to read
	 * @param rowNum : to get the particular row data
	 * @throws IOException
	 */
	public XlsReader(String Excelpath, String sheetname, int rowNum) throws IOException {
		try {
			FileInputStream fs = new FileInputStream(new File(Excelpath));
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			String title = "";
			String value = "";

			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				title = sheet.getRow(0).getCell(j).getStringCellValue();
				value = sheet.getRow(rowNum).getCell(j).getStringCellValue();
				map.put(title, value);
			}
		} catch (Exception e) {
			System.out.println("Error during reading input file");
			throw new Error(e.getStackTrace().toString());
		}
	}
	
	/**
	 * This method for initiating the excel workbook and sheet
	 * @param path
	 */
	public XlsReader(String path) {

		this.path = path;
		try {

			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Map<String, String> hashmap() {
		return map;
	}
	
	
		/**
		 * This method for set the cell data
		 */
		public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
			try {

				fis = new FileInputStream(path);

				workbook = new XSSFWorkbook(fis);

				if (rowNum <= 0)
					return false;

				int index = workbook.getSheetIndex(sheetName);
				int colNum = -1;
				if (index == -1)
					return false;

				sheet = workbook.getSheetAt(index);

				row = sheet.getRow(0);
				for (int i = 0; i < row.getLastCellNum(); i++) {
					// System.out.println(row.getCell(i).getStringCellValue().trim());
					if (row.getCell(i).getStringCellValue().trim().equals(colName))
						colNum = i;
				}
				if (colNum == -1)
					return false;

				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(rowNum - 1);
				if (row == null)
					row = sheet.createRow(rowNum - 1);

				cell = row.getCell(colNum);
				if (cell == null)
					cell = row.createCell(colNum);

				// cell style
				CellStyle cs = workbook.createCellStyle();
				cs.setWrapText(true);
				cell.setCellStyle(cs);

				cell.setCellValue(data);

				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);

				fileOut.close();

			} catch (Exception e) {

				e.printStackTrace();
				return false;

			}
			return true;
		}

		
		/**
		 * This method to get the total rows count
		 * @param sheetName
		 * @return
		 */
		public int getRowCount(String sheetName) {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return 0;
			else {
				sheet = workbook.getSheetAt(index);
				int number = sheet.getLastRowNum() + 1;
				return number;
			}

		}
		
		
		
		/**
		 * This method to get the cell data
		 * @param sheetName
		 * @return
		 */
		public String getCellData(String sheetName, String colName, int rowNum) {
			try {
				if (rowNum <= 0)
					return "";

				int index = workbook.getSheetIndex(sheetName);
				int col_Num = -1;
				if (index == -1)
					return "";

				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(0);
				for (int i = 0; i < row.getLastCellNum(); i++) {
					// System.out.println(row.getCell(i).getStringCellValue().trim());
					if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
						col_Num = i;
				}
				if (col_Num == -1)
					return "";

				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(rowNum - 1);
				if (row == null)
					return "";
				cell = row.getCell(col_Num);

				if (cell == null)
					return "";
				// System.out.println(cell.getCellType());
				if (cell.getCellType() == Cell.CELL_TYPE_STRING)
					return cell.getStringCellValue();
				else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

					String cellText = String.valueOf(cell.getNumericCellValue());
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						// format in form of M/D/YY
						double d = cell.getNumericCellValue();

						Calendar cal = Calendar.getInstance();
						cal.setTime(HSSFDateUtil.getJavaDate(d));
						cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
						cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

						// System.out.println(cellText);

					}

					return cellText;
				} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
					return "";
				else
					return String.valueOf(cell.getBooleanCellValue());

			} catch (Exception e) {

				e.printStackTrace();
				return "row " + rowNum + " or column " + colName + " does not exist in xls";
			}
		}
}
