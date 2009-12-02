package net.sf.outlinerme.outline.io;

import net.sf.outlinerme.outline.OutlineItem;


public interface OutlineDao {

    OutlineItem read(final String name) throws OutlineDaoException;

    void write(final OutlineItem outline, final String name)  throws OutlineDaoException;
}
