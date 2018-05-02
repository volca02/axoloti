package axoloti.abstractui;

import axoloti.mvc.IView;
import axoloti.patch.PatchModel;
import axoloti.patch.object.IAxoObjectInstance;
import axoloti.patch.object.ObjectInstanceController;
import axoloti.patch.object.inlet.InletInstance;
import axoloti.patch.object.outlet.OutletInstance;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import javax.swing.JComponent;

public interface IAxoObjectInstanceView extends IView<ObjectInstanceController> {

    IAxoObjectInstance getModel();

    void Lock();

    void Unlock();

    boolean isLocked();

    PatchView getPatchView();

    PatchModel getPatchModel();

    IIoletInstanceView getInletInstanceView(InletInstance inletInstance);

    IIoletInstanceView getOutletInstanceView(OutletInstance ouletInstance);

    List<IIoletInstanceView> getInletInstanceViews();

    List<IIoletInstanceView> getOutletInstanceViews();

    List<IParameterInstanceView> getParameterInstanceViews();

    void setLocation(int x, int y);

    void addInstanceNameEditor();

    void moveToFront();

    void resizeToGrid();

    Point getLocation();

    void repaint();

    Dimension getPreferredSize();

    Dimension getSize();

    JComponent getCanvas();

    boolean isZombie();

}
