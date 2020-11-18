package de.jpp.maze;

import java.awt.image.BufferedImage;

public class MazeAsImage {

    Maze maze;

    public MazeAsImage(Maze maze) {
        this.maze=maze;
    }

    public BufferedImage getImage() {

        int width = maze.getWidth() * 2 + 1;
        int height = maze.getHeight() * 2 + 1;

        BufferedImage bu = new BufferedImage(width, height, 5);

        for (int i = 0; i < width; i++) {
            bu.setRGB(i, 0, -16777216);
            bu.setRGB(i, height - 1, -16777216);
        }
        for (int i = 0; i < height; i++) {
            bu.setRGB(0, i, -16777216);
            bu.setRGB(width - 1, i, -16777216);
        }
        for (int i = 2; i < width - 2; i += 2) {
            for (int j = 2; j < height - 2; j += 2) {
                bu.setRGB(i, 0, -16777216);
                bu.setRGB(i, height - 1, -16777216);
            }
        }

        for (int i = 1; i < width - 1; i += 2) {
            for (int j = 1; j < height - 1; j += 2) {
                bu.setRGB(i, j, -1);
            }
        }
        for (int i = 1; i < width - 1; i += 2) {
            for (int j = 1; j < height - 1; j += 2) {
                bu.setRGB(i, j, -1);
            }
        }
        //int posI = 0;
        //int posJ = 0;
        for (int i = 1; i < bu.getWidth() - 1; i += 2) {
            for (int j = 2; j < bu.getHeight() - 2; j += 2) {
                if (maze.isVWallActive((i-1)/2, (j-2)/2)) {
                    //posJ++;
                    bu.setRGB(i, j, -16777216);
                } else {
                    bu.setRGB(i, j, -1);
                }

            }
            //posI++;
        }
        //int posI = 0;
        //int posJ = 0;
        for (int i = 2; i < bu.getWidth() - 2; i += 2) {

            for (int j = 1; j < bu.getHeight() - 1; j += 2) {
                if (maze.isHWallActive((i-2)/2, (j-1)/2)) {
                    //posJ++;
                    bu.setRGB(i, j, -16777216);
                } else {
                    bu.setRGB(i, j, -1);
                }
            }
            //posI++;
        }
        return bu;
    }


}
