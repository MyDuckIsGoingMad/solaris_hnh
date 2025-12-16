package myduckisgoingmad.config;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HavenSettings {
    private final Path configPath;
    public HighlightConfig highlight;

    public HavenSettings(String filepath) throws IOException {
        this.configPath = Paths.get(filepath);
        ensureFileExists();
        load();
    }

    private void ensureFileExists() throws IOException {
        if (!Files.exists(configPath)) {
            if (configPath.getParent() != null) {
                Files.createDirectories(configPath.getParent());
            }
            highlight = new HighlightConfig();
            save();
        }
    }

    public void load() throws IOException {
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader(configPath.toFile())));
            highlight = HighlightConfig.fromJson(json);
        } catch (JSONException e) {
            throw new IOException("Failed to parse JSON from " + configPath.toString(), e);
        }
    }

    public void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configPath.toFile()))) {
            try {
                JSONObject json = highlight.toJson();
                writer.write(json.toString(2));
            } catch (JSONException e) {
                throw new IOException("Failed to create JSON configuration", e);
            }
        }
    }
}
