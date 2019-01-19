package project.donnees.wrapper.insigning;

import android.graphics.Color;
import android.support.annotation.NonNull;

/**
 * Created by VINCENT on 31/12/2018.
 *
 */
public class Cell implements ICell {

    final String name;

    public Cell() {
        this.name = null;
    }

    public Cell(String name) {
        this.name = name;
    }

    @Override
    public int getColor() {
        return Color.WHITE;
    }

    @Override
    public String toString() {
        return name;
    }

}
