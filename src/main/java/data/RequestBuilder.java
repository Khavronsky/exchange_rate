package data;

public class RequestBuilder {
    private static final String SERVER_URL = "https://api.fixer.io/latest";

    public static String createRequest(String base, String target) {
        return SERVER_URL + "?base=" + base + "&symbols=" + target;
    }
}
