package net.sf.outlinerme.outline.io;

import net.sf.outlinerme.outline.OutlineItem;


public interface OutlineDao {

    OutlineItem read(final String name);

    void write(final OutlineItem outline, final String name);
}
