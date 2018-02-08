package yuv;
import java.io.*;
import java.util.Arrays;


public class YUV420PCoder {
    //分离YUV420P数据中的y,u,v分量
    public YUVData getYUVData(byte[] yuvBuffer) {
        YUVData data = new YUVData();
        //根据YUV存储格式，计算Y分量和UV分量字节数
        int yBytes = (int)yuvBuffer.length * 2 / 3;
        int uvBytes = yBytes / 4;
        data.y = new byte[yBytes];
        System.arraycopy(yuvBuffer, 0, data.y, 0, data.y.length);
        data.u = new byte[uvBytes];
        System.arraycopy(yuvBuffer, yBytes, data.u, 0, data.u.length);
        data.v = new byte[uvBytes];
        System.arraycopy(yuvBuffer, data.y.length + data.u.length, data.v, 0, data.v.length);

        return data;
    }

    //消除YUV420P数据中的UV分量，也就是生成一张黑白图片
    public void eliminateUV(byte[] yuvBuffer) {
        //think why 128 is filled
        Arrays.fill(yuvBuffer, (int)yuvBuffer.length * 2 / 3, yuvBuffer.length, (byte)128);
    }

    //把YUV420P数据格式转成RGB24格式
    public byte[] toRGB24(byte[] yuvBuffer) {
        YUVData yuv = getYUVData(yuvBuffer);
        int pixcels = (int)yuv.y.length;
        byte[] rgb = new byte[pixcels * 3];
        for (int i = 0; i < pixcels; ++i) {
            int C = ((int)(yuv.y[i] & 0xff) - 16);
            int D = ((int)(yuv.u[i / 4] & 0xff) - 128);
            int E = ((int)(yuv.v[i / 4] & 0xff) - 128);
            //R
            rgb[i*3+2] = (byte)clip((298 * C + 409 * E + 128) >> 8);
            //rgb[i*3] = (byte)clip(((yuv.y[i] & 0xff) + (91881 * ((yuv.v[i / 4] & 0xff)) >> 16) - 179 ));
            //G
            rgb[i*3+1] = (byte)clip((298 * C - 100 * D - 208 * E + 128) >> 8);
            //rgb[i*3+1] = (byte)clip(((yuv.y[i] & 0xff) - ((22544 * (yuv.u[i / 4] & 0xff) + 46793 *(yuv.v[i / 4] & 0xff) )>>16)));
            //B
            rgb[i*3] = (byte)clip((298 * C + 516 * D + 128) >> 8);
            //rgb[i*3+2] = (byte)clip(((yuv.y[i] & 0xff) + ((116129* (yuv.u[i / 4] & 0xff))>>16)) - 226);
        }

        return rgb;
    }

    private int clip(int value) {
        if (value <= 0 ) {
            return 0;
        }
        if (value >= 255) {
            return 255;
        }

        return value;
    }
}
