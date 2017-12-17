package axoloti.piccolo.displayviews;

import axoloti.patch.object.display.DisplayInstanceVScale;
import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.piccolo.components.displays.PVValueLabelsComponent;

public class PDisplayInstanceViewVScale extends PDisplayInstanceView {

    DisplayInstanceVScale displayInstance;
    private PVValueLabelsComponent vlabels;

    public PDisplayInstanceViewVScale(DisplayInstanceVScale displayInstance, IAxoObjectInstanceView axoObjectInstanceView) {
        super(displayInstance, axoObjectInstanceView);
        this.displayInstance = displayInstance;
    }

    @Override
    public void updateV() {
    }

    @Override
    public void PostConstructor() {
        super.PostConstructor();

        vlabels = new PVValueLabelsComponent(-60, 10, 10, axoObjectInstanceView);
        addChild(vlabels);
    }
}
