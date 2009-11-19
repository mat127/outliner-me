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
                midlet.viewSelected((List) displayable);
            }
        };

    public static Command back =
        new OutlinerCommand("Back", Command.BACK, 1) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.viewParent();
            }
        };

    public static Command delete =
        new OutlinerCommand("Delete", Command.ITEM, 2) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.deleteSelected((List)displayable);
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

    public void showCurrentItem() {
        Screen scr = new OutlineBrowser(this, this.outlineEditor.getCurrent());
        Display.getDisplay(this).setCurrent(scr);
    }

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

    private void viewParent() {

        if(this.outlineEditor.atRoot())
            return;

        this.outlineEditor.goToParent();

        this.showCurrentItem();
    }

    private void viewSelected(final List outlineBrowser) {

        int selectedIndex = outlineBrowser.getSelectedIndex();
        if(selectedIndex == 0)
            return;

        this.outlineEditor.goToChildAt(selectedIndex - 1);

        this.showCurrentItem();
    }

    protected void deleteSelected(final List outlineBrowser) {

        int selectedIndex = outlineBrowser.getSelectedIndex();

        if(selectedIndex == 0) {
            if(!this.outlineEditor.atRoot())
                this.outlineEditor.removeCurrent();
        }

        else {
            this.outlineEditor.removeCurrentChildAt(selectedIndex - 1);
        }

        this.showCurrentItem();
    }
}
