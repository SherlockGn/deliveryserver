package com.gth.delivery.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMapGenerator {

	private final String rootPath = "E:\\deliveryserver\\delivery\\src\\main\\java\\com\\gth\\delivery\\controller\\";
	private final String[] fileNames = new String[] { "UserController.java", "CourierController.java",
			"FriendController.java", "IndentController.java", "ScanController.java", "TestController.java" };

	Map<String, List<String>> map;

	public void generateMapText() throws Exception {

		map = new HashMap<String, List<String>>();

		for (String fileName : fileNames) {
			File f = new File(rootPath + fileName);
			if (!f.exists())
				continue;

			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null, methodName = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("public") && !line.startsWith("public class")) {
					int left = line.indexOf('(');
					int i;
					for (i = left - 1; i >= 0; i--)
						if (line.charAt(i) == ' ')
							break;
					methodName = line.substring(i + 1, left);
					map.put(methodName, new ArrayList<String>());
					System.out.println("map.put(\"" + methodName + "\", new ArrayList<String>());");
				}
				if (line.startsWith("@RequestParam")) {
					String tp = line.substring(line.indexOf("String")).split(" ")[1];
					int j;
					for (j = 0; j < tp.length(); j++)
						if (!Character.isAlphabetic(tp.charAt(j)) && !Character.isDigit(tp.charAt(j)))
							break;
					tp = tp.substring(0, j);
					map.get(methodName).add(tp);
					System.out.println("map.get(\"" + methodName + "\").add(\"" + tp + "\");");
				}
			}
			br.close();
		}

		System.out.println(map);
	}

	public void jsOutput() {
		boolean rootFirst = true;
		System.out.println("{");
		for (String key : map.keySet()) {
			if (!rootFirst)
				System.out.println(",");
			System.out.print(key + ": [");
			boolean first = true;
			for (String element : map.get(key)) {
				if (!first)
					System.out.print(", ");
				System.out.print("\"" + element + "\"");
				first = false;
			}
			System.out.print("]");
			rootFirst = false;
		}
		System.out.println("}");
	}

	public static void main(String[] args) {
		try {
			FileMapGenerator fmg = new FileMapGenerator();
			fmg.generateMapText();
			fmg.jsOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
