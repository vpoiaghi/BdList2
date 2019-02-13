package project.services.abs;

import project.services.ServiceBlueAngel82Books;

/**
 * Created by VINCENT on 10/02/2019.
 *
 */
public interface IAsyncServiceListener {
    void asyncServiceCallBack(int status, ServiceBlueAngel82Books.ResultEdition result);
}
