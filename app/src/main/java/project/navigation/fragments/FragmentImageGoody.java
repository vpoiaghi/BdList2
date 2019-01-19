package project.navigation.fragments;

import java.util.Locale;

import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentImage;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 25/04/2016.
 */
public class FragmentImageGoody extends FragmentImage {

    @Override
    public String getImageFilePath() {

        final Long idGoody = (Long) getParameterIn(ParametersCodes.ID_GOODY);

        String filePath = null;

        if (idGoody != null) {
            filePath = Constantes.PATH_GOODIES + String.format(Locale.FRANCE, "%06d", idGoody) + ".jpg";
        }

        return filePath;
    }

}
