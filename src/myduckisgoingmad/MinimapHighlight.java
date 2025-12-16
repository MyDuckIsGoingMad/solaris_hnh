package myduckisgoingmad;

import haven.Coord;
import myduckisgoingmad.config.HighlightItem;

public class MinimapHighlight {
    public Coord pos;
    public HighlightItem item;

    public MinimapHighlight(Coord pos, HighlightItem item) {
        this.pos = pos;
        this.item = item;
    }

}