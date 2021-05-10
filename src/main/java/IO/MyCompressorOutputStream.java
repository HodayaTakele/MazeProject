package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MyCompressorOutputStream extends OutputStream {

    @SuppressWarnings("FieldMayBeFinal")
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) { }

    @Override
    public void write(byte[] b) throws IOException {
        try {
            int startIndex;
            if (b[0] == 0)
                startIndex = 7;
            else if (b[0] == 1)
                startIndex = 25;
            else
                throw new IOException();
            // write the maze info byte
            this.out.write(b,0,startIndex);
            // copy maze data into a new array
            byte[] withoutInfo = Arrays.copyOfRange(b, startIndex, b.length);

            ArrayList<String> byteString = new ArrayList<>();
            int i = 0;
            StringBuilder tempString = new StringBuilder();
            // go over the byte array and convert each 8 cells to a string representation
            while (i < withoutInfo.length) {
                tempString.append((withoutInfo[i] == 1) ? '1' : '0');
                if (tempString.length() == 8) {
                    byteString.add(tempString.toString());
                    tempString = new StringBuilder();
                }
                i++;
            }
            // add a string if the last string was less then 8 chars
            int num;
            if ((num = 8 - (withoutInfo.length % 8)) < 8) {
                tempString.append(new String(new char[num]).replace('\0', '0'));
                byteString.add(tempString.toString());
            }
            //write the converted bytes to the output stream
            for (i = 0; i < byteString.size(); i++)
                this.out.write((byte) Integer.parseInt( byteString.get(i), 2));

        }catch (IOException io){
            throw io;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
