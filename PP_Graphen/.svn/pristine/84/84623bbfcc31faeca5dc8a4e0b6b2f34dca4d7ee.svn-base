package de.jpp.maze;

import de.jpp.io.interfaces.ParseException;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class MazeImpl implements Maze {

    private int width;
    private int height;
    private boolean[][] cells;
    private boolean[][] hWall;
    private boolean[][] vWall;

    private Random ran;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeImpl maze = (MazeImpl) o;
        return Arrays.deepEquals(hWall, maze.hWall) &&
                Arrays.deepEquals(vWall, maze.vWall);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(hWall);
        result = 31 * result + Arrays.hashCode(vWall);
        return result;
    }

    public MazeImpl(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new boolean[width][height];
        hWall = new boolean[width - 1][height];
        vWall = new boolean[width][height - 1];


    }

    public MazeImpl(int width, int height, Random ran) {
        this.width = width;
        this.height = height;
        this.ran = ran;
        cells = new boolean[width][height];
        hWall = new boolean[width - 1][height];
        vWall = new boolean[width][height - 1];
    }

    @Override
    public void setHWall(int x, int y, boolean wallActive) {
        hWall[x][y] = wallActive;
    }

    @Override
    public void setVWall(int x, int y, boolean wallActive) {
        vWall[x][y] = wallActive;
    }

    @Override
    public void setAllWalls(boolean wallActive) {
        for (int i = 0; i < hWall.length; i++) {
            for (int j = 0; j < hWall[i].length; j++) {
                hWall[i][j] = wallActive;
            }
        }
        for (int i = 0; i < vWall.length; i++) {
            for (int j = 0; j < vWall[i].length; j++) {
                vWall[i][j] = wallActive;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean[][] getCells() {
        return cells;
    }

    public boolean[][] gethWall() {
        return hWall;
    }

    public boolean[][] getvWall() {
        return vWall;
    }

    @Override
    public boolean isHWallActive(int x, int y) {
        if (x <= hWall.length && y <= hWall[x].length) {
            return hWall[x][y];
        } else return true;

    }

    @Override
    public boolean isVWallActive(int x, int y) {
        if (x <= vWall.length && y <= vWall[x].length) {
            return vWall[x][y];
        } else return true;

    }


    public void unterteile(int startbreite, int endbreite, int starthoehe, int endhoehe) {
        int widthNeu = endbreite - startbreite + 1;
        int heightNeu = endhoehe - starthoehe + 1;

        if (widthNeu > 1 && heightNeu > 1) {
            if (widthNeu > heightNeu) {
                unterteileLabyHorizontal(startbreite, endbreite, starthoehe, endhoehe);
            } else if (heightNeu > widthNeu) {
                unterteileLabyVertikal(startbreite, endbreite, starthoehe, endhoehe);
            } else {
                //boolean bool = ran.nextBoolean();
                if (ran.nextBoolean()) {
                    unterteileLabyHorizontal(startbreite, endbreite, starthoehe, endhoehe);
                } else unterteileLabyVertikal(startbreite, endbreite, starthoehe, endhoehe);
            }
        }
    }

    public void unterteileLabyHorizontal(int startbreite, int endbreite, int starthoehe, int endhoehe) {
        int widthNeu = endbreite - startbreite + 1;
        int heightNeu = endhoehe - starthoehe + 1;

        int ran1 = ran.nextInt(widthNeu - 1);
        int ran2 = ran.nextInt(heightNeu - 1);

        for (int i = starthoehe; i <= endhoehe; i++) {
            if (i != ran2+starthoehe) {
                setHWall(ran1+startbreite, i, true);
            }
        }

        unterteile(startbreite,startbreite+ran1,starthoehe,endhoehe);
        unterteile(startbreite+ran1+1,endbreite,starthoehe,endhoehe);
    }


    public void unterteileLabyVertikal(int startbreite, int endbreite, int starthoehe, int endhoehe) {
        int widthNeu = endbreite - startbreite + 1;
        int heightNeu = endhoehe - starthoehe + 1;

        int ran1 = ran.nextInt(heightNeu - 1);
        int ran2 = ran.nextInt(widthNeu - 1);

        for (int i = startbreite; i <= endbreite; i++) {
            if (i != ran2+startbreite) {
                setVWall(i, ran1+starthoehe, true);
            }
        }

        unterteile(startbreite, endbreite, starthoehe,starthoehe+ran1);
        unterteile(startbreite, endbreite, starthoehe+ran1+1, endhoehe);
    }
}

