package rgb;

enum BitmapHeaderSize {
    BitmapFileHeaderSize(14),
    BitmapInfoHeaderSize(40);

    public int value(){
        return this.value;
    }

    private BitmapHeaderSize(int value) {

    }
    private  int value;
}

class BitmapFileHeader {  //14字节
    BitmapFileHeader() {
        type = 0x424D;
        size = 0;
        reserved1 = 0;
        reserved2 = 0;
        offset = 0;
    }

    short type; //位图文件类型
    int size;  //位图文件大小
    short reserved1;
    short reserved2; //两个保留字，必须为0
    int offset; //位图数据的起始位置
}

class BitmapInfoHeader {
    BitmapInfoHeader() {
        size = 0x28000000;
        width = 0;
        heigth = 0;
        planes = 0x0100;
        bitsPerPixcel = 0x0800;
        compress = 0;
        imageSize = 0;
        xPixcelsPerMeter = 0;
        yPixcelsPerMeter = 0;
        colorUsed = 0;
        colorImportant = 0;
    }

    int size; //本结构字节数， 值为0x 28 00 00 00 （小端对齐，40）
    int width;
    int heigth; //位图宽度和高度，以像素为单位
    short planes; //目标设备颜色平面数，值为1
    short bitsPerPixcel; // 每个像素所需的位数：必须是1（双色），4(16色），8(256色）16(高彩色)或24（真彩色）之一
    int compress;  //必须是0: 不压缩;1:BI_RLE8压缩类型；2:BI_RLE4压缩类型
    int imageSize; //位图大小，包含为了补齐而添加的空字节
    int xPixcelsPerMeter;
    int yPixcelsPerMeter; //位图水平竖直分辨率：每米像素数
    int colorUsed; //位图实际使用的颜色表中的颜色数
    int colorImportant; //位图显示过程中重要的颜色数
}

public class Bitmap {
    public byte[] getBmpBuffer(byte[] rgbBuffer) {
        //给rgbbuffer增加文件头，信息头

    }

    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private byte[] rgb;
}
