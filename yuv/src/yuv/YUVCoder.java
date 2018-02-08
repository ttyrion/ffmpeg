package yuv;
import java.io.*;
import java.util.Arrays;


public class YUVCoder {
    public YUVData getYUVData(String yuvFile) throws IOException {
        YUVData data = new YUVData();
        File file = new File(yuvFile);
        if (file.exists()) {
            long size = file.length();
            byte[] buffer = new byte[((int) size)];
            FileInputStream ifstream = new FileInputStream(file);
            int offset = 0;
            int bytesRead;
            while(offset < size && (bytesRead = ifstream.read(buffer, offset, 1024)) != -1) {
                offset += bytesRead;
            }
            ifstream.close();
            System.out.println(yuvFile + " size=" + size);

            //根据YUV存储格式，计算Y分量和UV分量字节数
            int yBytes = (int)size * 2 / 3;
            int uvBytes = yBytes / 4;
            data.y = new byte[yBytes];
            System.arraycopy(buffer, 0, data.y, 0, data.y.length);
            data.u = new byte[uvBytes];
            System.arraycopy(buffer, yBytes, data.u, 0, data.u.length);
            data.v = new byte[uvBytes];
            System.arraycopy(buffer, data.y.length + data.u.length, data.v, 0, data.v.length);
        }

        return data;
    }

    public void deleteColorFromYUV(String yuvFile, String noColorYuvFile) throws IOException{
        File file = new File(yuvFile);
        if (file.exists()) {
            long size = file.length();
            byte[] buffer = new byte[((int) size)];
            FileInputStream ifstream = new FileInputStream(file);
            int offset = 0;
            int bytesRead;
            while(offset < size && (bytesRead = ifstream.read(buffer, offset, 1024)) != -1) {
                offset += bytesRead;
            }
            ifstream.close();

            //think why 128 is filled
            Arrays.fill(buffer, (int)size * 2 / 3, buffer.length, (byte)128);
            file = new File(noColorYuvFile);
            FileOutputStream ofstream = new FileOutputStream(file);
            ofstream.write(buffer);
            ofstream.close();
        }
    }
}
