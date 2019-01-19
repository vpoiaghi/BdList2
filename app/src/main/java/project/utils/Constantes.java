package project.utils;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class Constantes {

    // Folders name
    public static final String ROOT_RESOURCE_FOLDER_NAME = "bdlist/";
    public static final String DATA_FOLDER_NAME = "donnees/";
    public static final String COVERS_FOLDER_NAME = "covers/";
    public static final String GOODIES_FOLDER_NAME = "goodies/";
    public static final String AUTOGRAPHS_FOLDER_NAME = "autographs/";
    public static final String EVENTS_FOLDER_NAME = "events/";

    // Folders path
    public static final String PATH_SD_CARD = "/storage/sdcard1/";
    public static final String PATH_APP_ROOT_RESOURCE = PATH_SD_CARD + ROOT_RESOURCE_FOLDER_NAME;
    public static final String PATH_DATA = PATH_APP_ROOT_RESOURCE + DATA_FOLDER_NAME;
    public static final String PATH_COVERS = PATH_DATA + COVERS_FOLDER_NAME;
    public static final String PATH_GOODIES = PATH_DATA + GOODIES_FOLDER_NAME;
    public static final String PATH_AUTOGRAPHS = PATH_DATA + AUTOGRAPHS_FOLDER_NAME;
    public static final String PATH_EVENTS = PATH_DATA + EVENTS_FOLDER_NAME;


    // Files name
    public static final String DATABASE_FILE_NAME = "bdlist.db";

    // Database File path
    public static final String PATH_DATABASE_FILE = PATH_DATA + DATABASE_FILE_NAME;

    // Application version
    public static final String APP_VERSION = "2.0";

}
