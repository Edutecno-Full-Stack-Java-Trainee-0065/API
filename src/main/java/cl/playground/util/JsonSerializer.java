package cl.playground.util;

import cl.playground.modelo.Publicacion;

import java.util.List;

public class JsonSerializer {

    public static String toJsonArray(List<Publicacion> publicaciones) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < publicaciones.size(); i++) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append("    ").append(publicaciones.get(i).toJson().replace("\n", "\n    "));
        }
        sb.append("\n]");
        return sb.toString();
    }
}
