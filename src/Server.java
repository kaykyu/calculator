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
            
            if (request.length > 1) {
                    for (int i = 0; i < (request.length - 1); i++) {
                    calculate.add(request[i]);
                }
            }

            float num1 = Float.parseFloat((calculate.remove(calculate.size()-1)));
            float num2 = Float.parseFloat((calculate.remove(calculate.size()-1)));
            float newNum = 0;

            switch (request[(request.length - 1)]) {
                case "+" : newNum = num1 + num2; 
                            break;
                case "-" : newNum = num1 - num2; 
                            break;
                case "*" : newNum = num1 * num2;
                            break;
                case "/" : newNum = num1 / num2;
                            break;
                default : calculate.add(String.format("%.2f", num2));
                            calculate.add(String.format("%.2f", num1));
                            calculate.add(String.format("%.2f", request[(request.length - 1)]));
            }

            if (newNum != 0) {
                calculate.add(String.format("%.2f", newNum));
            }
            bw.write(calculate.toString() + "\n");
            bw.flush();
        }
                    
        System.out.println("Client disconnected");
        is.close();
        os.close(); 
        socket.close();
    }
    
}