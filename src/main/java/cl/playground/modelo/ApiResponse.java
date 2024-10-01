package cl.playground.modelo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiResponse {
    private Map<String, Object> data;

    public ApiResponse() {
        this.data = new LinkedHashMap<>();
    }

    public void addData(String key, Object value) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = data;

        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new LinkedHashMap<>());
        }
        current.put(keys[keys.length - 1], value);
    }

    public Object getData(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = data;

        for (int i = 0; i < keys.length - 1; i++) {
            if (!(current.get(keys[i]) instanceof Map)) {
                return null;
            }
            current = (Map<String, Object>) current.get(keys[i]);
        }
        return current.get(keys[keys.length - 1]);
    }

    public Map<String, Object> getAllData() {
        return data;
    }

    @Override
    public String toString() {
        return jsonLikeString(data, 0);
    }

    private String jsonLikeString(Object obj, int indentLevel) {
        if (obj instanceof Map) {
            return mapToString((Map<?, ?>) obj, indentLevel);
        } else if (obj instanceof List) {
            return listToString((List<?>) obj, indentLevel);
        } else {
            return obj.toString();
        }
    }

    private String mapToString(Map<?, ?> map, int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(indentLevel);
        String innerIndent = "  ".repeat(indentLevel + 1);

        sb.append("{\n");
        int size = map.size();
        int count = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            count++;
            sb.append(innerIndent).append("\"").append(entry.getKey()).append("\": ");
            sb.append(jsonLikeString(entry.getValue(), indentLevel + 1));
            if (count < size) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append(indent).append("}");
        return sb.toString();
    }

    private String listToString(List<?> list, int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(indentLevel);
        String innerIndent = "  ".repeat(indentLevel + 1);

        sb.append("[\n");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(innerIndent).append(jsonLikeString(list.get(i), indentLevel + 1));
            if (i < size - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append(indent).append("]");
        return sb.toString();
    }
}
