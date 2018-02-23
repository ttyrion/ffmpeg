package h264;

//NALU Header中表示优先级的信息，占2bits
enum NALUPriority {
    DISPOS,
    LOW,
    HIGN,
    HIGNEST  //优先级最高
}

//NALU Header（8bits）中的低5位bit:标识NALU类型信息（13-23保留， 24-31未使用）
enum NALUType {
    NONE,   //未使用
    SLICE,  //未使用Data Partition、非IDR图像的Slice
    DPA,    //使用Data Partition, 且为Slice A
    DPB,    //使用Data Partition, 且为Slice B
    DPC,    //使用Data Partition, 且为Slice C
    IDR,    //IDR图像中的Slice
    SEI,    //补充增强信息单元
    SPS,    //序列参数集（Sequence Parameter Set）
    PPS,    //图像参数集（Picture Parameter Set）
    AUD,    //分割器，紧随AUD，一般是SPS/PPS/SEI/IDR的组合或者简单就是一个SLICE，也就是一个帧的开始
    EOSEQ,   //序列结束
    EOSTREAM,  //码流结束
    FILL;    //填充
}

public class NALUnit {
    public int forbidden;   //H264定义forbidden_bit必须为0
    public NALUPriority priority;
    public NALUType type;
    public long startByte;
    public long endByte;
    public byte[] data;

    public String toString() {
        String expr = new String();
        expr += "ForbiddenBit:" + forbidden + " " +
                "Priority:" + priority.ordinal() + " " +
                "Type:" + type.toString() + " " +
                "Begin:" + startByte + " " +
                "End:" + endByte + " " +
                "Length:" + (endByte - startByte + 1);

        return expr;
    }
}
