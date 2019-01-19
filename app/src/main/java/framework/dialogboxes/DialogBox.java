package framework.dialogboxes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 *
 * Created by VINCENT on 21/12/2015.
 */
public class DialogBox {

    final IDialogBoxListener listener;
    final AlertDialog alertDialog;

    public DialogBox(final Activity owner) {
        this(null, owner);
    }

    public DialogBox(final IDialogBoxListener listener, final Activity owner) {

        this.listener = listener;
        this.alertDialog = new AlertDialog.Builder(owner).create();
    }

    public void showInformation(final String title, final String message) {

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        addButton(AlertDialog.BUTTON_NEUTRAL, "OK");

        alertDialog.show();
    }

    public void showWarning(final String title, final String message) {

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        addButton(AlertDialog.BUTTON_NEUTRAL, "OK");

        alertDialog.show();
    }

    public void showYesNoCancelQuestion(final String title, final String question) {

        alertDialog.setTitle(title);
        alertDialog.setMessage(question);

        addButton(AlertDialog.BUTTON_POSITIVE, "Oui");
        addButton(AlertDialog.BUTTON_NEGATIVE, "Non");
        addButton(AlertDialog.BUTTON_NEUTRAL, "Cancel");

        alertDialog.show();
    }

    private void addButton(final int buttonType, final String buttonText) {

        alertDialog.setButton(buttonType, buttonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (listener != null) {
                            listener.callback(buttonType);
                        }
                    }
                });

    }
}