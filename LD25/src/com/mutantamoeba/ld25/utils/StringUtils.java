package com.mutantamoeba.ld25.utils;

import java.io.File;
import java.util.Properties;
import java.util.Map.Entry;

public class StringUtils {
	static public String repeat(char c, int count) {
		String res = "";
		
		for (int i=0;i<count;i++) {
			res += c;
		}
		return res;
	}
	
	public static String join(Object o[], String sep) {
	    String res = "";
	    for (int i=0; i<o.length; i++) {
	        res += o[i].toString();
	        if (i < o.length-1) {
	            res += sep;
	        }
	    }
	    return res;
	}

	public static String join(Object o[], String sep, int spacing) {
	    String res = "";
	    for (int i=0; i<o.length; i++) {
	        res += String.format("%" + Integer.toString(spacing) + "s", o[i].toString());
	        if (i < o.length-1) {
	            res += sep;
	        }
	    }
	    return res;
	}
	public static String herePath(String fname) {
		Properties props = System.getProperties();
		
		for (Entry<Object, Object> prop:props.entrySet()) {
			String propName = (String)prop.getKey();
			Object propVal = prop.getValue();
			System.out.println(propName + ":" + propVal);
		}
//		String currentDir = new File(System.getProperty("user.dir"), fname).getAbsolutePath();
		File file = new File(System.getProperty("user.home"), "Library" + File.separator + 
																"Application Support" + File.separator + 
																"Mutant Amoeba/");
		file.mkdirs();
		file = new File(file.getAbsolutePath(), fname);
		String currentDir = file.getAbsolutePath();
		
		System.out.println(currentDir);
		return currentDir;
	}
}
