import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import ui.*;
import yuv.*;

public class PPPlayer {
    public static void main(String[] args) throws IOException {
        String yuvFile = "./image/lena_256x256_yuv420p.yuv";
        byte[] buffer;
        File file = new File(yuvFile);
        if (file.exists()) {
            buffer = new byte[(int)file.length()];
            FileInputStream ifstream = new FileInputStream(file);
            int bytesOffset = 0;
            int bytesRead;
            while(bytesOffset < file.length() &&(bytesRead = ifstream.read(buffer, bytesOffset, 1024)) != -1) {
                bytesOffset += bytesRead;
            }
            ifstream.close();

            YUV420PCoder coder = new YUV420PCoder();
            /*
            coder.eliminateUV(buffer);
            String[] names = yuvFile.split("\\.");
            String eliminateFilePath = new String();
            for (int i = 0; i < names.length - 1; ++i) {
                eliminateFilePath += names[i];
                eliminateFilePath += ".";
            }
            eliminateFilePath += "bw." + names[names.length - 1];
            file = new File(eliminateFilePath);
            FileOutputStream ofstream = new FileOutputStream(file);
            ofstream.write(buffer);
            ofstream.close();
            */

            byte[] rgb24 = coder.toRGB24(buffer);

            String[] names = yuvFile.split("\\.");
            String rgbFilePath = new String();
            for (int i = 0; i < names.length - 1; ++i) {
                rgbFilePath += names[i];
                rgbFilePath += ".";
            }
            rgbFilePath += "rgb";
            file = new File(rgbFilePath);
            FileOutputStream ofstream = new FileOutputStream(file);
            ofstream.write(rgb24);
            ofstream.close();
        }

        //PlayerFrame frame = new PlayerFrame("PP播放器", 600, 400);
        //frame.setVisible(true);
    }
}
