package myduckisgoingmad.config;

import java.awt.Color;

import org.json.JSONException;
import org.json.JSONObject;

import ender.HLInfo;
import myduckisgoingmad.DuckUtils;

public class HighlightItem {
    public String path;
    public String icon;
    public Color color = new Color(142, 142, 142);
    public boolean minimap = false;
    public boolean radius = false;
    public boolean danger = false;
    public HLInfo info;

    public HighlightItem(String path) {
        this.path = path;
    }

    public Boolean is(String resPath, Boolean strict) {
        if (strict && path == resPath) {
            return true;
        } else if (!strict && resPath.contains(path)) {
            return true;
        }

        return false;
    }

    public Boolean is(String resPath) {
        return is(resPath, false);
    }

    public String toString() {
        return String.format("HighLightItem(%s,%s,%s,%b,%b,%b)", path, icon, color, minimap, radius, danger);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("path", path);

        DuckUtils.jsonPutNonNull(json, "icon", icon);
        DuckUtils.jsonPutNonNull(json, "color", color);
        DuckUtils.jsonPutNonNull(json, "minimap", minimap);
        DuckUtils.jsonPutNonNull(json, "radius", radius);
        DuckUtils.jsonPutNonNull(json, "danger", danger);

        return json;
    }

    public static HighlightItem fromJson(JSONObject json) throws JSONException {
        String path = json.getString("path");
        HighlightItem item = new HighlightItem(path);

        item.minimap = DuckUtils.jsonGetBoolean(json, "minimap", false);
        item.radius = DuckUtils.jsonGetBoolean(json, "radius", false);
        item.danger = DuckUtils.jsonGetBoolean(json, "danger", false);

        if (json.has("color")) {
            item.color = Color.decode(json.getString("color"));
        }

        if (json.has("icon")) {
            item.icon = json.getString("icon");

            if (item.minimap) {
                item.info = new HLInfo(item.path, item.icon);
                item.info.setColor(item.color);
            }
        }

        return item;
    }
}
