package project.navigation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import bdlist.bdlist.R;
import project.donnees.wrapper.AuthorRole;
import project.donnees.extendedBo.Autograph;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Title;
import project.navigation.activities.PopupPossessionStatesActivity;
import project.navigation.adapters.AutographsAdapter;
import project.navigation.adapters.AuthorsRolesAdapter;
import project.navigation.adapters.StringAdapter;
import project.navigation.adapters.TitlesAdapter;
import project.navigation.adapters.OtherEditionsAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.ServiceAutographs;
import project.services.ServiceEditions;
import project.services.ServiceTitles;
import project.services.factory.ServicesFactory;
import project.utils.ImageUtils;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 27/03/2016.
 */
public class FragmentEdition extends FragmentMultiList implements OtherEditionsAdapter.OnRowClickListener {

    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceAutographs svcAutographs = ServicesFactory.get(ServiceAutographs.class);
    private static final ServiceTitles svcTitles = ServicesFactory.get(ServiceTitles.class);

    private Edition edition;
    private Edition prevEdition = null;
    private Edition nextEdition = null;

    public FragmentEdition() {
        super();

        edition = null;
    }

    @Override
    protected void doLoadFragment() {

        final long idEdition = (long) getParameterIn(ParametersCodes.ID_EDITION);
        edition = svcEditions.getById(idEdition);

        initPrevNextLinks();
    }

    @Override
    protected void doRefreshFragment() {
        addFrontPicture();

        addPossessionPicture();
        addStatePicture();
        addFirstEditionPicture();

        addSerieTitle();
        addEditionTitle();
        addEditor();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_edition;
    }

    @Override
    public String getFragmentTitle() {
        return "Edition";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_edition;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Informations", getInformationsAdapter(), R.drawable.icn_information));
        listDescriptors.add(new ListDescriptor("Autres éditions contenant le(s) même(s) titre(s)", getOtherEditionsAdapter(), R.drawable.icn_page_other_editions));
        listDescriptors.add(new ListDescriptor("Auteurs", getAuthorsAdapter(), R.drawable.icn_page_authors));
        listDescriptors.add(new ListDescriptor("Autographes", getAutographsAdapter(), R.drawable.icn_page_autograph));
        listDescriptors.add(new ListDescriptor("Titres de l'intégrale/receuil", getIntegraleTitlesAdapter(), R.drawable.icn_integrale));

        return listDescriptors;
    }

    private void addSerieTitle() {

        final TextView txtSerieName = (TextView) findViewById(R.id.edition_txt_seriename);
        txtSerieName.setText(edition.getSerieName());

        final Long idSerie = edition.getIdSerie();

        if (idSerie != null) {
            initLink(R.id.edition_txt_seriename, FragmentSerie.class.getName(), ParametersCodes.ID_SERIE, idSerie);
        }
    }

    private void addEditionTitle() {
        final TextView txtEditionName = (TextView) findViewById(R.id.edition_txt_editionName);
        final String text = ServiceEditions.getName(edition);
        txtEditionName.setText(text);
    }

    private void addEditor() {
        final TextView txtEditorName = (TextView) findViewById(R.id.edition_txt_editorName);
        txtEditorName.setText(edition.getEditorName());
    }

    private void addFrontPicture() {

        final ImageView imgEdition = (ImageView) findViewById(R.id.edition_img_cover);

        final Long idEdition = edition.getId();

        ImageUtils.loadImage(getEditionImagefilePath(idEdition), imgEdition);

        initLink(R.id.edition_img_cover, FragmentImageEdition.class.getName(), ParametersCodes.ID_EDITION, idEdition);
    }

    private String getEditionImagefilePath(Long idEdition) {
        return Constantes.PATH_COVERS + String.format(Locale.FRANCE, "%06d", idEdition) + ".jpg";
    }

    private void addPossessionPicture() {

        final ImageView imgPossession = (ImageView) findViewById(R.id.edition_img_in_possession);
        ImageUtils.loadPossessionImage(imgPossession, edition);

        imgPossession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent itt = new Intent(getParentActivity(), PopupPossessionStatesActivity.class);
                Uri u = Uri.parse(edition.getPossessionState().toString());
                itt.setData(u);
                startActivityForResult(itt, PopupPossessionStatesActivity.POPUP_REQUEST_CODE);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PopupPossessionStatesActivity.POPUP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Uri u = data.getData();
                String strPossessionState = u.toString();
                Integer possessionState = Integer.parseInt(strPossessionState);

                svcEditions.setInPossession(edition, possessionState);
                final ImageView imgPossession = (ImageView) findViewById(R.id.edition_img_in_possession);
                ImageUtils.loadPossessionImage(imgPossession, edition);
            }
        }
    }

    private void addStatePicture() {

        ImageView imgState = (ImageView) findViewById(R.id.edition_img_state);

        if (edition.getState() == null) {
            imgState.setVisibility(View.GONE);
        } else {

            final int state = edition.getState() - 1;
            final String drawableName = "icn_state_" + state;

            final Resources resources = getParentActivity().getResources();
            final int resourceId = resources.getIdentifier(drawableName, "drawable", getParentActivity().getPackageName());

            imgState.setImageResource(resourceId);
            imgState.setVisibility(View.VISIBLE);

        }
    }

    private void addFirstEditionPicture() {

        final ImageView imgFirstEdition = (ImageView) findViewById(R.id.edition_img_first_edition);
        final Integer versionNumber = edition.getVersionNumber();

        if ((versionNumber != null) && (versionNumber == 1)) {
            imgFirstEdition.setVisibility(View.VISIBLE);
        } else {
            imgFirstEdition.setVisibility(View.GONE);
        }

    }

    private BaseAdapter getInformationsAdapter() {

        List<String> infosList = new ArrayList<>();

        if (edition.getCollection() != null) {
            infosList.add("Collection : " + edition.getCollection());
        }

        if (edition.getParutionDate() != null) {
            infosList.add("Parution : " + edition.getParutionDate().toString());
        }

        if (edition.getIsbn() != null) {
            infosList.add("ISBN : " + edition.getIsbn());
        }

        if (edition.getBoardsColor() != null) {
            infosList.add("Couleur : " + edition.getBoardsColor());
        }

        if (edition.getBoardsCount() != null) {
            infosList.add("Nb pages : " + edition.getBoardsCount());
        }

        if (edition.getPrintingMax() != null) {
            infosList.add("Nb exemplaires : " + edition.getPrintingMax());
        }

        return new StringAdapter(getParentActivity(), R.layout.itm_string_info, infosList);
    }

    private BaseAdapter getOtherEditionsAdapter() {

        final boolean canShow =  edition.hasAnotherEditions();
        BaseAdapter adapter = null;

        if (canShow) {
            List<Edition> editionsList = svcEditions.getByIdTitles(edition.getIdTitlesList());

            final Long editionId = edition.getId();
            int i = 0;
            Edition e;

            while(i < editionsList.size()) {

                e = editionsList.get(i);

                if (e.getId().equals(editionId)) {
                    editionsList.remove(i);
                } else {
                    i++;
                }

            }

            adapter = new OtherEditionsAdapter(getParentActivity(), R.layout.itm_other_edition, editionsList);
            ((OtherEditionsAdapter)adapter).setOnRowClickListener(this);

        }

        return adapter;

    }

    @Override
    public void onRowClick(View v, Edition edition) {
        addParameterOut(ParametersCodes.ID_EDITION, edition.getId());
        loadFragment();
    }

    private BaseAdapter getAuthorsAdapter() {

        BaseAdapter adapter = null;

        final List<AuthorRole> authorsList = new ArrayList<>();
        addAuthors(edition.getTextAuthorName(), "Scénario", authorsList);
        addAuthors(edition.getDrawAuthorName(), "Dessins", authorsList);
        addAuthors(edition.getColorAuthorName(), "Couleurs", authorsList);

        if (authorsList.size() > 0) {
            adapter = new AuthorsRolesAdapter(getParentActivity(), R.layout.itm_author_role, authorsList);
        }

        return adapter;

    }

    private BaseAdapter getAutographsAdapter() {

        BaseAdapter adapter = null;

        if (edition.isWithAutograph()) {
            List<Autograph> autographsList = svcAutographs.getByEdition(edition.getId());

            adapter = new AutographsAdapter(getParentActivity(), R.layout.itm_autograph, autographsList);
        }

        return adapter;

    }

    private void addAuthors(final String authors, final String role, final List<AuthorRole> authorsList) {

        if ((authors != null) && (authors.length() > 0)) {

            AuthorRole authorRole;
            String idAuthor;
            String[] authorsArray = authors.split(";");

            for (String author : authorsArray) {

                String[] authorInfos = author.split(",");
                idAuthor = authorInfos[0].trim();

                authorRole = new AuthorRole();
                authorRole.setIdAuthor(Long.parseLong(idAuthor));
                authorRole.setAuthorName(author.substring(idAuthor.length() + 1));
                authorRole.setRole(role);
                authorsList.add(authorRole);
            }

        }

    }

    private BaseAdapter getIntegraleTitlesAdapter() {

        BaseAdapter adapter = null;

        if (edition.isIntegral() || edition.isSet()) {

            final List<Long> idTitlesList = edition.getIdTitlesList();
            final List<Title> titlesList = svcTitles.getById(idTitlesList);

            adapter = new TitlesAdapter(getParentActivity(), R.layout.itm_edition_title, titlesList);
        }

        return adapter;

    }

    @Override
    protected void beforeGoPrev() {
        addParameterOut(ParametersCodes.ID_EDITION, prevEdition.getId());
    }

    @Override
    protected void beforeGoNext() {
        addParameterOut(ParametersCodes.ID_EDITION, nextEdition.getId());
    }

    private void initPrevNextLinks() {

        final long idEdition = edition.getId();

        final List<Edition> editionsList = svcEditions.getBySerie(edition.getIdSerie());
        final Iterator<Edition> itEditions = editionsList.iterator();

        boolean found = false;
        Edition currEdition;

        prevEdition = null;
        nextEdition = null;

        while(itEditions.hasNext() && !found) {

            currEdition = itEditions.next();

            if (currEdition.getId() == idEdition) {
                found = true;
            } else {
                prevEdition = currEdition;
            }

        }

        if (itEditions.hasNext()) {
            nextEdition = itEditions.next();
        }

    }

    @Override
    protected boolean hasNext() {
        return (nextEdition != null);
    }

    @Override
    protected boolean hasPrev() {
        return (prevEdition != null);
    }

    @Override
    protected String getPrevText() {
        return ServiceEditions.getNumber(prevEdition);
    }

    @Override
    protected String getNextText() {
        return ServiceEditions.getNumber(nextEdition);
    }
}