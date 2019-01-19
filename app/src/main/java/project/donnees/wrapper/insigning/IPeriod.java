package project.donnees.wrapper.insigning;

import java.util.Date;

/**
 * Created by VINCENT on 24/12/2018.
 *
 */
public interface IPeriod extends ICell {

    Date getBeginning();
    void setBeginning(Date date);
    Date getEnd();
    void setEnd(Date date);

}
