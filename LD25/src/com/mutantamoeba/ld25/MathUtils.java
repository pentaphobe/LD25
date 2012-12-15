package com.mutantamoeba.ld25;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class MathUtils {
	static public final float PI = 3.141592653589f;
	static public float map(float in, float rMin, float rMax, float oMin, float oMax) {
		return ( (in-rMin) / (rMax-rMin)) * (oMax-oMin) + oMin;
	}
	static public float min(float a, float b) {
		return (a < b ? a : b);
	}
	static public float max(float a, float b) {
		return (a > b ? a : b);
	}
	static public float constrain(float a, float rMin, float rMax) {
		if (a < rMin)
			return rMin;
		else if (a > rMax)
			return rMax;
		return a;
	}
	static public float frac(float a) {
		return a - (int)a;
	}
	static public double sqrt(final double a) {
	    final long x = Double.doubleToLongBits(a) >> 32;
	    double y = Double.longBitsToDouble((x + 1072632448) << 31);
	 
	    // repeat the following line for more precision
	    y = (y + a / y) * 0.5;
	    return y;
	}	
	static public float inverseSqrt(double x) {
        final double xhalves = 0.5d * x;
        x = Double.longBitsToDouble(0x5FE6EB50C7B537AAl - (Double.doubleToRawLongBits(x) >> 1));
        
        // repeat the following line for more precision
        x = x * (1.5d - xhalves * x * x);
        return (float)x; 
	}
	static public float atan2(float y, float x) {
		float coeff_1 = PI / 4.0f;
		float coeff_2 = 3.0f * coeff_1;
		float abs_y = Math.abs(y);
		float angle;
		if (x >= 0) {
			float r = (x - abs_y) / (x + abs_y);
			angle = coeff_1 - coeff_1 * r;
		} else {
			float r = (x + abs_y) / (abs_y - x);
			angle = coeff_2 - coeff_1 * r;
		}
		return y < 0 ? -angle : angle;
	}
	static public double distance(float x1, float y1, float x2, float y2) {
		float x = x2 - x1;
		float y = y2 - y1;
		return sqrt(x*x + y*y);
	}
	static public float degrees(float rads) {
		return (rads * 180.0f) / PI;
	}
	static public float radians(float degs) {
		return (degs * PI) / 180.0f;
	}

	static public float dirDelta(float start, float target) {
	    float deltaDir = target - start;
	    
	    // constrain the delta so that our head doesn't spin the long way around
	    while (deltaDir < -PI)
	        deltaDir += 2*PI;
	    while (deltaDir > PI)
	        deltaDir -= 2*PI;
	        
	    return deltaDir;
	}
	static public String repeat(char c, int count) {
		String res = "";
		
		for (int i=0;i<count;i++) {
			res += c;
		}
		return res;
	}
	static public float hermiteBlend(float x, float y, float t) {
	    float bl = 3*(t*t) - 2*(t*t*t);
	    return x + (y-x)*bl;
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
    public static float ridge(float x, int ridges) {
        // assumes the value is between 0 and 1
        int i = (int)(x * ridges);
        float stepSize = 1.0f / (float)ridges;        
        float val = i / (float)ridges;
        float frac =(( (x - val) ));

        frac -= 0.5;

        float dest;
        float boundary = 0.45f;
        if (frac < -boundary) {
            dest = val - stepSize;
            frac = map(frac, -0.5f, -boundary, 0, 1);
        } else if (frac > boundary) {
            dest = val + stepSize;
            frac = map(frac, boundary, 0.5f, 1, 0);
        } else {
            dest = val;
            frac = 1.0f;
        }
        val += (dest-val)*(1.0-frac);
        return val;
    }
    
    public static float ridgeFrac(float x, int ridges) {
        // assumes the value is between 0 and 1
        int i = (int)(x * ridges);
        float stepSize = 1.0f / (float)ridges;        
        float val = i / (float)ridges;
        float frac = x - val; 

        return constrain(frac, 0, 1);
    }    
    public static float lerp(float a, float b, float amnt) {
    	return a + (b-a)*amnt;
    }
}
