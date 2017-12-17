package axoloti.piccolo.displayviews;

import axoloti.patch.object.display.DisplayInstanceFrac32VU;
import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.piccolo.components.displays.PVUComponent;

public class PDisplayInstanceViewFrac32VU extends PDisplayInstanceViewFrac32 {

    DisplayInstanceFrac32VU displayInstance;

    public PDisplayInstanceViewFrac32VU(DisplayInstanceFrac32VU displayInstance, IAxoObjectInstanceView axoObjectInstanceView) {
        super(displayInstance, axoObjectInstanceView);
        this.displayInstance = displayInstance;
    }

    private PVUComponent vu;

    @Override
    public void PostConstructor() {
        super.PostConstructor();

        vu = new PVUComponent(axoObjectInstanceView);
        vu.setValue(0);
        addChild(vu);
    }

    @Override
    public void updateV() {
        //vu.setValue(displayInstance.getValueRef().getDouble());
    }
}
