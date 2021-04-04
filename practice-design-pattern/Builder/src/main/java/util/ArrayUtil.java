package util;


public class ArrayUtil {
    public static byte[] joinArrays(byte[]... arrays){
        int cnt = 0;
        for (byte[] byteArray : arrays){
            cnt += byteArray.length;
        }
        byte[] result = new byte[cnt];
        for (byte[] byteArray: arrays){
            for(int i=0; i<byteArray.length; i++)
                result[i] = byteArray[i];
        }
        return result;
    }
}
