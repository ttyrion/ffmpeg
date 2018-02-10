package rgb;

enum BitmapHeaderSize {
    BitmapFileHeaderSize(14),
    BitmapInfoHeaderSize(40);

    public int value(){
        return this.value;
    }

    private BitmapHeaderSize(int value) {
        this.value = value;
    }
    private  int value;
}

class BitmapFileHeader {  //14字节
    BitmapFileHeader() {
        type = 0x4D42;
        size = 0;
        reserved1 = 0;
        reserved2 = 0;
        offset = 0;
    }

    //注意：int和short转成byte时，字节是倒序的
    //rgb数据已经在YUV420PCoder.toRGB24()内部被逆序过
    byte[] toByteBuffer() {
        byte[] buffer = new byte[BitmapHeaderSize.BitmapFileHeaderSize.value()];
        //type
        buffer[0] = (byte)(type & 0x00ff);
        buffer[1] = (byte)(type >> 8);
        //size
        buffer[2] = (byte)(size & 0x00ff);
        buffer[3] = (byte)((size >> 8) & 0x00ff);
        buffer[4] = (byte)((size >> 16) & 0x00ff);
        buffer[5] = (byte)(size >> 24);
        //reserved
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;
        //offset
        buffer[10] = (byte)(offset & 0x00ff);
        buffer[11] = (byte)((offset >> 8) & 0x00ff);
        buffer[12] = (byte)((offset >> 16) & 0x00ff);
        buffer[13] = (byte)(offset >> 24);

        return buffer;
    }

    short type; //位图文件类型
    int size;  //位图文件大小
    short reserved1;
    short reserved2; //两个保留字，必须为0
    int offset; //位图数据的起始位置
}

class BitmapInfoHeader {
    BitmapInfoHeader() {
        size = 40;
        width = 0;
        height = 0;
        planes = 1;
        bitsPerPixcel = 24;
        compress = 0;
        imageSize = 0;
        xPixcelsPerMeter = 0;
        yPixcelsPerMeter = 0;
        colorUsed = 0;
        colorImportant = 0;
    }

    byte[] toByteBuffer() {
        byte[] buffer = new byte[BitmapHeaderSize.BitmapInfoHeaderSize.value()];
        //size
        buffer[0] = (byte)(size & 0x00ff);
        buffer[1] = (byte)((size >> 8) & 0x00ff);
        buffer[2] = (byte)((size >> 16) & 0x00ff);
        buffer[3] = (byte)(size >> 24);
        //width
        buffer[4] = (byte)(width & 0x00ff);
        buffer[5] = (byte)((width >> 8) & 0x00ff);
        buffer[6] = (byte)((width >> 16) & 0x00ff);
        buffer[7] = (byte)(width >> 24);
        //height
        buffer[8] = (byte)(height & 0x00ff);
        buffer[9] = (byte)((height >> 8) & 0x00ff);
        buffer[10] =(byte)((height >> 16) & 0x00ff);
        buffer[11] =(byte)(height >> 24);
        //planes
        buffer[12] = (byte)(planes & 0x00ff);
        buffer[13] = (byte)((planes >> 8) & 0x00ff);
        //bitsPerPixcel
        buffer[14] = (byte)(bitsPerPixcel & 0x00ff);
        buffer[15] = (byte)((bitsPerPixcel >> 8) & 0x00ff);
        //compress
        buffer[16] = (byte)(compress & 0x00ff);
        buffer[17] = (byte)((compress >> 8) & 0x00ff);
        buffer[18] = (byte)((compress >> 16) & 0x00ff);
        buffer[19] = (byte)(compress >> 24);
        //imageSize
        buffer[20] = (byte)(imageSize & 0x00ff);
        buffer[21] = (byte)((imageSize >> 8) & 0x00ff);
        buffer[22] = (byte)((imageSize >> 16) & 0x00ff);
        buffer[23] = (byte)(imageSize >> 24);
        //xPixcelsPerMeter
        buffer[24] = (byte)(xPixcelsPerMeter & 0x00ff);
        buffer[25] = (byte)((xPixcelsPerMeter >> 8) & 0x00ff);
        buffer[26] = (byte)((xPixcelsPerMeter >> 16) & 0x00ff);
        buffer[27] = (byte)(xPixcelsPerMeter >> 24);
        //yPixcelsPerMeter
        buffer[28] = (byte)(yPixcelsPerMeter & 0x00ff);
        buffer[29] = (byte)((yPixcelsPerMeter >> 8) & 0x00ff);
        buffer[30] = (byte)((yPixcelsPerMeter >> 16) & 0x00ff);
        buffer[31] = (byte)(yPixcelsPerMeter >> 24);
        //colorUsed
        buffer[32] = (byte)(colorUsed & 0x00ff);
        buffer[33] = (byte)((colorUsed >> 8) & 0x00ff);
        buffer[34] = (byte)((colorUsed >> 16) & 0x00ff);
        buffer[35] = (byte)(colorUsed >> 24);
        //colorImportant
        buffer[36] = (byte)(colorImportant & 0x00ff);
        buffer[37] = (byte)((colorImportant >> 8) & 0x00ff);
        buffer[38] = (byte)((colorImportant >> 16) & 0x00ff);
        buffer[39] = (byte)(colorImportant >> 24);

        return buffer;
    }

    int size; //本结构字节数
    int width;
    int height; //位图宽度和高度，以像素为单位
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
    public Bitmap(byte[] rgb24Buffer, int width , int height) {
        rgb = rgb24Buffer;
        //设置文件头size和offset
        fileHeader = new BitmapFileHeader();
        int headerSize = BitmapHeaderSize.BitmapFileHeaderSize.value() + BitmapHeaderSize.BitmapInfoHeaderSize.value();
        fileHeader.size = rgb.length + headerSize;
        fileHeader.offset = headerSize;

        infoHeader = new BitmapInfoHeader();
        infoHeader.width = width;
        infoHeader.height =  height;
        infoHeader.imageSize = rgb.length;
    }

    public byte[] getBmpBuffer() {
        if (rgb == null) {
            return null;
        }
        //给rgbbuffer增加文件头，信息头
        byte[] buffer = new byte[fileHeader.size];
        byte[] fileHeaderBuffer = fileHeader.toByteBuffer();
        byte[] infoHeaderBuffer = infoHeader.toByteBuffer();
        System.arraycopy(fileHeaderBuffer, 0, buffer, 0, fileHeaderBuffer.length);
        System.arraycopy(infoHeaderBuffer, 0, buffer, fileHeaderBuffer.length, infoHeaderBuffer.length);
        System.arraycopy(rgb, 0, buffer, fileHeaderBuffer.length + infoHeaderBuffer.length, rgb.length);

        return buffer;
    }

    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private byte[] rgb;
}
