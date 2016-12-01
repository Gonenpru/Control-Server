package functions;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONExport {

	public void CrunchifyJSONFileWrite(){


		JSONObject obj = new JSONObject();
		obj.put("Name", "crunchify.com");
		obj.put("Author", "App Shah");

		JSONArray company = new JSONArray();
		company.add("Compnay: eBay");
		company.add("Compnay: Paypal");
		company.add("Compnay: Google");
		obj.put("Company List", company);

		try {
			FileWriter file = new FileWriter("/file1.json");
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}catch (IOException e) {
			// TODO: handle exception
		}
	}
}


