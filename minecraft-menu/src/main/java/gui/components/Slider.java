package gui.components;

import jpize.Jpize;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.components.ExpandType;
import jpize.gui.components.NinePatchImage;
import jpize.gui.components.RegionMesh;
import jpize.gui.constraint.Constraint;
import jpize.math.Maths;
import run.Session;
import audio.Sound;
import gui.text.Component;

public class Slider extends MComponent{
    
    protected final Session session;
    
    private final NinePatchImage background, handle;
    private float value, prevValue, divisions;
    private boolean drag;
    private final gui.components.TextView textView;
    
    private final TextureRegion handleTexture, handleHoverTexture;
    
    public Slider(Session session){
        this.session = session;
        
        this.handleTexture = session.getResourceManager().getTexture("button");
        this.handleHoverTexture = session.getResourceManager().getTexture("button_hover");
        final TextureRegion blockedTexture = session.getResourceManager().getTexture("button_blocked");
        
        background = new NinePatchImage(blockedTexture, new RegionMesh(0,0, 2,2, 198,17, 200,20));
        super.setAsParentFor(background);
        background.setSize(Constraint.matchParent());
        background.setExpandType(ExpandType.HORIZONTAL);
        
        handle = new NinePatchImage(handleTexture, new RegionMesh(0,0, 2,2, 198,17, 200,20));
        super.setAsParentFor(handle);
        handle.setPosition(Constraint.zero(), Constraint.zero());
        handle.setSize(Constraint.aspect(8 / 20F), Constraint.relative(1));
        handle.setExpandType(ExpandType.HORIZONTAL);
        
        textView = new TextView(session, null);
        textView.alignSelf(Align.CENTER);
        textView.setScissor(true);
        super.setAsParentFor(textView);
    }
    
    
    @Override
    public void render(TextureBatch batch, float x, float y, float width, float height){
        if(super.isTouchReleased())
            session.getAudioManager().play(Sound.CLICK, 1, 1);
        
        float handleWidth = handle.getWidth();
        handle.getXConstraint().setValue(value * (width - handleWidth));
        if(isHover())
            handle.setTexture(handleHoverTexture);
        else
            handle.setTexture(handleTexture);
        
        background.render(batch);
        handle.render(batch);
        textView.render(batch);
        
        if(isTouchDown())
            drag = true;
        else if(Jpize.isTouchReleased())
            drag = false;
        
        prevValue = value;
        
        if(!drag)
            return;
        
        float mouseX = Jpize.getX();
        value = Maths.clamp(( mouseX - x - handleWidth / 2 ) / ( width - handleWidth ),0,1);
        
        if(divisions > 0)
            value = Maths.round(value * divisions) / divisions;
    }
    
    
    public float getValue(){
        return value;
    }
    
    public Slider setValue(double value){
        if(value > 1 || value < 0)
            return this;
        
        this.value = (float) value;
        return this;
    }
    
    public boolean isChanged(){
        return prevValue != value;
    }
    
    public Slider setDivisions(int divisions){
        this.divisions = divisions;
        return this;
    }
    
    
    public Component getText(){
        return textView.getText();
    }

    public void setText(Component component){
        this.textView.setText(component);
    }

}
