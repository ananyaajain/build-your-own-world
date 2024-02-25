package byow.Core;

import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.Random;

import java.util.HashMap;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public HashMap<Integer, Boolean> connected = new HashMap<>();
    public HashMap<Integer, Room> roomNumber = new HashMap<>();

    public int score = 0;

    public boolean over = false;

    public int light = 1;
    public boolean lit = false;


    public static int xA= 0;
    public static int yA = 0;
    public TETile[][] finalWorldFrame;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH/2, HEIGHT/3, "CS61B : THE GAME");
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(WIDTH/2, HEIGHT/3*2, "New Game (N)");
        StdDraw.text(WIDTH/2, HEIGHT/3*2+2, "Load Game (L)");
        StdDraw.text(WIDTH/2, HEIGHT/3*2+4, "Quit (Q)");
        StdDraw.show();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char c = StdDraw.nextKeyTyped();
            TETile[][] finalWorld;
            if ( c== 'n' || c == 'N') {
                StdDraw.text(WIDTH/2, HEIGHT/2, "PLEASE ENTER A SEED, THEN PRESS 'S'");
                System.out.println("N being reached");
                StdDraw.show();
                String seed = "";
                char temp;
                score = 0;
                light = 5;
                while (true) {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    temp = StdDraw.nextKeyTyped();
                    if (temp == 's' || temp == 'S') {
                        break;
                    }
                    seed += temp;
                }
                int seedN = Integer.parseInt(seed);
                StdDraw.clear();
                Random r = new Random(seedN);
                finalWorld = createWorld(r);
                char pre = 'a';
                System.out.println("reach 2");
                while (!over) {
                    if (StdDraw.hasNextKeyTyped()) {
                        System.out.println("if mein aa gaya");
                        char curr = StdDraw.nextKeyTyped();
                        if ((curr == 'q' || curr == 'Q') && pre == ':') {
                            over = true;
                            StdDraw.clear(StdDraw.BLACK);
                            StdDraw.setPenColor(Color.WHITE);
                            Font font = new Font("Monaco", Font.BOLD, 30);
                            StdDraw.setFont(font);
                            StdDraw.text(WIDTH / 2, HEIGHT / 2, "The game is saved");
                            StdDraw.show();
                            StdDraw.pause(2000);
                            LoadGame(finalWorld);
                        } else {
                            finalWorld = move(curr, finalWorld);
                            ter.renderFrame(finalWorld);
                            pre = curr;
                        }
                    }
                }
            }
            if (c == 'l' || c == 'L') {
                finalWorld = readGame();
                over = false;
                char pre = 'a';
                while (!over) {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    char curr = StdDraw.nextKeyTyped();
                    if ((curr == 'q' || curr == 'Q') && pre == ':') {
                        over = true;
                        StdDraw.clear(StdDraw.BLACK);
                        StdDraw.setPenColor(Color.WHITE);
                        Font font = new Font("Monaco", Font.BOLD, 30);
                        StdDraw.setFont(font);
                        StdDraw.text(WIDTH/2, HEIGHT/2, "The game is saved");
                        StdDraw.show();
                        StdDraw.pause(2000);
                        LoadGame(finalWorld);
                    }
                    move(curr, finalWorld);
                    pre = curr;
                }
                continue;
            }
            if (c == 'Q' || c == 'q') {
                over = true;
                StdDraw.clear(StdDraw.BLACK);
                StdDraw.setPenColor(Color.WHITE);
                Font font = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setFont(font);
                StdDraw.text(WIDTH/2, HEIGHT/2, "The game is over");
                StdDraw.show();
                StdDraw.pause(2000);
                System.exit(0);
            }
        }

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        String seed = "";
        StringInputDevice s = new StringInputDevice(input);
        while (s.possibleNextInput()) {
            char c = s.getNextKey();
            if (c == 'N' || c == 'n') {
                continue;
            } else if (c == 's' || c == 'S') {
                break;
            }
            seed += c;
        }
        long seedString = Long.parseLong(seed);
        Random r = new Random(seedString);

        return createWorld(r);
    }

    public TETile[][] createWorld (Random r) {
        ter.initialize(WIDTH, HEIGHT, 0, 0);
        TETile[][] finalWorldFrame = new TETile[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i ++) {
            for (int j = 0; j < WIDTH; j++) {
                finalWorldFrame[i][j] = Tileset.NOTHING;
            }
        }
        double numRoomTiles = 0;
        double totalTiles = HEIGHT*WIDTH;
        int size = 6;
        int roomN = 1;
        for (int i = 0; i < HEIGHT*WIDTH; i++) {
            int h = r.nextInt(3, 6) + 1;
            int w = r.nextInt(3, 6) + 1;
            int x = r.nextInt(1, HEIGHT/2 - h);
            int y = r.nextInt(1, WIDTH/2 - w);
            numRoomTiles += h*w;
            Room room = new Room(x, y);
            room.createRoom(h, w, finalWorldFrame);
            roomNumber.put(roomN, room);
            connected.put(roomN, false);
            roomN++;
            if (numRoomTiles/totalTiles >= 0.08) {
                break;
            }
        }

        for (int i = 0; i < HEIGHT*WIDTH; i++ ) {
            int h = r.nextInt(3, 6) + 1;
            int w = r.nextInt(3, 6) + 1;
            int x = r.nextInt(HEIGHT/2, HEIGHT - h);
            int y = r.nextInt(1, WIDTH/2 - w);
            numRoomTiles += h*w;
            Room room = new Room(x, y);
            room.createRoom(h, w, finalWorldFrame);
            roomNumber.put(roomN, room);
            connected.put(roomN, false);
            roomN++;
            if (numRoomTiles/totalTiles >= 0.16) {
                break;
            }
        }

        for (int i = 0; i < HEIGHT*WIDTH; i++ ) {
            int h = r.nextInt(3, 5) + 1;
            int w = r.nextInt(3, 5) + 1;
            int x = r.nextInt(1, HEIGHT/2 - h);
            int y = r.nextInt(WIDTH/2, WIDTH - w);
            numRoomTiles += h*w;
            Room room = new Room(x, y);
            room.createRoom(h, w, finalWorldFrame);
            roomNumber.put(roomN, room);
            connected.put(roomN, false);
            roomN++;
            if (numRoomTiles/totalTiles >= 0.24) {
                break;
            }
        }
        for (int i = 0; i < HEIGHT*WIDTH; i++ ) {
            int h = r.nextInt(3, 5) + 1;
            int w = r.nextInt(3, 5) + 1;
            int x = r.nextInt(HEIGHT/2, HEIGHT - h);
            int y = r.nextInt(WIDTH/2, WIDTH - w);
            numRoomTiles += h*w;
            Room room = new Room(x, y);
            room.createRoom(h, w, finalWorldFrame);
            roomNumber.put(roomN, room);
            connected.put(roomN, false);
            roomN++;
            if (numRoomTiles/totalTiles >= 0.32) {
                break;
            }
        }

        for (int z = 1; z < roomNumber.size(); z+=1) {
            Room.joinRooms(roomNumber.get(z), roomNumber.get(z + 1), finalWorldFrame);
        }
        Room.joinRooms(roomNumber.get(1), roomNumber.get(roomNumber.size()-1), finalWorldFrame);
        Room.joinRooms(roomNumber.get(roomNumber.size()/2), roomNumber.get(roomNumber.size()-1), finalWorldFrame);
        Wall.addWall(finalWorldFrame);

        while (true) {
            xA = r.nextInt(5, HEIGHT-5);
            yA = r.nextInt(5, WIDTH-5);
            if (finalWorldFrame[xA][yA] == Tileset.FLOOR) {
                break;
            }
        }
        finalWorldFrame[xA][yA] = Tileset.AVATAR;

        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }



    public TETile[][] move(char m, TETile[][] finalWorldFrame) {
        System.out.println("method reached");
        TETile[][] show = new TETile[HEIGHT][WIDTH];
        TETile[][] WorldFrame;
        WorldFrame = finalWorldFrame;
        if (over) {
            return finalWorldFrame;
        }
        if (m == 'w' || m == 'W') {
            if (!(finalWorldFrame[xA][yA + 1] == (Tileset.WALL)) && !(finalWorldFrame[xA][yA + 1] == (Tileset.NOTHING))) {
                WorldFrame[xA][yA] = Tileset.FLOOR;
                finalWorldFrame[xA][yA] = Tileset.FLOOR;
                yA += 1;
                WorldFrame[xA][yA] = Tileset.AVATAR;
                finalWorldFrame[xA][yA] = Tileset.AVATAR;
                if (lit) {
                    WorldFrame = showLight(finalWorldFrame, show);
                }
            }
        }
        if (m == 's' || m == 'S') {
            if (!(finalWorldFrame[xA][yA - 1] == (Tileset.WALL)) && !(finalWorldFrame[xA][yA - 1] == (Tileset.NOTHING))) {
                WorldFrame[xA][yA] = Tileset.FLOOR;
                finalWorldFrame[xA][yA] = Tileset.FLOOR;
                yA -= 1;
                WorldFrame[xA][yA] = Tileset.AVATAR;
                finalWorldFrame[xA][yA] = Tileset.AVATAR;
                if (lit) {
                    WorldFrame = showLight(finalWorldFrame, show);
                }
            }
        }
        if (m == 'a' || m == 'A') {
            if (!(finalWorldFrame[xA - 1][yA] == (Tileset.WALL)) && !(finalWorldFrame[xA - 1][yA] == (Tileset.NOTHING))) {
                WorldFrame[xA][yA] = Tileset.FLOOR;
                finalWorldFrame[xA][yA] = Tileset.FLOOR;
                xA -= 1;
                WorldFrame[xA][yA] = Tileset.AVATAR;
                finalWorldFrame[xA][yA] = Tileset.AVATAR;
                if (lit) {
                    WorldFrame = showLight(finalWorldFrame, show);
                }
            }
        }
        if (m == 'd' || m == 'D') {
            if (!(finalWorldFrame[xA + 1][yA] == (Tileset.WALL)) && !(finalWorldFrame[xA + 1][yA] == (Tileset.NOTHING))) {
                WorldFrame[xA][yA] = Tileset.FLOOR;
                finalWorldFrame[xA][yA] = Tileset.FLOOR;
                xA += 1;
                WorldFrame[xA][yA] = Tileset.AVATAR;
                finalWorldFrame[xA][yA] = Tileset.AVATAR;
                if (lit) {
                    WorldFrame = showLight(finalWorldFrame, show);
                }
            }
        }

        if (m == 'o' || m == 'O') {
            if (!lit) {
                WorldFrame = showLight(WorldFrame, new TETile[WIDTH][HEIGHT]);
                lit = true;
            } else {
                lit = false;
                WorldFrame = finalWorldFrame;
            }
        }
        return WorldFrame;
    }
            //ter.renderFrame(finalWorldFrame);
//            return finalWorldFrame;

    private void LoadGame(TETile[][] world) {
        File file = new File("currentState.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream y = new FileOutputStream(file);
            ObjectOutputStream fileY = new ObjectOutputStream(y);
            fileY.writeObject (world);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        } catch ( IOException e) {
            System.out.println(e);
            System.exit (0);
        }
    }

    private TETile[][] readGame () {
        File file = new File("currentState.txt");
        if (!file.exists()) {
            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
                return (TETile[][]) input.readObject();
            } catch (FileNotFoundException e) {
                System.exit(0);
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.exit(0);
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                System.exit(0);
                throw new RuntimeException(e);
            }
        }
        In read = new In(file);
        Random r = null;
        if (read.hasNextLine()) {
            long seed = Long.parseLong(read.readLine());
            r = new Random(seed);
        }
        if (read.hasNextLine()) {
            xA = Integer.parseInt(read.readLine());
        }
        if (read.hasNextLine()) {
            yA = Integer.parseInt(read.readLine());
        }
        TETile[][] world = createWorld(r);
        return world;
    }

    private TETile[][] showLight(TETile[][] world, TETile[][] show) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                show[i][j] = Tileset.NOTHING;
            }
        }
        for (int i = Math.max(xA - light, 0); i < Math.min(xA + light, HEIGHT); i++) {
            for (int j = Math.max(yA - light, 0); j < Math.min(yA + light, WIDTH); j++) {
                show[i][j] = world[i][j];
            }
        }
        return show;
    }
}
