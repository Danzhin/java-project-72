package hexlet.code.utils;

public class Routes {

    public static final String ROOT_PATH = "/";

    public static final String URLS_PATH = "/urls";

    public static final String URL_PATH = "/urls/{id}";

    public static final String URLS_CHECKS_PATH = "/urls/{id}/checks";

    public static String urlPath(int id) {
        return "/urls/" + id;
    }

    public static String urlCheckPath(int id) {
        return "/urls/" + id + "/checks";
    }

}
