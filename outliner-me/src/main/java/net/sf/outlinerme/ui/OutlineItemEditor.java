package net.sf.outlinerme.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

import net.sf.outlinerme.outline.OutlineItem;


public class OutlineItemEditor extends TextBox implements CommandListener {

    private static final String EDITOR_TITLE = "Edit description";

    private static final int DESCRIPTION_MAX_SIZE = 512;

    private static final Command COMMAND_OK =
        new Command("OK", Command.OK, 1);

    private static final Command COMMAND_CANCEL =
        new Command("Cancel", Command.CANCEL, 1);

    protected OutlineItem editedItem;

    protected OutlinerMIDlet midlet;

    public OutlineItemEditor(final OutlinerMIDlet midlet, final OutlineItem item) {

        super(EDITOR_TITLE, item.getDescription(), DESCRIPTION_MAX_SIZE, TextField.ANY);

        this.midlet = midlet;
        this.editedItem = item;

        this.addCommand(OutlineItemEditor.COMMAND_OK);
        this.addCommand(OutlineItemEditor.COMMAND_CANCEL);
        this.setCommandListener(this);
    }

    public void commandAction(final Command command, final Displayable displayable) {

        if(command == OutlineItemEditor.COMMAND_OK) {
            this.ok();
        }

        else if(command == OutlineItemEditor.COMMAND_CANCEL) {
            this.cancel();
        }
    }

    protected void cancel() {
        this.close();
    }

    protected void ok() {
        this.editedItem.setDescription(this.getString());
        this.close();
    }
    
    protected void close() {
        this.midlet.showCurrentItem();
    }

}
