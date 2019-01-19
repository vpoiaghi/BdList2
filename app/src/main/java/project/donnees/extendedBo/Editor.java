package project.donnees.extendedBo;

import project.donnees.bo.BoEditor;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class Editor extends BoEditor implements Comparable<Editor> {

    @Override
    public int compareTo(Editor another) {
        return this.getName().compareTo(another.getName());
    }

    @Override
    public String toString() {
        return getName();
    }

}
