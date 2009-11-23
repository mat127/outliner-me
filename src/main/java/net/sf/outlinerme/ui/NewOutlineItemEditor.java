package net.sf.outlinerme.ui;

import net.sf.outlinerme.outline.OutlineEditor;
import net.sf.outlinerme.outline.OutlineItem;


public class NewOutlineItemEditor extends OutlineItemEditor {

    public NewOutlineItemEditor(final OutlinerMIDlet midlet, final OutlineItem item) {
        super(midlet, item);
    }

    protected void ok() {
        this.midlet.addChildToCurrent(this.editedItem);
        super.ok();
    }

}
