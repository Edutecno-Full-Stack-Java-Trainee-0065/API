package cl.playground.modelo;

public class ApiResponse {
    private String message;
    private IssPosition iss_position;
    private String timestamp;

    public ApiResponse(String message, IssPosition iss_position, String timestamp) {
        this.message = message;
        this.iss_position = iss_position;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IssPosition getIss_position() {
        return iss_position;
    }

    public void setIss_position(IssPosition iss_position) {
        this.iss_position = iss_position;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", iss_position=" + iss_position +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
