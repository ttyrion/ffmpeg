package h264;
import java.util.*;

/*
*H264的码流的打包方式有两种：
* 1、annex-b byte stream format 的格式
* 这个是绝大部分编码器的默认输出格式，就是每个帧的开头的3~4个字节是H264的start_code,0x00000001或者0x000001。
* 2、原始的NAL打包格式
* 就是开始的若干字节（1，2，4字节）是NAL的长度，而不是start_code,此时必须借助某个全局的数据来获得编码器的profile,level,PPS,SPS等信息才可以解码。
*/

enum StartCodeType {
    NONE, //非start code
    OTHERSTART,  //0x000001
    FRAMESTART  //一帧的开始, 0x00000001
}

public class H264Coder {
    ArrayList<NALUnit> getNALUList(byte[] buffer) {
        ArrayList<NALUnit> naluList = new ArrayList<NALUnit>();
        int lastStartCodePosition = -1;
        int lastStartCodeBytes = 0;
        for(int i = 0; i < buffer.length;) {
            StartCodeType type = encounterStartCode(buffer, i);
            switch (type) {
                case NONE: {
                    ++i;
                    continue;
                }
                case FRAMESTART:
                case OTHERSTART: {
                    int startCodeBytes = type.ordinal() + 2;
                    if (lastStartCodePosition != -1){
                        //lastStartCodePosition到i之间是上一个NALU
                        NALUnit unit = new NALUnit();
                        unit.forbidden = (byte)((buffer[lastStartCodePosition+lastStartCodeBytes] >> 7) & 0x01);
                        unit.priority = NALUPriority.values()[((buffer[lastStartCodePosition+lastStartCodeBytes] >> 5) & 0x03)];
                        unit.type = NALUType.values()[((buffer[lastStartCodePosition+lastStartCodeBytes]) & 0x1f)];
                        //lastStartCodePosition+3+1 到 i 之间（左闭右开区间）的数据就是该NALU的RBSP
                        int rbspStart = lastStartCodePosition+lastStartCodeBytes+1;
                        byte[] rbsp = new byte[i - rbspStart];
                        System.arraycopy(buffer, rbspStart, rbsp, 0, rbsp.length);
                        unit.data = rbsp;
                        unit.startByte = lastStartCodePosition+lastStartCodeBytes;
                        unit.endByte = i - 1;

                        naluList.add(unit);
                    }
                    lastStartCodePosition = i;
                    lastStartCodeBytes = startCodeBytes;
                    i += startCodeBytes;
                    continue;
                }
            }
        }

        return naluList;
    }

    StartCodeType encounterStartCode(byte[] buffer, int pos) {
        if ((pos+3 < buffer.length) && (buffer[pos] == 0 && buffer[pos+1] == 0 && buffer[pos+2] == 0 && buffer[pos+3] == 1)) {
            return StartCodeType.FRAMESTART;
        }
        else if ((pos+2 < buffer.length) && (buffer[pos] == 0 && buffer[pos+1] == 0 && buffer[pos+2] == 1)) {
            return StartCodeType.OTHERSTART;
        }

        return StartCodeType.NONE;
    }
}
