package project.navigation.fragments;

import java.util.Locale;

import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentImage;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 25/04/2016.
 */
public class FragmentImageEdition extends FragmentImage {

    @Override
    public String getImageFilePath() {

        final Long idEdition = (Long) getParameterIn(ParametersCodes.ID_EDITION);

        String filePath = null;

        if (idEdition != null) {
            filePath = Constantes.PATH_COVERS + String.format(Locale.FRANCE, "%06d", idEdition) + ".jpg";
        }

        return filePath;
    }

}
