import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static final Integer DEFAULT_PORT = 3000;

    public static void main(String[] args) throws Exception {
        
        int port = DEFAULT_PORT;

        System.out.printf("Attempting to connect to %s\n", port);
        Socket socket = new Socket("localhost", port);

        System.out.println("Connected to server");

        Console cons = System.console();
        System.out.println("Reverse Polish calculator");
        String line = cons.readLine("> ");
            
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        while (!line.equals("quit")) {
            bw.write(line + "\n");
            bw.flush();
               
            line = br.readLine();
            System.out.println(line);
            line = cons.readLine("> ");
        }
            bw.write("quit\n");
            bw.flush();
            os.close();
            is.close();

            socket.close();
            System.out.println("Thank you for using calculator");
    }
}