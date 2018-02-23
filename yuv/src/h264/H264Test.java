package h264;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class H264Test {
    public static void main(String[] args) throws IOException{
        String h264File = "./image/sintel.h264";
        byte[] buffer;
        File file = new File(h264File);
        if (file.exists()) {
            buffer = new byte[(int) file.length()];
            FileInputStream ifstream = new FileInputStream(file);
            //int bytesOffset = 0;
            //int bytesRead;
            //while (bytesOffset < file.length() && (bytesRead = ifstream.read(buffer, bytesOffset, 1024)) != -1) {
            //    bytesOffset += bytesRead;
            //}
            ifstream.read(buffer);
            ifstream.close();

            H264Coder coder = new H264Coder();
            ArrayList<NALUnit> naluList = coder.getNALUList(buffer);
            System.setOut(new PrintStream("./image/sintel.h264.nalu"));
            for (NALUnit unit : naluList) {
                System.out.println(unit.toString());
            }

            System.setOut(System.out);
        }
    }
}
