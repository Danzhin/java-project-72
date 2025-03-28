package hexlet.code.utils;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlPath() {
        return "/urls/{id}";
    }

    public static String urlPath(int id) {
        return "/urls/" + id;
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlChecksPath() {
        return "/urls/{id}/checks";
    }

    public static String urlCheckPath(int id) {
        return "/urls/" + id + "/checks";
    }

}
