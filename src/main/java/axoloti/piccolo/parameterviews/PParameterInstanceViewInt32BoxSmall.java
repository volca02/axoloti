package axoloti.piccolo.parameterviews;

import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.patch.object.parameter.ParameterInstanceInt32BoxSmall;
import axoloti.piccolo.components.control.PNumberBoxComponent;

public class PParameterInstanceViewInt32BoxSmall extends PParameterInstanceViewInt32Box {

    public PParameterInstanceViewInt32BoxSmall(ParameterInstanceInt32BoxSmall parameterInstance,
            IAxoObjectInstanceView axoObjectInstanceView) {
        super(parameterInstance, axoObjectInstanceView);
    }

    @Override
    public ParameterInstanceInt32BoxSmall getModel() {
        return (ParameterInstanceInt32BoxSmall) parameterInstance;
    }

    @Override
    public PNumberBoxComponent CreateControl() {
        return new PNumberBoxComponent(0.0, getModel().getMinValue(),
                getModel().getMaxValue(), 1.0, 12, 12, axoObjectInstanceView);
    }
}
