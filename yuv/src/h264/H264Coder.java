package h264;
import java.util.*;

public class H264Coder {
    ArrayList<NALUnit> getNALUList(byte[] buffer) {
        ArrayList<NALUnit> naluList = new ArrayList<NALUnit>();
        int lastStartCodePosition = -1;
        for(int i = 0; i < buffer.length;) {
            if (encounterStartCode3(buffer, i)) {
                if (lastStartCodePosition == -1) {
                    lastStartCodePosition = i;
                }
                else {
                    //lastStartCodePosition到i之间是上一个NALU
                    NALUnit unit = new NALUnit();
                    unit.forbidden = (byte)((buffer[lastStartCodePosition+3] >> 7) & 0x01);
                    unit.priority = NALUPriority.values()[((buffer[lastStartCodePosition+3] >> 5) & 0x03)];
                    unit.type = NALUType.values()[((buffer[lastStartCodePosition+3]) & 0x1f)];
                    //lastStartCodePosition+3+1 到 i 之间（左闭右开区间）的数据就是该NALU的RBSP
                    int rbspStart = lastStartCodePosition+3+1;
                    byte[] rbsp = new byte[i - rbspStart];
                    System.arraycopy(buffer, rbspStart, rbsp, 0, rbsp.length);
                    unit.data = rbsp;
                    unit.startByte = rbspStart - 1;
                    unit.endByte = i - 1;

                    naluList.add(unit);
                }
                i += 3;
            }
            else {
                if (encounterStartCode4(buffer, i)) {
                    if (lastStartCodePosition == -1) {
                        lastStartCodePosition = i;
                    }
                    else {
                        //lastStartCodePosition到i之间是上一个NALU
                        NALUnit unit = new NALUnit();
                        unit.forbidden = (byte)((buffer[lastStartCodePosition+4] >> 7) & 0x01);
                        unit.priority = NALUPriority.values()[((buffer[lastStartCodePosition+4] >> 5) & 0x03)];
                        unit.type = NALUType.values()[((buffer[lastStartCodePosition+4]) & 0x1f)];
                        //lastStartCodePosition+4+1 到 i 之间（左闭右开区间）的数据就是该NALU的RBSP
                        int rbspStart = lastStartCodePosition+4+1;
                        byte[] rbsp = new byte[i - rbspStart];
                        System.arraycopy(buffer, rbspStart, rbsp, 0, rbsp.length);
                        unit.data = rbsp;
                        unit.startByte = rbspStart - 1;
                        unit.endByte = i - 1;

                        naluList.add(unit);
                    }
                    i += 4;
                }
            }

            ++i;
        }

        return naluList;
    }

    //start code: 0x000001
    boolean encounterStartCode3(byte[] buffer, int pos) {
        if (buffer[pos] == 0 && buffer[pos+1] == 0 && buffer[pos+2] == 1) {
            return true;
        }

        return false;
    }

    //start code: 0x00000001
    boolean encounterStartCode4(byte[] buffer, int pos) {
        if (buffer[pos] == 0 && buffer[pos+1] == 0 && buffer[pos+2] == 0 && buffer[pos+3] == 1) {
            return true;
        }

        return false;
    }
}
