package myduckisgoingmad.config;

import java.awt.event.KeyEvent;
import java.util.Set;

import haven.CheckBox;
import haven.Config;
import haven.Coord;
import haven.Label;
import haven.Widget;
import haven.Window;

public class HighlightGroupWnd extends Window {
    private Callback callback;

    public static abstract class Callback {
        public abstract void callback();
    }

    public HighlightGroupWnd(Widget parent, String cap) {
        super(new Coord(400, 300), Coord.z, parent, cap);
        justclose = true;
    }

    public void setData(final HighlightGroup group, Callback callback) {
        this.callback = callback;
        int linePos = 4;

        for (HighlightItem item : group.items) {

            new Label(new Coord(4, linePos + 16), this, item.name);

            (new CheckBox(new Coord(120, linePos), this, "radius") {
                public void changed(boolean val) {
                    item.radius = val;
                    Config.settings.save();
                }
            }).a = item.radius;

            (new CheckBox(new Coord(180, linePos), this, "minimap") {
                public void changed(boolean val) {
                    item.minimap = val;
                    Config.settings.save();
                }
            }).a = item.minimap;

            linePos += 30;
        }
        pack();
    }

    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        callback.callback();
        super.wdgmsg(sender, msg, args);
    }
}
