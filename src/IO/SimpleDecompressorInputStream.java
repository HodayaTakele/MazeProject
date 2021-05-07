package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {

    InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     * @param b an empty array that will hold the data we read.
     * @return int represent the number of bytes we read
     * @throws IOException for example if b is not as should be (the first index is not equal 0 or 1).
     */
    @Override
    public int read(byte[] b) throws IOException {
        int firstIndex = b[0];
        int startIndex;
        int numOfBytes = 0;
        byte byteToWrite = (byte) 0;
        if (firstIndex == 0) { startIndex = 7; }
        else if (firstIndex==1) { startIndex = 25; }
        else { throw new IOException(); }
        int data = this.in.read(b, 0, startIndex - 1);

        data = this.in.read();
        while (data != -1) {
            for (int i = 0; i < data; i++) {
                b[startIndex++] = byteToWrite;
                numOfBytes++;
            }
            data = this.in.read();
            if (data != 0) {
                byteToWrite = (byte) ((byteToWrite) ^ (1));
            }
            //avoiding option of -1
            else if (data == 1) {
                data = this.in.read();
            }
        }
    return numOfBytes;
    }
}
