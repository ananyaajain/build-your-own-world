package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;

public class Hallway {
    private int r1x;
    private int r2x;
    private int r1y;
    private int r2y;

    public Hallway (int r1x, int r2x, int r1y, int r2y) {
        this.r1x = r1x;
        this.r2x = r2x;
        this.r1y = r1y;
        this.r2y = r2y;
    }


    public void makeHallway(int r1x, int r2x, int r1y, int r2y, TETile[][] finalWorldFrame) {
        if (r1y == r2y) {
            for (int x = Math.min(r1x, r2x); x < Math.max(r1x, r2x); x++) {
                finalWorldFrame[x][r1y] = Tileset.FLOOR;
            }
        } else if (r1x == r2x) {
            for (int y = Math.min(r1y, r2y); y < Math.max(r1y, r2y); y++) {
                finalWorldFrame[r1x][y] = Tileset.FLOOR;
            }
        } else {
            int xpoint = 0;
            int xpoint2 = 0;
            if (Math.min(r1y, r2y) == r1y) {
                xpoint = r1x;
                xpoint2 = r2x;
            } else {
                xpoint = r2x;
                xpoint2 = r1x;
            }
            for (int y = Math.min(r1y, r2y); y < Math.max(r1y, r2y); y++) {
                finalWorldFrame[xpoint][y] = Tileset.FLOOR;
            }
            for (int x = xpoint; x < xpoint2; x++) {
                finalWorldFrame[x][Math.max(r1y, r2y)] = Tileset.FLOOR;
            }
        }
    }
}
