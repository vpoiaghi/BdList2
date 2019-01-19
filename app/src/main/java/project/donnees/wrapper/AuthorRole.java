package project.donnees.wrapper;

/**
 *
 * Created by VINCENT on 29/03/2016.
 */
public class AuthorRole {

    private long idAuthor;
    private String authorName;
    private String role;

    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
