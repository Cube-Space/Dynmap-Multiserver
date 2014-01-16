package net.cubespace.dynmap.multiserver.Dynmap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ConfigJSPrepare {
    public static String convertToJson(String jsObject) {
        return jsObject.replace("var config = ", "")
                .replace(";", "")
                .replace("\n", "")
                .replaceAll(" ", "")
                .replaceAll("'", "\"")
                .replaceAll("(?!\")([a-z]+):(?<!\")", "\"$1\":");
    }
}
