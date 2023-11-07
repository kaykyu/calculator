import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {return socket;}

    @Override
    public void run() {
        try {
        start();
        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void start() throws IOException {
        
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        String line = "";
        List<String> calculate = new ArrayList<>();

        while (!line.equals("quit")) {

            line = br.readLine();
            System.out.printf("Client's request: %s\n", line);
            String request[] = line.trim().split(" ");
            
            for (String s : request) {
                calculate.add(s);
            }

            if (calculate.size() < 3) {
                System.out.println("Invalid command");
                line = "quit";
            }

            int num1 = Integer.parseInt((calculate.remove(calculate.size()-2)));
            int num2 = Integer.parseInt((calculate.remove(calculate.size()-2)));
            int newNum = 0;

            switch (calculate.removeLast()) {
                case "+" : newNum = num1 + num2; 
                            break;
                case "-" : newNum = num1 - num2; 
                            break;
                case "*" : newNum = num1 * num2;
                            break;
                case "/" : newNum = num1 / num2;
                            break;
            }

            calculate.addLast(String.format("%d", newNum));
            bw.write(calculate.toString() + "\n");
            bw.flush();
        }
                    
        System.out.println("Client disconnected");
        is.close();
        os.close(); 
        socket.close();
    }
    
}