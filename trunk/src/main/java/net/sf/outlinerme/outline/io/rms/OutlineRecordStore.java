package net.sf.outlinerme.outline.io.rms;

import java.io.IOException;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

import net.sf.outlinerme.outline.OutlineItem;
import net.sf.outlinerme.outline.io.OutlineByteArrayCodec;

public class OutlineRecordStore {

    private RecordStore store;

    private OutlineRecordStore(final RecordStore store) {
        this.store = store;
    }

    public static OutlineRecordStore open(
        final String name,
        final boolean createIfNecessary
    ) throws RecordStoreException
    {
        RecordStore rs = RecordStore.openRecordStore(name, createIfNecessary);
        return new OutlineRecordStore(rs);
    }

    public OutlineItem read() throws RecordStoreException, IOException {

        byte[] outlineBytes = this.readBytes();

        return OutlineByteArrayCodec.decode(outlineBytes);
    }

    protected byte[] readBytes() throws RecordStoreException {

        RecordEnumeration recordEnumeration =
            this.store.enumerateRecords(null, null, false);

        return recordEnumeration.nextRecord();
    }

    public void write(final OutlineItem outline) throws IOException, RecordStoreException {

        byte [] outlineBytes = OutlineByteArrayCodec.encode(outline);

        if(this.isEmpty()) {
            this.store.addRecord(outlineBytes, 0, outlineBytes.length);
        }
        else {
            this.updateBytes(outlineBytes);
        }
    }

    public boolean isEmpty() throws RecordStoreNotOpenException {
        
        return store.getNumRecords() < 1;
    }
    
    protected void updateBytes(final byte [] outlineBytes) throws RecordStoreException {
        
        int id = this.getOutlineRecordId();

        this.store.setRecord(id, outlineBytes, 0, outlineBytes.length);
    }

    protected int getOutlineRecordId() throws RecordStoreException {

        RecordEnumeration recordEnumeration =
            store.enumerateRecords(null, null, false);

        return recordEnumeration.nextRecordId();
    }
    
}
