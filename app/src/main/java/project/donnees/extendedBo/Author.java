package project.donnees.extendedBo;

import project.donnees.bo.BoAuthor;

/**
 *
 * Created by VINCENT on 16/10/2016.
 */
public class Author extends BoAuthor implements Comparable<Author> {

    @Override
    public int compareTo(Author another) {
        return this.getName().compareTo(another.getName());
    }

    @Override
    public String toString() {
        return getName();
    }

}
