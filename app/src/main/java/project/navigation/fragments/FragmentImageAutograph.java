package project.navigation.fragments;

import java.util.Locale;

import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentImage;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 25/04/2016.
 */
public class FragmentImageAutograph extends FragmentImage {

    @Override
    public String getImageFilePath() {

        final Long idAutograph = (Long) getParameterIn(ParametersCodes.ID_AUTOGRAPH);

        String filePath = null;

        if (idAutograph != null) {
            filePath = Constantes.PATH_AUTOGRAPHS + String.format(Locale.FRANCE, "%05d", idAutograph) + "-01.jpg";
        }

        return filePath;
    }

}
