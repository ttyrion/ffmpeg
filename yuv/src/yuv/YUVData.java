package yuv;
import java.io.*;

enum YUVFormat {
    YUV420P,
    YUV422P,
}

public class YUVData {
    //将yuv分量分别保存到与源文件同名的三个文件中
    public void Save(String path) throws IOException{
        String yFilePath = path + ".y";
        File file = new File(yFilePath);
        FileOutputStream ofstream = new FileOutputStream(file);
        ofstream.write(y, 0 , y.length);
        ofstream.close();

        String uFilePath = path + ".u";
        file = new File(uFilePath);
        ofstream = new FileOutputStream(file);
        ofstream.write(u, 0 , u.length);
        ofstream.close();

        String vFilePath = path + ".v";
        file = new File(vFilePath);
        ofstream = new FileOutputStream(file);
        ofstream.write(v, 0 , v.length);
        ofstream.close();
    }

    public byte[] y;
    public byte[] u;
    public byte[] v;
    //YUVFormat format;
}