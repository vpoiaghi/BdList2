package project.donnees.wrapper.insigning;

import android.graphics.Color;
import android.support.annotation.NonNull;

import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.InSigning;

/**
 * Created by VINCENT on 23/12/2018.
 *
 */
public class PeriodEditor extends UsedPeriod {

    @NonNull final InSigning inSigning;

    public PeriodEditor(@NonNull final InSigning inSigning) {
        super(getText(inSigning), inSigning.getBegin().getDate(), inSigning.getEnd().getDate());
        this.inSigning = inSigning;
    }

    // Function static nécessaire pour pouvoir définir dynamiquemnt la valeur du paramètre
    // name passer au constructeur de la classe mère
    private static String getText(@NonNull final InSigning inSigning) {
        final Editor editor = inSigning.getEditor();
        return (editor == null ? "" : editor.toString());
    }

    @Override
    public int getColor() {

        final Editor editor = inSigning.getEditor();
        //final long editorsCount = svcEditor.getLastId();
        final long editorsCount = 131;

        int c = (int) (Math.floor(Color.BLACK / editorsCount * (editor.getId() - 1)));

        if (c < Color.BLACK) {
            c = Color.BLACK;
        }

        if (c > Color.WHITE) {
            c = Color.WHITE;
        }


        return c;
    }

    @Override
    public String getComments() {
        return inSigning.getComments();
    }

    public InSigning getInSigning() {
        return inSigning;
    }

}
