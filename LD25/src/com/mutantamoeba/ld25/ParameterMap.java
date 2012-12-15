package com.mutantamoeba.ld25;


public class ParameterMap<ContentType> {
    String    _name;
    public final int       _w, _h, _area;
    Object    _map[];
    public ParameterMap(String name, int w, int h, ContentType defaultValue) {
        _w = w;
        _h = h;
        _area = w * h;
        _name = name;
        _map = new Object[_area];
        for (int i=0;i<_area;i++) {
            _map[i] = defaultValue;
        }
    }
    public ContentType get(int x, int y) {
        x = (int)MathUtils.constrain(x, 0, _w-1);
        y = (int)MathUtils.constrain(y, 0, _h-1);
        return (ContentType)_map[y*_w+x];
    }
    public ContentType get(int offs) {
        offs = (int)MathUtils.constrain(offs, 0, _area-1);
        return (ContentType)_map[offs];
    }
    public void set(int x, int y, ContentType v) {
        x = (int)MathUtils.constrain(x, 0, _w-1);
        y = (int)MathUtils.constrain(y, 0, _h-1);
        
        int ix = (int)x;
        int iy = (int)y;
        _map[iy*_w+ix] = v;      
    }
    public void set(int offs, ContentType v) {
        offs = (int)MathUtils.constrain(offs, 0, _area-1);
        _map[offs] = v;
    }    
}