package project.navigation.components;

import project.donnees.wrapper.insigning.ICell;

/**
 * Created by VINCENT on 31/12/2018.
 *
 */
public class CellView {

    private final ICell cell;

    private int width = 0;

    public CellView(ICell cell, final int width) {
        this.cell = cell;
        this.width = width;
    }

    public ICell getCell() {
        return cell;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
