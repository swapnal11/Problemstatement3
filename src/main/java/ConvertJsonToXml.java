import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ConvertJsonToXml {

	public static void main(String[] args) throws IOException {

		String xmlFile ="JsonToXml.xml";
		byte[] jsonData = Files.readAllBytes(Paths.get("C:\\Users\\SHNN\\eclipse-workspace\\Assignment3\\src\\main\\java\\reader.json"));

		ObjectMapper objectMapper = new ObjectMapper();
		Employee emp = objectMapper.readValue(jsonData, Employee.class);

		JSONObject json = new JSONObject(emp);

		String xml ="<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+"root" +">" + XML.toString(json) + "</"+"root"+">";

		System.out.println(xml);
		FileWriter fileWriter = new FileWriter(xmlFile);
		fileWriter.write(xml);
		fileWriter.close();

	}

}


