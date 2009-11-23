package net.sf.outlinerme.ui;


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import net.sf.outlinerme.outline.OutlineEditor;
import net.sf.outlinerme.outline.OutlineItem;



public class OutlinerMIDlet extends MIDlet implements CommandListener {

    public static Command exit =
        new OutlinerCommand("Exit", Command.EXIT, 3) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.exitRequested();
            }
        };

    public static Command view =
        new OutlinerCommand("View", Command.ITEM, 1) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.showSelectedItem((OutlineBrowser) displayable);
            }
        };

    public static Command back =
        new OutlinerCommand("Back", Command.BACK, 1) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.showParentItem();
            }
        };

    public static Command remove =
        new OutlinerCommand("Remove", Command.ITEM, 2) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.removeSelected((OutlineBrowser)displayable);
            }
    }; 

    public static Command create =
        new OutlinerCommand("Create", Command.ITEM, 2) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.createNewItem();
            }
    }; 

    public static Command modify =
        new OutlinerCommand("Modify", Command.ITEM, 2) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.modifySelectedDescription((OutlineBrowser) displayable);
            }
    }; 

    private OutlineEditor outlineEditor;

    public OutlinerMIDlet() {
        OutlineItem outline = OutlinerMIDlet.createSampleOutline();
        this.outlineEditor = new OutlineEditor(outline);
    }

    private static OutlineItem createSampleOutline() {

        return new OutlineItem("root", new OutlineItem [] {
            new OutlineItem("A1"),
            new OutlineItem("A2", new OutlineItem [] {
                new OutlineItem("B1"),
                new OutlineItem("B2")
            }),
            new OutlineItem("A3")
        });
    }

    public void startApp() {
        this.showCurrentItem();
    }
    
    public void pauseApp() { }

    public void destroyApp(boolean b) { }

    // A convenience method for exiting
    void exitRequested() {
        destroyApp(false);
        notifyDestroyed();
    }

    public void commandAction(
        final Command command,
        final Displayable displayable
    ) {
        if(command instanceof OutlinerCommand) {
            ((OutlinerCommand)command).execute(this, displayable);
        }
    }

    public void showCurrentItem() {
        OutlineItem current = this.outlineEditor.getCurrent();
        Screen scr = new OutlineBrowser(this, current);
        Display.getDisplay(this).setCurrent(scr);
    }

    public void showParentItem() {

        if(this.outlineEditor.atRoot())
            return;

        this.outlineEditor.goToParent();

        this.showCurrentItem();
    }

    public void showSelectedItem(final OutlineBrowser outlineBrowser) {

        if(outlineBrowser.atRoot())
            return;

        int selectedChildIndex = outlineBrowser.getSelectedChildIndex();
        this.outlineEditor.goToChildAt(selectedChildIndex);

        this.showCurrentItem();
    }

    protected void removeSelected(final OutlineBrowser outlineBrowser) {

        if(outlineBrowser.atRoot()) {
            this.removeCurrentItem();
        }

        else {
            int selectedChildIndex = outlineBrowser.getSelectedChildIndex();
            this.outlineEditor.removeCurrentChildAt(selectedChildIndex);
        }

        this.showCurrentItem();
    }

    public void removeCurrentItem() {
        if(!this.outlineEditor.atRoot())
            this.outlineEditor.removeCurrent();
    }

    public void createNewItem() {
        OutlineItem newItem = new OutlineItem();
        Screen scr = new NewOutlineItemEditor(this, newItem);
        Display.getDisplay(this).setCurrent(scr);
    }

    public void addChildToCurrent(final OutlineItem newChild) {
        this.outlineEditor.addChildToCurrent(newChild);
    }

    public void modifySelectedDescription(final OutlineBrowser outlineBrowser) {

        OutlineItem selectedItem =
            outlineBrowser.getSelectedItem(this.outlineEditor);

        Screen scr = new OutlineItemEditor(this, selectedItem);
        Display.getDisplay(this).setCurrent(scr);
    }

}
