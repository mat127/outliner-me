package net.sf.outlinerme.ui;

import java.util.Enumeration;

import javax.microedition.lcdui.List;

import net.sf.outlinerme.outline.OutlineEditor;
import net.sf.outlinerme.outline.OutlineItem;



public class OutlineBrowser extends List {
    
    private static final String BROWSER_TITLE = "Outliner ME";

    public OutlineBrowser(final OutlinerMIDlet midlet, final OutlineItem item) {

        super(BROWSER_TITLE, List.IMPLICIT);

        this.append(item.getDescription(), null);

        for(Enumeration childs = item.getChilds(); childs.hasMoreElements(); ) {
            OutlineItem child = (OutlineItem) childs.nextElement();
            this.append(child.getDescription(), null);
        }
        
        this.setSelectCommand(OutlinerMIDlet.view);

        this.addCommand(OutlinerMIDlet.exit);
        this.addCommand(OutlinerMIDlet.back);
        this.addCommand(OutlinerMIDlet.create);
        this.addCommand(OutlinerMIDlet.remove);
        this.addCommand(OutlinerMIDlet.modify);
        
        this.setCommandListener(midlet);
    }

    public boolean atRoot() {
        return this.getSelectedIndex() == 0;
    }

    public int getSelectedChildIndex() {

        if(this.atRoot())
            throw new IllegalStateException("no child selected");
        
        return this.getSelectedIndex() - 1;
    }

    public OutlineItem getSelectedItem(final OutlineEditor outlineEditor) {

        if(this.atRoot()) {
            return outlineEditor.getCurrent();
        }

        else {
            int selectedChildIndex = this.getSelectedChildIndex();
            return outlineEditor.getCurrentChildAt(selectedChildIndex);
        }
    }
    
}
