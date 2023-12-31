package gui.components;

import jpize.Jpize;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.UIComponent;
import jpize.gui.constraint.Constraint;
import jpize.gui.constraint.PixelConstraint;
import jpize.math.Maths;
import gui.constraints.GapConstraint;

public abstract class MComponent extends UIComponent<TextureBatch>{

    public static final int INTERFACE_SCALE = 3;
    
    private Constraint widthC, heightC, xC, yC, textSizeC;
    private int scale;
    private boolean needInit = true;
    
    private void init(){
        if(this instanceof TextView component)
            textSizeC = component.getSizeConstraint();
        else{
            widthC = getWidthConstraint();
            heightC = getHeightConstraint();
        }
        
        xC = getXConstraint();
        yC = getYConstraint();
    }
    
    @Override
    protected void correctConstraints(){
        if(needInit){
            init();
            needInit = false;
        }
        scale = Math.max(1, Maths.round((float) Jpize.getHeight() * INTERFACE_SCALE / 20 / 50));
        
        if(textSizeC != null){
            if(textSizeC instanceof PixelConstraint constraint)
                setSize(Constraint.pixel(constraint.getValue() * scale));
        }else{
            if(widthC instanceof PixelConstraint constraint)
                setWidth(Constraint.pixel(constraint.getValue() * scale));
            if(heightC instanceof PixelConstraint constraint)
                setHeight(Constraint.pixel(constraint.getValue() * scale));
        }
        
        if(xC instanceof GapConstraint constraint)
            setX(GapConstraint.gap(constraint.getValue() * scale));
        if(yC instanceof GapConstraint constraint)
            setY(GapConstraint.gap(constraint.getValue() * scale));
    }
    
    @Override
    protected void correctPos(){
        x = Maths.round(x);
        y = Maths.round(y);
    }
    
    @Override
    protected void correctSize(){
        width = Maths.round(width / scale) * scale;
        height = Maths.round(height / scale) * scale;
    }

}
