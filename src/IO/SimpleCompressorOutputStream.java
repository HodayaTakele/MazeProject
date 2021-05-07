package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream{

    private OutputStream out;
    private byte lastByte;
    private int byteCounter;
    private Object IOException;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.lastByte = (byte)0;
        this.byteCounter = 0;
    }

    /**
     * @param b array of byte that represent the array.
     * mazeByte[0] - represent the number of cells we use for maze information
     * if mazeByte[0] = 0 : we use the first 7 bytes for information.
     * if mazeByte[0] = 1 : we use the first 25 bytes for information.
     * @throws IOException for example if b is not as should be (the first index is not equal 0 or 1).
     */
    @Override
    public void write(byte[] b) throws IOException {
        /*super.write(b);*/
        int firstIndex = b[0];
        int startIndex;

        if(firstIndex==0){ startIndex = 7; }
        else if (firstIndex==1){ startIndex = 25; }
        else { throw new IOException(); }

        lastByte = b[startIndex-1];
        for (int i = 0; i < startIndex; i++) {
            this.out.write(b[i]);
        }
        for (int i = startIndex; i < b.length; i++)
        {
            if (Byte.compare(b[i], lastByte) == 0){
                byteCounter++;
                lastByte = b[i];
            }
            else {
                lastByte = b[i];
                write(byteCounter);
                byteCounter = 1;
            }
        }
    }

    /**
     * @param byteCounter - Represent the number of appearances in a sequence of a particular digit .
     * if byteCounter > 255 : The algorithm divides the number so that each digit is separated by zero and of course not greater than 255 .
     * else : The algorithm writes the number as it is .
     * write the byteCounter to the OutputStream .
     */
    @Override
    public void write(int byteCounter) {
        try {
            byte[] toWriteAfterPartition;

            if ( byteCounter > 255)
            {
                int mod = byteCounter % 255;
                toWriteAfterPartition = new byte[mod * 2 + 1];
                int i;
                int lastIndex = toWriteAfterPartition.length-1;
                for(i=0; i < lastIndex; i=i+2){
                    toWriteAfterPartition[i] = (byte) 255;
                }
                toWriteAfterPartition[lastIndex] = (byte) (byteCounter - (255 * mod));
/*                byte byteCounter1, byteCounter2;
                byteCounter1 = (byte)255;
                byteCounter2 = (byte)(byteCounter - 255);
                l = new byte[]{byteCounter1, (byte) 0, byteCounter2};*/
            }
            else {
                toWriteAfterPartition = new byte[]{(byte) byteCounter};
            }
            this.out.write(toWriteAfterPartition);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}

