package axoloti.piccolo.attributeviews;

import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.patch.object.attribute.AttributeInstanceTextEditor;
import axoloti.piccolo.components.control.PButtonComponent;
import axoloti.swingui.TextEditor;

public class PAttributeInstanceViewTextEditor extends PAttributeInstanceViewString {

    AttributeInstanceTextEditor attributeInstance;
    PButtonComponent bEdit;
    TextEditor editor;

    public PAttributeInstanceViewTextEditor(AttributeInstanceTextEditor attributeInstance, IAxoObjectInstanceView axoObjectInstanceView) {
        super(attributeInstance, axoObjectInstanceView);
        this.attributeInstance = attributeInstance;
    }

    void showEditor() {
        if (editor == null) {/*
            editor = new TextEditor(attributeInstance.getStringRef(), null);
            // fixme DocumentWindow null arg was: getPatchView().getPatchController().getPatchFrame());
            editor.setTitle(attributeInstance.getObjectInstance().getInstanceName() + "/" + attributeInstance.getModel().getName());
            editor.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    //attributeInstance.setValueBeforeAdjustment(attributeInstance.getStringRef().s);
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    //if (!attributeInstance.getValueBeforeAdjustment().equals(attributeInstance.getStringRef().s)) {
                    //    attributeInstance.getObjectInstance().getPatchModel().setDirty();
                    //}
                }
            });*/
        }
        editor.toFront();
    }

    @Override
    public void PostConstructor() {
        super.PostConstructor();
        bEdit = new PButtonComponent("Edit", axoObjectInstanceView);
        addChild(bEdit);
        bEdit.addActListener(new PButtonComponent.ActListener() {
            @Override
            public void OnPushed() {
                showEditor();
            }
        });
    }

    @Override
    public void Lock() {
        if (bEdit != null) {
            bEdit.setEnabled(false);
        }
    }

    @Override
    public void UnLock() {
        if (bEdit != null) {
            bEdit.setEnabled(true);
        }
    }

    @Override
    public String getString() {
        return attributeInstance.getValue();
    }

    @Override
    public void setString(String sText) {
        if (editor != null) {
            editor.SetText(sText);
        }
    }
}
