
import org.json.JSONException; 
import org.json.JSONObject;
import java.util.*;
import java.io.File;
import java.io.FileWriter;

public class MergeJSON {
 
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		String folder = s.next();
		String prefix = s.next();
		String outPrefix = s.next();
		int max = s.nextInt();
		
		int suffix = 1;

		File outFile = new File(outPrefix + suffix + ".json");

		File fold = new File(folder);
		File[] files = fold.listFiles();
		Arrays.sort(files);

		int extras = 0;

		JSONObject json = new JSONObject();		
	
		for(int i=0 ; i<files.length; i++)
		{
			File f = files[i];
			if(!f.getName().startsWith(prefix)) { extras++; continue; }
			Scanner in=new Scanner(f);
			in.useDelimiter("\\Z");
			JSONObject j = new JSONObject(in.next());
			JSONObject t = mergeJSONObjects(json, j);
			if(t.toString().length() > max) {
				outFile = new File(outPrefix + suffix + ".json");
				suffix++;		
				FileWriter fw = new FileWriter(outFile);
				fw.write(t.toString());
				fw.close();
				json = new JSONObject();
			} else {
				json = t;
				if(i==files.length-extras) {
					FileWriter fw = new FileWriter(outFile);
					fw.write(json.toString());
					fw.close();
				}
			}
	
		}


	}
 
	public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
		JSONObject mergedJSON = new JSONObject();
		try {
			if(JSONObject.getNames(json1) == null) return json2;

			mergedJSON = new JSONObject(json1, JSONObject.getNames(json1) );

			for (String key : JSONObject.getNames(json2)) {
				mergedJSON.put(key, json2.get(key));
			}
 
		} catch (JSONException e) {
			throw new RuntimeException("JSON Exception" + e);
		}
		return mergedJSON;
	}
}