package entity;

import jpize.graphics.util.batch.TextureBatch;
import jpize.physic.axisaligned.rect.AARect;
import jpize.physic.axisaligned.rect.AARectBody;
import jpize.physic.utils.Velocity2f;

public abstract class Entity extends AARectBody{

    private final Velocity2f velocity;

    public Entity(AARect rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public abstract void render(TextureBatch batch);

    public Velocity2f getVelocity(){
        return velocity;
    }

}
