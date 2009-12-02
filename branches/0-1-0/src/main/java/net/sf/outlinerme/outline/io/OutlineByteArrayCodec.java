package net.sf.outlinerme.outline.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.outlinerme.outline.OutlineItem;


public class OutlineByteArrayCodec {

    public static byte [] encode(final OutlineItem outline) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(); 
        OutlineOutputStream.writeOutlineTo(outline, bytes);
        return bytes.toByteArray();
    }

    public static OutlineItem decode(final byte [] bytes) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes); 
        return OutlineInputStream.readOutlineFrom(input);
    }
}
