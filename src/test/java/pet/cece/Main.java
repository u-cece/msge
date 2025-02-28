package pet.cece;

public class Main {
    public static void main(String[] args) {
        MSGEServer server = new MSGEServer.Builder()
                .hostname("127.0.0.1")
                .port((short) 6969)
                .build();
        server.start();
        while (server.isRunning()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}