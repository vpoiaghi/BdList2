package project.navigation.fragments;

import android.app.Activity;
import android.content.Intent;
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
import framework.tools.StringUtils;
import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Goody;
import project.navigation.activities.PopupPossessionStatesActivity;
import project.navigation.adapters.AuthorsAdapter;
import project.navigation.adapters.StringAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.FactoryServices;
import project.services.ServiceAuthors;
import project.services.ServiceGoodies;
import project.utils.Constantes;
import project.utils.ImageUtils;

/**
 * Created by VINCENT on 01/12/2018.
 * Ecran de détail d'un para-bd
 */
public class FragmentGoody extends FragmentMultiList {

    private static final ServiceGoodies svcGoodies = FactoryServices.get(ServiceGoodies.class);
    private static final ServiceAuthors svcAuthors = FactoryServices.get(ServiceAuthors.class);

    private Goody goody = null;
    private Goody prevGoody = null;
    private Goody nextGoody = null;

    public FragmentGoody() {
        super();
    }

    @Override
    protected void doLoadFragment() {

        final long idGoody = (long) getParameterIn(ParametersCodes.ID_GOODY);
        goody = svcGoodies.getById(idGoody);

        initPrevNextLinks();
    }

    @Override
    protected void doRefreshFragment() {

        addFrontPicture();

        addPossessionPicture();
        addWithAutograph();
        addWithNumber();

        addSerieTitle();
        addGoodyDescription();
        addEditor();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_goodies;
    }

    @Override
    public String getFragmentTitle() {
        return "Para-bd";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_goody;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Informations", getInformationsAdapter(), R.drawable.icn_information));
        listDescriptors.add(new ListDescriptor("Auteurs", getAuthorsAdapter(), R.drawable.icn_page_authors));

        return listDescriptors;
    }

    private void addSerieTitle() {

        final TextView txtSerieName = (TextView) findViewById(R.id.goody_txt_seriename);
        txtSerieName.setText(goody.getSerieName());

        final Long idSerie = goody.getIdSerie();

        if (idSerie != null) {
            initLink(R.id.goody_txt_seriename, FragmentSerie.class.getName(), ParametersCodes.ID_SERIE, idSerie);
        }
    }

    private void addGoodyDescription() {
        final TextView txtGoodyName = (TextView) findViewById(R.id.goody_txt_description);
        txtGoodyName.setText(goody.getName());
    }

    private void addEditor() {
        final TextView txtEditorName = (TextView) findViewById(R.id.goody_txt_editorName);
        txtEditorName.setText(goody.getEditorName());
    }

    private void addFrontPicture() {

        final ImageView imgGoody = (ImageView) findViewById(R.id.goody_img);
        final Long idGoody = goody.getId();

        ImageUtils.loadImage(getGoodyImagefilePath(idGoody), imgGoody);
        initLink(R.id.goody_img, FragmentImageGoody.class.getName(), ParametersCodes.ID_GOODY, idGoody);
    }

    private String getGoodyImagefilePath(Long idGoody)
    {
        return Constantes.PATH_GOODIES + String.format(Locale.FRANCE, "%06d", idGoody) + ".jpg";
    }

    private void addPossessionPicture() {

        final ImageView imgPossession = (ImageView) findViewById(R.id.goody_img_in_possession);
        ImageUtils.loadPossessionImage(imgPossession, goody);

        imgPossession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent itt = new Intent(getParentActivity(), PopupPossessionStatesActivity.class);
                Uri u = Uri.parse(goody.getPossessionState().toString());
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

                svcGoodies.setInPossession(goody, possessionState);
                final ImageView imgPossession = (ImageView) findViewById(R.id.goody_img_in_possession);
                ImageUtils.loadPossessionImage(imgPossession, goody);
            }
        }
    }

    private void addWithAutograph() {

        final ImageView imgAutograph = (ImageView) findViewById(R.id.goody_img_with_autograph);

        if (goody.isWithAutograph()) {
            imgAutograph.setVisibility(View.VISIBLE);
        } else {
            imgAutograph.setVisibility(View.GONE);
        }

    }

    private void addWithNumber() {

        final ImageView imgNumber = (ImageView) findViewById(R.id.goody_img_with_number);

        if (StringUtils.isNullOrEmpty(goody.getCopyNumber())) {
            imgNumber.setVisibility(View.GONE);
        } else {
            imgNumber.setVisibility(View.VISIBLE);
        }

    }

    private BaseAdapter getInformationsAdapter() {

        List<String> infosList = new ArrayList<>();

        if (goody.getKindName() != null) {
            infosList.add("Genre : " + goody.getKindName());
        }

        if (goody.getParutionDate() != null) {
            infosList.add("Parution : " + goody.getParutionDate().toString());
        }

        String cNumber = goody.getCopyNumber();
        Integer cCount = goody.getCopyCount();
        if ((cNumber != null) || (cCount != null)) {
            String n = cNumber != null ? cNumber.trim() : "";
            String c = cCount != null ? cCount.toString() : "";
            infosList.add("N° : " + n + " / " + c);
        }

        return new StringAdapter(getParentActivity(), R.layout.itm_string_info, infosList);
    }

    private BaseAdapter getAuthorsAdapter() {

        BaseAdapter adapter = null;

        final List<Author> authorsList = svcAuthors.getByGoody(goody);

        if (authorsList.size() > 0) {
            adapter = new AuthorsAdapter(getParentActivity(), R.layout.itm_author, authorsList);
        }

        return adapter;

    }

    @Override
    protected void beforeGoPrev() {
        addParameterOut(ParametersCodes.ID_GOODY, prevGoody.getId());
    }

    @Override
    protected void beforeGoNext() {
        addParameterOut(ParametersCodes.ID_GOODY, nextGoody.getId());
    }

    private void initPrevNextLinks() {

        final long idGoody = goody.getId();

        final List<Goody> goodiesList = svcGoodies.getBySerie(goody.getIdSerie());
        final Iterator<Goody> itGoodies = goodiesList.iterator();

        boolean found = false;
        Goody currGoody;

        prevGoody = null;
        nextGoody = null;

        while(itGoodies.hasNext() && !found) {

            currGoody = itGoodies.next();

            if (currGoody.getId() == idGoody) {
                found = true;
            } else {
                prevGoody = currGoody;
            }

        }

        if (itGoodies.hasNext()) {
            nextGoody = itGoodies.next();
        }

    }

    @Override
    protected boolean hasNext() {
        return (nextGoody != null);
    }

    @Override
    protected boolean hasPrev() {
        return (prevGoody != null);
    }

}
