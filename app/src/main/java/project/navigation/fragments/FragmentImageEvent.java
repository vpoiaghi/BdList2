package project.navigation.fragments;

import java.util.Locale;

import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentImage;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 25/04/2016.
 */
public class FragmentImageEvent extends FragmentImage {

    @Override
    public String getImageFilePath() {

        final Long idEvent = (Long) getParameterIn(ParametersCodes.ID_GLOBAL_EVENT);

        String filePath = null;

        if (idEvent != null) {
            filePath = Constantes.PATH_EVENTS + String.format(Locale.FRANCE, "%06d", idEvent) + ".jpg";
        }

        return filePath;
    }

}