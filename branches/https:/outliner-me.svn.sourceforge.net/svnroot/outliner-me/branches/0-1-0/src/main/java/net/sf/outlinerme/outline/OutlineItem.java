package net.sf.outlinerme.outline;

import java.util.Enumeration;
import java.util.Vector;


public class OutlineItem {

    private String description;

    private Vector childs;

    public OutlineItem(final String description, final OutlineItem [] childs) {
        this.description = description;
        this.childs = childs != null ?
            new Vector(childs.length) :
            new Vector();
        this.add(childs);
    }

    public OutlineItem(final String description) {
        this(description, null);
    }

    public OutlineItem() {
        this("");
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setChilds(final OutlineItem [] childs)  {
        this.removeAll();
        this.add(childs);
    }

    public void removeAll() {
        this.childs.removeAllElements();
    }

    public void add(final OutlineItem child) {
        childs.addElement(child);
    }

    public void add(final OutlineItem [] childs) {

        if(childs == null)
            return;

        this.childs.ensureCapacity(this.getChildCount() + childs.length);

        for(int i = 0; i < childs.length; i++) {
            this.add(childs[i]);
        }
    }

    public int getChildCount() {
        return this.childs.size();
    }

    public Enumeration getChilds() {
        return this.childs.elements();
    }

    public OutlineItem getChildAt(final int index) {
        return (OutlineItem) this.childs.elementAt(index);
    }

    public void removeChildAt(final int index) {
        this.childs.removeElementAt(index);
    }

    public void removeChild(final OutlineItem child) {
        this.childs.removeElement(child);
    }
}
