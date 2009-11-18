package net.sf.outlinerme.outline;

import java.util.Stack;


public class OutlineEditor {

    private Stack path;

    public OutlineEditor(final OutlineItem root) {
        this.path = new Stack();
        this.path.push(root);
    }

    public OutlineItem getCurrent() {
        return (OutlineItem) this.path.peek();
    }

    public void goToChildAt(final int index) {
        OutlineItem current = this.getCurrent();
        this.path.push(current.getChildAt(index));
    }

    public void goToParent() {
        if(this.atRoot())
            throw new IllegalStateException("already at root");
        this.path.pop();
    }

    public boolean atRoot() {
        return this.path.size() <= 1;
    }


    public void removeCurrent() {
        if(this.atRoot())
            throw new IllegalStateException("cannot remove root");
        OutlineItem current = (OutlineItem) this.path.pop();
        this.removeChild(current);
    }

    public void removeChild(final OutlineItem child) {
        OutlineItem current = this.getCurrent();
        current.removeChild(child);
    }

    public void removeCurrentChildAt(int index) {
        OutlineItem current = this.getCurrent();
        current.removeChildAt(index);
    }

}
