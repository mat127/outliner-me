package net.sf.outlinerme.outline.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.outlinerme.outline.OutlineItem;


public class OutlineInputStream {

    private DataInput input;

    public OutlineInputStream(final DataInput input) {
        this.input = input;
    }

    public OutlineInputStream(final InputStream input) {
        this((DataInput)new DataInputStream(input));
    }

    public OutlineItem readOutline() throws IOException {

        OutlineItem root = new OutlineItem();

        String description = this.input.readUTF();
        root.setDescription(description);

        OutlineItem [] childItems = this.readOutlineItemChilds();
        root.setChilds(childItems);

        return root;
    }

    private OutlineItem [] readOutlineItemChilds() throws IOException {

        short childItemCount = this.input.readShort();

        OutlineItem [] childItems = new OutlineItem[childItemCount];

        for(short i = 0; i < childItemCount; i++) {
            childItems[i] = this.readOutline();
        }

        return childItems;
    }
    
}
