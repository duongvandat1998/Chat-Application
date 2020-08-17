package sendfile.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OnlineListThread implements Runnable {

    MainForm main;

    public OnlineListThread(MainForm main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) //Nếu mà luồng thread không bị gián đoạn
            {
                String msg = "";
                for (int x = 0; x < main.clientList.size(); x++) {
                    msg = msg + " " + main.clientList.elementAt(x);
                }

                for (int x = 0; x < main.socketList.size(); x++) {
                    Socket tsoc = (Socket) main.socketList.elementAt(x);
                    DataOutputStream dos = new DataOutputStream(tsoc.getOutputStream());
                    /*
                      CMD_ONLINE [user1] [user2] [user3] *
                     */
                    if (msg.length() > 0) {
                        dos.writeUTF("CMD_ONLINE " + msg);
                    }
                }
//Phương thức sleep() của lớp Thread được sử dụng để tạm ngứng một thread cho một khoảng thời gian nhất định.
                Thread.sleep(1900);
            }
        } catch (InterruptedException e) {
            main.appendMessage("[InterruptedException]: " + e.getMessage());
        } catch (IOException e) {
            main.appendMessage("[IOException]: " + e.getMessage());
        }
    }

}
