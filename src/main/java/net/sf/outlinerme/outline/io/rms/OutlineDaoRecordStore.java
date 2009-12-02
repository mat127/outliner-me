package net.sf.outlinerme.outline.io.rms;

import java.io.IOException;

import javax.microedition.rms.RecordStoreException;

import net.sf.outlinerme.outline.OutlineItem;
import net.sf.outlinerme.outline.io.OutlineDao;
import net.sf.outlinerme.outline.io.OutlineDaoException;


public class OutlineDaoRecordStore implements OutlineDao {
    
    public OutlineItem read(final String name) throws OutlineDaoException {

        try {
            OutlineRecordStore store = OutlineRecordStore.open(name, false);
            return store.read();
        }
        catch(RecordStoreException e) {
            throw new OutlineDaoException(e.getMessage());
        }
        catch (IOException e) {
            throw new OutlineDaoException(e.getMessage());
        }
    }
    
    public void write(final OutlineItem outline, final String name) throws OutlineDaoException {

        try {
            OutlineRecordStore store = OutlineRecordStore.open(name, true);
            store.write(outline);
        }
        catch(RecordStoreException e) {
            throw new OutlineDaoException(e.getMessage());
        }
        catch (IOException e) {
            throw new OutlineDaoException(e.getMessage());
        }
    }

}
