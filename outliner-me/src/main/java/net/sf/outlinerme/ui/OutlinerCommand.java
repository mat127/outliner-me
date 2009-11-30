package net.sf.outlinerme.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;


public abstract class OutlinerCommand extends Command {

    public OutlinerCommand(String label, int commandType, int priority) {
        super(label, commandType, priority);
    }
    
    public abstract void execute(
        final OutlinerMIDlet midlet,
        final Displayable displayable
    );
}
