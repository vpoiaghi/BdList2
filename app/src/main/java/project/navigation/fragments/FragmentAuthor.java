package project.navigation.fragments;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.search.SearchGoodiesParameters;
import project.navigation.adapters.AuthorBiographyAdapter;
import project.navigation.adapters.EditionsAdapter;
import project.navigation.adapters.SerieGoodiesAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.ServiceAuthors;
import project.services.ServiceEditions;
import project.services.ServiceGoodies;
import project.services.factory.ServicesFactory;
import project.utils.ImageUtils;

/**
 * Created by VINCENT on 28/03/2016.
 *
 */
public class FragmentAuthor extends FragmentMultiList {

    private static final ServiceAuthors svcAuthors = ServicesFactory.get(ServiceAuthors.class);
    private static final ServiceGoodies svcGoodies = ServicesFactory.get(ServiceGoodies.class);
    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);

    private Author author;

    public FragmentAuthor() {
        super();
        author = null;
    }

    @Override
    protected void doLoadFragment() {
        final long idAuthor = (long) getParameterIn(ParametersCodes.ID_AUTHOR);
        author = svcAuthors.getById(idAuthor);
    }

    @Override
    protected void doRefreshFragment() {
        final TextView txtName = (TextView) findViewById(R.id.author_txt_name);
        txtName.setText(author.getName());

        addFrontPicture();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_authors;
    }

    @Override
    public String getFragmentTitle() {
        return "Auteur";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_author;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Biographie", getAuthorBiographyAdapter(), R.drawable.icn_information));
        listDescriptors.add(new ListDescriptor("Editions", getAuthorEditionsAdapter(), R.drawable.icn_page_edition));
        listDescriptors.add(new ListDescriptor("Ex-libris", getAuthorExLibrisAdapter(), R.drawable.icn_exlibris));

        return listDescriptors;
    }

    private void addFrontPicture() {

        final ImageView imgAuthor = (ImageView) findViewById(R.id.author_img);
        final String filePath = getAuthorImagefilePath(author.getId());

        if (filePath == null) {
            ImageUtils.loadImage(R.drawable.unknown_author, imgAuthor);
        //} else {
            // A ADAPTER POUR L'AUTEUR QUAND ON AURA DES IMAGES
            //final Long idAuthor = idAuthor;
            //ImageUtils.loadImage(filePath, imgAuthor);
        }

    }

    private String getAuthorImagefilePath(Long idAuthor) {

        // A ADAPTER POUR L'AUTEUR QUAND ON AURA DES IMAGES
        //return ConstantesEvents.PATH_AUTHORS + String.format(Locale.FRANCE, "%06d", idAuthor) + ".jpg";
        return null;
    }

    private BaseAdapter getAuthorBiographyAdapter() {

        List<String> infosList = new ArrayList<>();

        infosList.add("Bla bla bla");

        return new AuthorBiographyAdapter(getParentActivity(), R.layout.itm_author_biography, infosList);
    }

    private BaseAdapter getAuthorEditionsAdapter() {

        List<Edition> editionsList = svcEditions.getByAuthor(author.getId());

        return new EditionsAdapter(getParentActivity(), R.layout.itm_edition, editionsList);
    }

    private BaseAdapter getAuthorExLibrisAdapter() {

        SearchGoodiesParameters searchparameters = new SearchGoodiesParameters();
        searchparameters.setIdAuthor(author.getId());
        //searchparameters.addKindName("Ex-libris");
        //searchparameters.addKindName("Offset");

        List<Goody> goodiesList = svcGoodies.search(searchparameters);

        return new SerieGoodiesAdapter(getParentActivity(), R.layout.itm_serie_goody, goodiesList);
    }
}
