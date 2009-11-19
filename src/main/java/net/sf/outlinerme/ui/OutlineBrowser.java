package net.sf.outlinerme.ui;

import java.util.Enumeration;

import javax.microedition.lcdui.List;

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
        this.addCommand(OutlinerMIDlet.delete);
        
        this.setCommandListener(midlet);
    }
    
}
