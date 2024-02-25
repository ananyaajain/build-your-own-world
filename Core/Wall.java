package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Wall extends Engine{

    public Wall () {

    }

    public static void addWall(TETile[][] finalWorld) {
        for (int i = 1; i < finalWorld.length; i++) {
            for (int j = 1; j < finalWorld[0].length; j++) {
                if (finalWorld[i][j] == Tileset.FLOOR) {
                    if (finalWorld[i - 1][j] == Tileset.NOTHING) {
                        finalWorld[i-1][j] = Tileset.WALL;
                    }
                    if (finalWorld[i+1][j] == Tileset.NOTHING && (i+1< finalWorld.length)) {
                        finalWorld[i+1][j] = Tileset.WALL;
                    }
                    if (finalWorld[i][j - 1] == Tileset.NOTHING) {
                        finalWorld[i][j-1] = Tileset.WALL;
                    }
                    if (finalWorld[i][j+1] == Tileset.NOTHING && (j+1< finalWorld[0].length)) {
                        finalWorld[i][j+1] = Tileset.WALL;
                    }
                    if (finalWorld[i-1][j-1] == Tileset.NOTHING && (j+1< finalWorld[0].length)) {
                        finalWorld[i-1][j-1] = Tileset.WALL;
                    }
                    if (finalWorld[i+1][j+1] == Tileset.NOTHING && (j+1< finalWorld[0].length)) {
                        finalWorld[i+1][j+1] = Tileset.WALL;
                    }
                    if (finalWorld[i-1][j+1] == Tileset.NOTHING && (j+1< finalWorld[0].length)) {
                        finalWorld[i-1][j+1] = Tileset.WALL;
                    }
                    if (finalWorld[i+1][j-1] == Tileset.NOTHING && (j+1< finalWorld[0].length)) {
                        finalWorld[i+1][j-1] = Tileset.WALL;
                    }
                }
            }
        }
    }
}
