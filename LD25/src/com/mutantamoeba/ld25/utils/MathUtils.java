package com.mutantamoeba.ld25.utils;

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
	
	/** Just a port of well-known code (original source unknown, I found it via the Google Oracle)
	 * 
	 */
	static public double sqrt(final double a) {
	    final long x = Double.doubleToLongBits(a) >> 32;
	    double y = Double.longBitsToDouble((x + 1072632448) << 31);
	 
	    // repeat the following line for more precision
	    y = (y + a / y) * 0.5;
	    return y;
	}	
	
	/** Just a port of well-known code (original source unknown, I found it via the Google Oracle)
	 * 
	 */
	static public float inverseSqrt(double x) {
        final double xhalves = 0.5d * x;
        x = Double.longBitsToDouble(0x5FE6EB50C7B537AAl - (Double.doubleToRawLongBits(x) >> 1));
        
        // repeat the following line for more precision
        x = x * (1.5d - xhalves * x * x);
        return (float)x; 
	}
	
	/** Just a port of well-known code (original source unknown, I found it via the Google Oracle)
	 * 
	 */	
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

	/** The angular distance between startAngle and targetAngle (avoids turns of > 180 degrees) (generalised)
	 * @param start start angle
	 * @param target target angle
	 * @param maxAngle use PI for radians and 180 for degrees
	 * @return the direction delta
	 */
	static public float dirDelta(float start, float target, float maxAngle) {
	    float deltaDir = target - start;
	    
	    // constrain the delta so that our head doesn't spin the long way around
	    while (deltaDir < -maxAngle)
	        deltaDir += 2*maxAngle;
	    while (deltaDir > maxAngle)
	        deltaDir -= 2*maxAngle;
	        
	    return deltaDir;
	}
	
	/** The angular distance between startAngle and targetAngle (avoids turns of > 180 degrees)  in radians 
	 * @param start the source angle in radians
	 * @param target the angle we wish to turn towards in radians
	 * @return the direction delta in radians
	 */
	static public float dirDelta(float start, float target) {
		return dirDelta(start, target, PI);
	}

	/** performs a simple Hermite Blend
	 * 
	 */
	static public float hermiteBlend(float x, float y, float t) {
	    float bl = 3*(t*t) - 2*(t*t*t);
	    return x + (y-x)*bl;
	}

	/** turns a smooth value between 0..1 into a ridged (discrete) value in the same range
	 * @param x the input value (between 0 and 1)
	 * @param ridges how many discrete steps in the result
	 */
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
