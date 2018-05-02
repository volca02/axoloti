package axoloti.patch.object;

import axoloti.mvc.IModel;
import axoloti.object.IAxoObject;
import axoloti.patch.PatchModel;
import axoloti.patch.object.attribute.AttributeInstance;
import axoloti.patch.object.display.DisplayInstance;
import axoloti.patch.object.inlet.InletInstance;
import axoloti.patch.object.outlet.OutletInstance;
import axoloti.patch.object.parameter.ParameterInstance;
import axoloti.target.fs.SDFileReference;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author jtaelman
 */
public interface IAxoObjectInstance extends Comparable<IAxoObjectInstance>, IModel {

    IAxoObject getType();

    String getInstanceName();

    String getCInstanceName();

    String getLegalName();

    List<InletInstance> getInletInstances();

    List<OutletInstance> getOutletInstances();

    List<ParameterInstance> getParameterInstances();

    List<AttributeInstance> getAttributeInstances();

    List<DisplayInstance> getDisplayInstances();

    InletInstance findInletInstance(String n);

    OutletInstance findOutletInstance(String n);

    int getX();

    int getY();

    Boolean getSelected();

    @Override
    PatchModel getParent();

    Point getLocation();

    void setLocation(Point p);

    List<SDFileReference> getFileDepends();

    boolean isTypeWasAmbiguous();

    boolean setInstanceName(String InstanceName);

    IAxoObject resolveType(String directory);

    void setParent(PatchModel patchModel);

    @Override
    ObjectInstanceController getControllerFromModel();
}
