package myduckisgoingmad.config;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import myduckisgoingmad.DuckUtils;

public class HighlightGroup {
    public String name;
    public boolean enabled;
    public ArrayList<HighlightItem> items;

    public HighlightGroup(String name) {
        this(name, true);
    }

    public HighlightGroup(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        this.items = new ArrayList<HighlightItem>();
    }

    public Integer size() {
        return items.size();
    }

    public HighlightItem findItem(String path, Boolean strict) {
        for (HighlightItem item : items) {
            if (item.is(path, strict)) {
                return item;
            }
        }
        return null;
    }

    public HighlightItem findItem(String path) {
        return findItem(path, false);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("group", name);
        json.put("enabled", enabled);

        JSONArray itemsArray = new JSONArray();
        for (HighlightItem item : items) {
            itemsArray.put(item.toJson());
        }
        json.put("items", itemsArray);

        return json;
    }

    public static HighlightGroup fromJson(JSONObject json) {
        try {
            String name = json.getString("group");
            Boolean enabled = DuckUtils.jsonGetBoolean(json, "enabled", true);
            HighlightGroup group = new HighlightGroup(name, enabled);

            if (json.has("items")) {
                JSONArray itemsArray = json.getJSONArray("items");
                for (int i = 0; i < itemsArray.length(); ++i) {
                    JSONObject itemJson = itemsArray.getJSONObject(i);
                    group.items.add(HighlightItem.fromJson(itemJson));
                }
            }

            return group;
        } catch (Exception e) {
            System.out.println("Error while parsing HighlightGroup: " + e.toString());
        }

        return null;
    }
}
