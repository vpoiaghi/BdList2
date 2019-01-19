package project.services;

import java.util.ArrayList;
import java.util.List;

import framework.donnees.bo.Bo;
import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.wrapper.PressItem;
import project.donnees.wrapper.PressItemsList;
import project.services.abs.Service;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 16/12/2017.
 *
 */
public class ServicePress extends Service<Bo, TypedDao<Bo>> {

    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoodies = ServicesFactory.get(ServiceGoodies.class);

    public ServicePress() {
        super();
    }

    @Override
    protected TypedDao<Bo> getDao() {
        return null;
    }

    public List<PressItemsList> getItems() {

        List<PressItemsList> pressItemsListByDate = new ArrayList<>();

        AddPressEditions(pressItemsListByDate);
        AddPressGoodies(pressItemsListByDate);

        return pressItemsListByDate;
    }

    private void AddPressEditions(List<PressItemsList> pressItems) {

        List<Edition> editionsList = svcEditions.getPress();

        PressItem pressItem;

        String name;

        for(Edition edition : editionsList) {

            name = edition.getName();

            if (edition.getEditionNumber() != null) {
                name = edition.getEditionNumber() + " - " + name;
            }else if (edition.getOrderNumber() != null) {
                name = edition.getOrderNumber() + " - " + name;
            }

            pressItem = new PressItem();
            pressItem.setEdition(edition);
            pressItem.setGoody(null);
            pressItem.setIdSerie(edition.getIdSerie());
            pressItem.setIdEditor(edition.getIdEditor());
            pressItem.setItemName(name);
            pressItem.setSerieName(edition.getSerieName());
            pressItem.setEditorName(edition.getEditorName());
            pressItem.setParutionDate(edition.getParutionDate());
            pressItem.setPossessionState(edition.getPossessionState());

            AddPressItem(pressItems, pressItem);
        }
    }

    private void AddPressGoodies(List<PressItemsList> pressItems) {

        List<Goody> goodiesList = svcGoodies.getPress();

        PressItem pressItem;

        for(Goody goody : goodiesList) {

            pressItem = new PressItem();
            pressItem.setEdition(null);
            pressItem.setGoody(goody);
            pressItem.setIdSerie(goody.getIdSerie());
            pressItem.setIdEditor(goody.getIdEditor());
            pressItem.setItemName(goody.getName());
            pressItem.setSerieName(goody.getSerieName());
            pressItem.setEditorName(goody.getEditorName());
            pressItem.setParutionDate(goody.getParutionDate());
            pressItem.setPossessionState(goody.getPossessionState());

            AddPressItem(pressItems, pressItem);
        }
    }

    private void AddPressItem(List<PressItemsList> pressItems, PressItem pressItem) {

        SqlDate dte = pressItem.getParutionDate();
        int i = 0;

        while ((i < pressItems.size()) && (compareSqlDate(dte, pressItems.get(i).getParutionDate()) < 0)) {
            i++;
        }

        if (i >= pressItems.size()) {
            pressItems.add(new PressItemsList(dte));
        } else if (compareSqlDate(dte, pressItems.get(i).getParutionDate()) > 0) {
            pressItems.add(i, new PressItemsList(dte));
        }

        pressItems.get(i).getPressItemsList().add(pressItem);
    }

    private int compareSqlDate(SqlDate d1, SqlDate d2) {

        int result;

        if ((d1 == null) && (d2 == null)) {
            result = 0;
        } else if (d1 == null) {
            result = -1;
        } else if (d2 == null) {
            result = 1;
        } else {
            result = d1.compareTo(d2);
        }

        return result;
    }
}