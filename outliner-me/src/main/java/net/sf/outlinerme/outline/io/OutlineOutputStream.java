package net.sf.outlinerme.outline.io;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import net.sf.outlinerme.outline.OutlineItem;


public class OutlineOutputStream {

    private DataOutput output;

    public OutlineOutputStream(final DataOutput output) {
        this.output = output;
    }

    public OutlineOutputStream(final OutputStream output) {
        this((DataOutput)new DataOutputStream(output));
    }

    public void writeOutline(final OutlineItem item) throws IOException {

        this.output.writeUTF(item.getDescription());

        this.writeOutlineItemChilds(item);
    }

    private void writeOutlineItemChilds(final OutlineItem item) throws IOException {

        this.output.writeShort(item.getChildCount());

        for(Enumeration childEnumeration = item.getChilds();
            childEnumeration.hasMoreElements();
        ) {
            OutlineItem childItem = (OutlineItem) childEnumeration.nextElement();
            this.writeOutline(childItem);
        }
    }

    public static void writeOutlineTo(
        final OutlineItem item,
        final OutputStream stream
    ) throws IOException
    {
        OutlineOutputStream output = new OutlineOutputStream(stream);
        output.writeOutline(item);
    }
}
