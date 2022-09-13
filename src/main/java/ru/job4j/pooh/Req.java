package ru.job4j.pooh;

public class Req {
    public final static String GET = "GET";
    public final static String POST = "POST";
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] values = parse(content);
        return new Req(values[0], values[1], values[2], values[3]);
    }

    private static String[] parse(String content) {
        String[] values = content.split("/", 4);
        String[] arr = values[2].split(" ", 2);
        String type = values[0].trim();
        String mode = values[1];
        String name = arr[0];
        String param;
        if (GET.equals(type) && "queue".equals(mode)) {
            param = "";
        } else if (GET.equals(type) && "topic".equals(mode)) {
            String[] getTopic = values[3].split(" ", 2);
            param = getTopic[0];
        } else if (POST.equals(type)) {
            String[] postQueue = content.split(System.lineSeparator());
            param = validate(postQueue[postQueue.length - 1]);
        } else {
            throw new IllegalArgumentException();
        }
        String[] result = new String[4];
        result[0] = type;
        result[1] = mode;
        result[2] = name;
        result[3] = param;
        return result;
    }

    private static String validate(String name) {
        if (!name.contains("=")) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain the symbol \"=\"", name));
        }
        if (name.startsWith("=")) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain a key", name));
        }
        if (name.indexOf("=") == name.length() - 1) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain a value", name));
        }
        return name;
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
