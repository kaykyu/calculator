import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MTServer {

   public static final Integer DEFAULT_PORT = 3000;

   public static void main(String[] args) throws Exception {

      int port = DEFAULT_PORT;

      System.out.printf("Starting threaded server on port %d\n", port);
      ServerSocket server = new ServerSocket(port);

      // Create a threadpool
      ExecutorService thrPool = Executors.newFixedThreadPool(2);

      while (true) {
         // Main thread waits for a new connection
         Socket client = server.accept();
         System.out.println("New client connection");
         // waiting for new connection, handling the client
         // pass the client to the thread/worker

         Runnable handler = new Server(client);
         thrPool.submit(handler);
      }
   }
}