package net.sf.outlinerme.outline.io;

import java.io.IOException;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import net.sf.outlinerme.outline.OutlineItem;


public class OutlineDaoRecordStore implements OutlineDao {
    
    public OutlineItem read(final String name) throws OutlineDaoException {

        try {
            RecordStore store = RecordStore.openRecordStore(name, false);
            
            return this.read(store);
        }
        catch(RecordStoreException e) {
            throw new OutlineDaoException(e.getMessage());
        }
        catch (IOException e) {
            throw new OutlineDaoException(e.getMessage());
        }
    }
    
    public OutlineItem read(final RecordStore rs) throws RecordStoreException, IOException {

        RecordEnumeration recordEnumeration =
            rs.enumerateRecords(null, null, false);

        byte [] outlineBytes = recordEnumeration.nextRecord();

        return OutlineByteArrayCodec.decode(outlineBytes);
    }

    public void write(final OutlineItem outline, final String name) throws OutlineDaoException {

        try {
            RecordStore store = RecordStore.openRecordStore(name, true);
            
            this.write(outline, store);
        }
        catch(RecordStoreException e) {
            throw new OutlineDaoException(e.getMessage());
        }
        catch (IOException e) {
            throw new OutlineDaoException(e.getMessage());
        }
    }

    public void write(final OutlineItem outline, final RecordStore store) throws IOException, RecordStoreException {

        byte [] outlineBytes = OutlineByteArrayCodec.encode(outline);

        int id = this.getFirstRecordId(store);

        store.setRecord(id, outlineBytes, 0, outlineBytes.length);
    }

    private int getFirstRecordId(final RecordStore store) throws RecordStoreException {

        RecordEnumeration recordEnumeration =
            store.enumerateRecords(null, null, false);

        return recordEnumeration.nextRecordId();
    }
    
}
