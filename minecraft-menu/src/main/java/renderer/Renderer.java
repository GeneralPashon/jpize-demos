package renderer;

import jpize.util.Disposable;
import jpize.util.Resizable;

public interface Renderer extends Disposable, Resizable{

    void render();

}
