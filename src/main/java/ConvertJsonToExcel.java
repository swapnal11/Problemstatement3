
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJsonToExcel {

	public static void main(String[] args) throws IOException {


		byte[] jsonData = Files.readAllBytes(Paths.get("C:\\Users\\SHNN\\eclipse-workspace\\Assignment3\\src\\main\\java\\reader.json"));
		ObjectMapper objectMapper = new ObjectMapper();

		Employee emp = objectMapper.readValue(jsonData, Employee.class);

		System.out.print("Employee Object\n"+emp);

		if (emp != null) {
			writeExcel(emp); // Method to write data in excel
		} else {
			System.out.println("No data to write in excel, json is null or empty.");
		}
	}

	private static void writeExcel(Employee emp) {

		FileOutputStream fileOutputStream = null;
		StringBuilder sb=null;

		String[] COLUMNs = {"id", "name", "permanent","street","city","zipCode","role","cities","phoneNumbers","age","salary"};

		try {
			String filename = "C:\\Users\\SHNN\\eclipse-workspace\\Assignment3\\JSONInfo.xls";
			Workbook hssfWorkbook = new XSSFWorkbook();
			Sheet hssfSheet = hssfWorkbook.createSheet("new sheet");

			Font headerFont = hssfWorkbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = hssfWorkbook.createCellStyle();
			headerCellStyle.setFont(headerFont);


			Row row = hssfSheet.createRow(0); // Header

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = row.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			Row rowhead = hssfSheet.createRow(1);
			rowhead.createCell(0).setCellValue(emp.getId());		   
			rowhead.createCell(1).setCellValue(emp.getName());
			rowhead.createCell(2).setCellValue(emp.isPermanent());
			rowhead.createCell(3).setCellValue(emp.getAddress().getStreet());
			rowhead.createCell(4).setCellValue(emp.getAddress().getCity());
			rowhead.createCell(5).setCellValue(emp.getAddress().getZipcode());
			rowhead.createCell(6).setCellValue(emp.getRole());

			sb = new StringBuilder("");			
			for (String cities : emp.getCities()) {
				sb.append(cities+",");				
			}
			rowhead.createCell(7).setCellValue(sb.toString());

			StringBuilder sb1 = new StringBuilder("");			
			for (long PhoneNo : emp.getPhoneNumbers()) {
				sb1.append(PhoneNo+",");				
			}
			rowhead.createCell(8).setCellValue(sb1.toString());
			rowhead.createCell(9).setCellValue(emp.getProperties().get("age"));
			rowhead.createCell(10).setCellValue(emp.getProperties().get("salary"));
			fileOutputStream = new FileOutputStream(filename);
			hssfWorkbook.write(fileOutputStream);
			fileOutputStream.close();
			System.out.println("JSON data successfully exported to excel!");
		} catch (Throwable throwable) {
			System.out.println("Exception in writting data to excel : "
					+ throwable);
		}
	}
}
