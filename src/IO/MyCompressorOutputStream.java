package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    int byteCounter;
    boolean byteIsVisit;

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] b) throws IOException {
        /*super.write(b);*/
        for (int i=0; i<b.length; i++)
        {
        }
    }

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
}
