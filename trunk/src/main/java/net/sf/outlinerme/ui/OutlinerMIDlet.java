package net.sf.outlinerme.ui;


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import net.sf.outlinerme.outline.OutlineEditor;
import net.sf.outlinerme.outline.OutlineItem;
import net.sf.outlinerme.outline.io.OutlineDao;
import net.sf.outlinerme.outline.io.OutlineDaoException;
import net.sf.outlinerme.outline.io.rms.OutlineDaoRecordStore;



public class OutlinerMIDlet extends MIDlet implements CommandListener {

    private static final String DEFAULT_OUTLINE_NAME = "OutlineName";

    public static Command exit =
        new OutlinerCommand("Exit", Command.EXIT, 3) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.exitRequested();
            }
        };

    public static Command select =
        new OutlinerCommand("View", Command.ITEM, 1) {
            public void execute(OutlinerMIDlet midlet, Displayable displayable) {
                midlet.selectItem((OutlineBrowser) displayable);
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

    private OutlineDao dao = new OutlineDaoRecordStore();

    public OutlinerMIDlet() {
        OutlineItem outline = this.loadOutline();
        this.outlineEditor = new OutlineEditor(outline);
    }

    private OutlineItem loadOutline() {
        OutlineItem outline;
        try {
            outline = dao.read(DEFAULT_OUTLINE_NAME);
        } catch (OutlineDaoException e) {
            outline = OutlinerMIDlet.createSampleOutline();
        }
        return outline;
    }

    private static OutlineItem createSampleOutline() {

        return new OutlineItem("My Notes", new OutlineItem [] {
            new OutlineItem("Projects"),
            new OutlineItem("Shopping List", new OutlineItem [] {
                new OutlineItem("chocolate"),
                new OutlineItem("oranges")
            }),
            new OutlineItem("Gift Ideas"),
            new OutlineItem("Vacation Plans")
        });
    }

    private void saveOutline() {
        OutlineItem outline = this.outlineEditor.getRoot();
        try {
            this.dao.write(outline, OutlinerMIDlet.DEFAULT_OUTLINE_NAME);
        } catch (OutlineDaoException e) {
            e.printStackTrace();
        }
    }

    public void startApp() {
        this.showCurrentItem();
    }
    
    public void pauseApp() { }

    public void destroyApp(boolean b) { }

    // A convenience method for exiting
    void exitRequested() {
        this.saveOutline();
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

    public void selectItem(final OutlineBrowser outlineBrowser) {

        if(outlineBrowser.atRoot()) {
            this.modifySelectedDescription(outlineBrowser);
        }

        else {
            this.showSelectedItem(outlineBrowser);
        }
    }

    public void showSelectedItem(final OutlineBrowser outlineBrowser) {

        if(outlineBrowser.atRoot()) {
            return;
        }

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

        this.modifyItemDescription(selectedItem);
    }

    public void modifyItemDescription(final OutlineItem selectedItem) {

        Screen scr = new OutlineItemEditor(this, selectedItem);
        Display.getDisplay(this).setCurrent(scr);
    }

}
