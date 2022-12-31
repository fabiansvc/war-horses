package model;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Horse {

    protected Image image;
    protected int[] position;
    protected Image box;
    protected ArrayList<int[]> posiblesMovements;

    public Horse(Image image, int[] position, Image box, ArrayList<int[]> posiblesMovements) {
        this.image = image;
        this.position = position;
        this.box = box;
        this.posiblesMovements = posiblesMovements;
    }

    public void findPosiblesMovements() {
        int row = position[0];
        int col = position[1];

        int[] topLeft = new int[2];
        topLeft[0] = row - 1;
        topLeft[1] = col - 2;
        posiblesMovements.add(topLeft);

        int[] bottomLeft = new int[2];
        bottomLeft[0] = row + 1;
        bottomLeft[1] = col - 2;
        posiblesMovements.add(bottomLeft);

        int[] leftTop = new int[2];
        leftTop[0] = row - 2;
        leftTop[1] = col - 1;
        posiblesMovements.add(leftTop);

        int[] rightTop = new int[2];
        rightTop[0] = row - 2;
        rightTop[1] = col + 1;
        posiblesMovements.add(rightTop);

        int[] topRight = new int[2];
        topRight[0] = row - 1;
        topRight[1] = col + 2;
        posiblesMovements.add(topRight);

        int[] bottomRight = new int[2];
        bottomRight[0] = row + 1;
        bottomRight[1] = col + 2;
        posiblesMovements.add(bottomRight);

        int[] leftBottom = new int[2];
        leftBottom[0] = row + 2;
        leftBottom[1] = col - 1;
        posiblesMovements.add(leftBottom);

        int[] rightBottom = new int[2];
        rightBottom[0] = row + 2;
        rightBottom[1] = col + 1;
        posiblesMovements.add(rightBottom);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public Image getBox() {
        return box;
    }

    public void setBox(Image box) {
        this.box = box;
    }

    public ArrayList<int[]> getPosiblesMovements() {
        return posiblesMovements;
    }

    public void setPosiblesMovements(ArrayList<int[]> posiblesMovements) {
        this.posiblesMovements = posiblesMovements;
    }

}
