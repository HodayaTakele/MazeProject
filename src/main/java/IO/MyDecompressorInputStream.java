package IO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() { return 0; }
    @Override
    public int read(byte[] b) throws IOException {
        int numOfBytes = 0;
        try {
            numOfBytes += this.in.read(b, 0, 1);
            int startIndex;
            if (b[0] == 0)
                startIndex = 7;
            else if (b[0] == 1)
                startIndex = 25;
            else
                throw new IOException();

            //read maze info bytes to b
            numOfBytes += this.in.read(b, 1, startIndex - 1);
            int rows, columns;
            if(startIndex == 7){
                rows = Byte.toUnsignedInt(b[1]) + 1;
                columns = Byte.toUnsignedInt(b[2]) + 1;
            }
            else{
                rows = ByteBuffer.wrap(b, 1,4).getInt() + 1;
                columns = ByteBuffer.wrap(b, 5,4).getInt() + 1;
            }

            int fullBytesCount = (rows * columns)/8;
            ArrayList<String> byteStringList = new ArrayList<>();
            String s, toWrite;
            int data = this.in.read();
            while (data != -1) {
                s = Integer.toUnsignedString(data,2);
                toWrite = (s.length() < 8) ? new String(new char[(8 - s.length())]).replace('\0', '0') : "";
                byteStringList.add(toWrite+s);
                data = this.in.read();
            }
            int i;
            for (i = 0; i < byteStringList.size(); i++) {
                toWrite = byteStringList.get(i);

                if (fullBytesCount - i <= 0)
                    toWrite = byteStringList.get(i).substring(0,(rows * columns) % 8);

                for (int j = 0; j < toWrite.length(); j++) {
                    b[startIndex++] = (byte) ((toWrite.charAt(j) == '1') ? 1 : 0);
                    numOfBytes++;
                }
            }

        }catch (IOException io){
            throw io;
        }catch (Exception e){
            e.printStackTrace();
        }
        return numOfBytes;
    }
}
