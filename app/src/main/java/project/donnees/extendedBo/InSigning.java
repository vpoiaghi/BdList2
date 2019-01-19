package project.donnees.extendedBo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.donnees.bo.BoInSigning;
import project.services.ServiceAuthors;
import project.services.ServiceEditors;
import project.services.ServiceFestivals;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 18/05/2016.
 *
 */
public class InSigning extends BoInSigning {

    private Author author = null;
    private Festival festival = null;
    private Editor editor = null;

    final static ServiceAuthors svcAuthors = ServicesFactory.get(ServiceAuthors.class);
    final static ServiceFestivals svcFestivals = ServicesFactory.get(ServiceFestivals.class);
    final static ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);


    public List<Long> getIdEditionsList() {

        final List<Long> list = new ArrayList<>();
        final String ids = getIdListEditions();

        if (ids != null) {

            String[] idsArray = ids.split(";");

            for (String id : idsArray) {
                if (id.length() > 0) {
                    list.add(Long.parseLong(id));
                }
            }
        }

        return list;
    }

    public Author getAuthor() {

        Long idAuthor = getIdAuthor();

        if ((author == null) || (! author.getId().equals(idAuthor))) {
            final DateFormat df = new SimpleDateFormat("HH:mm:ss SSS");
            System.out.println("############ get author in base " + df.format(new Date()));
            author = svcAuthors.getById(idAuthor);
        }

        return author;
    }

    public void setAuthor(Author author) {

        if (! author.getId().equals(getIdAuthor()) || (this.author == null)) {
            this.author = author;
            setIdAuthor(author.getId());
        }
    }

    public Festival getFestival() {

        Long idFestival = getIdFestival();

        if ((festival == null) || (! festival.getId().equals(idFestival))) {
            festival = svcFestivals.getById(idFestival);
        }

        return festival;
    }

    public void setFestival(Festival festival) {

        if (! festival.getId().equals(getIdFestival()) || (this.festival == null)) {
            this.festival = festival;
            setIdFestival(festival.getId());
        }
    }

    public Editor getEditor() {

        Long idEditor = getIdEditor();

        if ((editor == null) || (! editor.getId().equals(idEditor))) {
            final DateFormat df = new SimpleDateFormat("HH:mm:ss SSS");
            System.out.println("############ get editor in base " + df.format(new Date()));
            editor = svcEditors.getById(idEditor);
        }

        return editor;
    }

    public void setEditor(Editor editor) {

        if (editor == null) {
            this.editor = null;
            setIdEditor(null);
        } else if (! editor.getId().equals(getIdEditor()) || (this.editor == null)) {
            this.editor = editor;
            setIdEditor(editor.getId());
        }
    }

    @Override
    public String toString() {
        return festival.toString() + " - " + author.toString();
    }

}
