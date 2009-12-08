package net.sf.outlinerme.ui;

import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

import net.sf.outlinerme.outline.OutlineEditor;
import net.sf.outlinerme.outline.OutlineItem;



public class OutlineBrowser extends List {
    
    private static final String BROWSER_TITLE = "Outliner ME";

    private static Image OUTLINE_ICON = null;
    private static Image ITEM_ICON = null;

    static {
        try {
            OutlineBrowser.OUTLINE_ICON =
                Image.createImage("/net/sf/outlinerme/icons/outline.png");
        }
        catch (IOException e) {
            OutlineBrowser.OUTLINE_ICON = Image.createImage(1, 1);
        }

        try {
            OutlineBrowser.ITEM_ICON =
                Image.createImage("/net/sf/outlinerme/icons/item.png");
        }
        catch (IOException e) {
            OutlineBrowser.ITEM_ICON = Image.createImage(1, 1);
        }
    }

    public OutlineBrowser(final OutlinerMIDlet midlet, final OutlineItem item) {

        super(BROWSER_TITLE, List.IMPLICIT);

        this.append(this.getDescription(item), OutlineBrowser.ITEM_ICON);

        for(Enumeration childs = item.getChilds(); childs.hasMoreElements(); ) {
            OutlineItem child = (OutlineItem) childs.nextElement();
            this.append(this.getDescription(child), OutlineBrowser.OUTLINE_ICON);
        }
        
        this.setSelectCommand(OutlinerMIDlet.select);

        this.addCommand(OutlinerMIDlet.exit);
        this.addCommand(OutlinerMIDlet.back);
        this.addCommand(OutlinerMIDlet.create);
        this.addCommand(OutlinerMIDlet.remove);
        this.addCommand(OutlinerMIDlet.modify);
        this.addCommand(OutlinerMIDlet.about);
        
        this.setCommandListener(midlet);
    }

    private String getDescription(final OutlineItem item) {
        String description = item.getDescription();
        int newLineIndex = description.indexOf('\n');
        if(newLineIndex >= 0)
            description = description.substring(0, newLineIndex);
        return description;
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
