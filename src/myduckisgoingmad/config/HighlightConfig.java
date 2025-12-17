package myduckisgoingmad.config;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HighlightConfig {
    public HighlightGroup forageables;
    public HighlightGroup curiosities;
    public HighlightGroup kritters;
    public HighlightGroup misc;

    public HighlightConfig() {
    }

    public void addGroup(HighlightGroup group) {
        switch (group.name) {
            case "Forageables":
                forageables = group;
                break;
            case "Curiosities":
                curiosities = group;
                break;
            case "Kritter":
                kritters = group;
                break;
            case "Misc":
                misc = group;
                break;
            default:
                System.out.println("Unknown highlight group: " + group.name);
                break;
        }
    }

    public ArrayList<HighlightGroup> groups() {
        ArrayList<HighlightGroup> result = new ArrayList<HighlightGroup>();

        result.add(forageables);
        result.add(curiosities);
        result.add(kritters);
        result.add(misc);

        return result;
    }

    public HighlightItem findItem(String name, Boolean strict) {
        HighlightItem item = null;
        if (kritters.enabled) {
            item = kritters.findItem(name, strict);
        }
        if (item == null && curiosities.enabled) {
            item = curiosities.findItem(name, strict);
        }
        if (item == null && forageables.enabled) {
            item = forageables.findItem(name, strict);
        }
        if (item == null && misc.enabled) {
            item = misc.findItem(name, strict);
        }

        return item;
    }

    public HighlightItem findItem(String name) {
        return findItem(name, false);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject root = new JSONObject();
        JSONArray highlightArray = new JSONArray();
        highlightArray.put(forageables.toJson());
        highlightArray.put(curiosities.toJson());
        highlightArray.put(kritters.toJson());
        highlightArray.put(misc.toJson());
        root.put("highlight", highlightArray);
        return root;
    }

    public static HighlightConfig fromJson(JSONObject json) throws JSONException {
        HighlightConfig config = new HighlightConfig();

        if (json.has("highlight")) {
            JSONArray highlightArray = json.getJSONArray("highlight");
            for (int i = 0; i < highlightArray.length(); i++) {
                JSONObject groupJson = highlightArray.getJSONObject(i);
                HighlightGroup group = HighlightGroup.fromJson(groupJson);
                if (group != null) {
                    config.addGroup(group);
                }
            }
        }

        return config;
    }

    public void debugPrint() {
        System.out
                .println(String.format("Loaded:\n\t%d Forageables\n\t%d Curiosities\n\t%d Kritters\n\t%d Misc",
                        forageables.size(), curiosities.size(), kritters.size(), misc.size()));
    }
}
