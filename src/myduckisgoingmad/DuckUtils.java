package myduckisgoingmad;

import org.json.JSONException;
import org.json.JSONObject;

import static haven.MCache.tileSize;
import haven.Coord;
import haven.GOut;
import haven.Gob;
import haven.Tex;
import haven.UI;
import union.JSBot;
import union.JSBotUtils;

public class DuckUtils {
    public static void jsonPutNonNull(JSONObject json, String key, Object value) throws JSONException {
        if (value != null)
            json.put(key, value);
    }

    public static Boolean jsonGetBoolean(JSONObject json, String key, Boolean defaultValue) throws JSONException {
        if (json.has(key)) {
            return json.getBoolean(key);
        }
        return defaultValue;
    }

    public static void drawMinimapHighlights(GOut g, Coord tc, Coord hsz) {
        if (UI.instance.minimap == null) {
            return;
        }
        synchronized (UI.instance.minimap.highlights) {
            for (MinimapHighlight highlight : UI.instance.minimap.highlights) {

                Coord ptc = highlight.pos.div(tileSize).add(tc.inv()).add(hsz.div(2));

                g.chcolor(highlight.item.color);
                g.fellipse(ptc, new Coord(10, 10));
                g.chcolor();

                if (highlight.item.info != null) {
                    Tex tex = highlight.item.info.geticon();
                    g.image(tex, ptc.sub(new Coord(10, 10)), new Coord(20, 20));
                }
            }
        }
    }

    public static Gob findNearestGob(String resName, Coord position, int radius) {
        Gob result = null;
        double min = radius;

        synchronized (UI.instance.mapview.glob.oc) {
            for (Gob gob : UI.instance.mapview.glob.oc) {
                if (gob.resname() != resName) {
                    continue;
                }

                double dist = gob.position().dist(position);
                if ((dist <= radius) && (result == null || dist < min)) {
                    result = gob;
                    min = dist;
                }
            }
        }

        return result;
    }

    public static Gob findNearestGob(String resName, Coord rc) {
        return findNearestGob(resName, rc, 10);
    }

}
