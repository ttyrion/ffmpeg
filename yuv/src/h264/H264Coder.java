package h264;

public class H264Coder {
    NALUnit[] getNALUList(byte[] buffer) {
        NALUnit[] naluList = null;


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
