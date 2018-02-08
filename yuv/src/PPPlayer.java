import javax.swing.*;
import java.io.File;
import java.io.IOException;
import ui.*;
import yuv.*;

public class PPPlayer {
    public static void main(String[] args) throws IOException {
        YUVCoder coder = new YUVCoder();
        //YUVData data = coder.getYUVData("./image/lena_256x256_yuv420p.yuv");
        //data.Save("./image/lena_256x256_yuv420p.yuv");
        coder.deleteColorFromYUV("./image/lena_256x256_yuv420p.yuv", "./image/lena_256x256_yuv420p_blackwhite.yuv");

        PlayerFrame frame = new PlayerFrame("PP播放器", 600, 400);
        frame.setVisible(true);
    }
}
