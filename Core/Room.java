package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Room extends Engine {


    private int x1;
    private int y1;

    private int x;

    private int y;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void createRoom(int height, int width, TETile[][] worldFrame) {
        boolean Go = true;
        for (int n = x; n < height + x - 1; n++) {
            for (int m = y; m < width + y - 1; m++) {
                if (worldFrame[n][m] == Tileset.FLOOR || worldFrame[n + 1][m + 1] == Tileset.FLOOR ||
                        worldFrame[n + 1][m] == Tileset.FLOOR || worldFrame[n][m + 1] == Tileset.FLOOR ||
                        worldFrame[n - 1][m] == Tileset.FLOOR || worldFrame[n][m - 1] == Tileset.FLOOR ||
                        worldFrame[n - 1][m - 1] == Tileset.FLOOR) {
                    Go = false;
                }
            }
        }
        if (Go) {
            for (int i = x; i < height + x - 1; i++) {
                for (int j = y; j < width + y - 1; j++) {
                    worldFrame[i][j] = Tileset.FLOOR;
                }
            }
        }
//        Wall.addWall(worldFrame);
        this.x1 = height + x;
        this.y1 = width + y;
    }

    public static void joinRooms(Room one, Room two, TETile[][] world) {
        Random rand = new Random();
        Boolean next = false;
        int r1x = rand.nextInt(one.x, one.x1 - 1);
        int r2x = rand.nextInt(two.x, two.x1 - 1);
        int r1y = rand.nextInt(one.y, one.y1 - 1);
        int r2y = rand.nextInt(two.y, two.y1 - 1);
        Hallway h = new Hallway(one.x, two.x, one.y, two.y);
        h.makeHallway(one.x, two.x, one.y, two.y, world);
//        for (int i = one.x; i < one.x1; i++) {
//            for (int j = two.x; j < two.x1; j++) {
//                if (i == j) {
//                    for (int y = Math.min(one.y, two.y); y < Math.max(one.y, two.y); y++) {
//                        world[i][y] = Tileset.FLOOR;
//                    }
//                } else {
//                    next = true;
//                }
//            }
//        }
//        if (next) {
//            for (int i = one.y; i < one.y1; i++) {
//                for (int j = two.y; j < two.y1; j++) {
//                    if (i == j) {
//                        for (int x = Math.min(one.x, two.x); x < Math.max(one.x, two.x); x++) {
//                            world[x][i] = Tileset.FLOOR;
//                        }
//                    } else {
//                        next = true;
//                    }
//                }
//            }
//        }

//        if (next) {
//            int xpoint = 0;
//            int xpoint2 = 0;
//            if (Math.min(one.y, two.y) == one.y) {
//                xpoint = one.x;
//                xpoint2 = two.x;
//            } else {
//                xpoint = two.x;
//                xpoint2 = one.x;
//            }
//            for (int y = Math.min(one.y, two.y); y < Math.max(one.y, two.y); y++) {
//                world[xpoint][y] = Tileset.FLOOR;
//            }
//            for (int x = xpoint; x < xpoint2; x++) {
//                world[x][Math.max(one.y, two.y)] = Tileset.FLOOR;
//            }
//        }
    }
}
