package project.services;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.InSigning;
import project.donnees.dao.DaoInSigning;
import project.services.abs.Service;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceInSignings extends Service<InSigning, DaoInSigning> {

    private static final ServiceAuthors svcAuthors = ServicesFactory.get(ServiceAuthors.class);
    private static final ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);

    //private static final DaoInSigning daoInSigning = new DaoInSigning();
    private DaoInSigning dao;

    public ServiceInSignings() {
        super();
    }

    @Override
    protected DaoInSigning getDao() {

        if (dao == null) {
            dao = new DaoInSigning();
        }

        return dao;
    }

    public List<InSigning> get(final Festival festival) {

        final List<InSigning> result;

        if (festival == null) {
            result = null;
        } else {
            result = get(festival, null, festival.getBeginDate().getDate());
        }

        return result;
    }

    public List<InSigning> get(final Festival festival, final Date day) {
        return get(festival, null, day);
    }

    public List<InSigning> get(@NonNull final Festival festival, final Editor editor, @NonNull final Date day) {

        List<InSigning> lst = getDao().getByFestival(festival, editor, day);

        // Chargement des éditeurs et des auteurs
        if (editor == null) {
            setLinkedBo(lst, festival);
        } else {
            setLinkedBo(lst, festival, editor);
        }

        return lst;

    }

    private void setLinkedBo(List<InSigning> lst, Festival festival, Editor editor) {

        List<Long> idAuthorsList = new ArrayList<>();
        List<Author> authorsList = svcAuthors.getById(idAuthorsList);
        Long idAuthor;

        for (InSigning is : lst) {
            idAuthorsList.add(is.getIdAuthor());
        }

        for (InSigning is : lst) {

            is.setFestival(festival);
            is.setEditor(editor);

            idAuthor = is.getIdAuthor();
            for (Author author : authorsList) {
                if (author.getId().equals(idAuthor)) {
                    is.setAuthor(author);
                    break;
                }
            }
        }
    }

    private void setLinkedBo(List<InSigning> lst, Festival festival) {

        List<Long> idAuthorsList = new ArrayList<>();
        List<Long> idEditorsList = new ArrayList<>();

        for (InSigning is : lst) {
            idAuthorsList.add(is.getIdAuthor());
            idEditorsList.add(is.getIdEditor());
        }

        List<Author> authorsList = svcAuthors.getById(idAuthorsList);
        Long idAuthor;

        List<Editor> editorsList = svcEditors.getById(idEditorsList);
        Long idEditor;

        for (InSigning is : lst) {

            is.setFestival(festival);

            idAuthor = is.getIdAuthor();
            for (Author author : authorsList) {
                if ((author != null) && (author.getId() != null) && (author.getId().equals(idAuthor))) {
                    is.setAuthor(author);
                    break;
                }
            }

            idEditor = is.getIdEditor();
            if (idEditor != null) {
                for (Editor e : editorsList) {
                    if (e.getId().equals(idEditor)) {
                        is.setEditor(e);
                        break;
                    }
                }
            }
        }
    }

}
