package TemaTest;

public class CommandResponse {
    String status;
    String message;
    public CommandResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public String toString() {
        return "{'status':'"+ status +"','message':'"+ message +"'}";
    }
}
