import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.util.io.TextProcessor;

import java.util.List;
import java.util.StringJoiner;

public class TextEditor extends JpizeApplication{
    
    public static void main(String[] args){
        ContextBuilder.newContext(1280, 720, "Editor")
                .register()
                .setAdapter(new TextEditor());

        Jpize.runContexts();
    }
    
    
    private TextureBatch batch;
    private BitmapFont font;
    private TextProcessor text;
    
    @Override
    public void init(){
        batch = new TextureBatch();

        text = new TextProcessor(true);

        font = FontLoader.getDefault();
        font.setScale(1F);
    }
    
    @Override
    public void render(){
        if(Key.LEFT_CONTROL.isPressed() && Key.Y.isDown())
            text.removeLine();

        if(Key.LEFT_CONTROL.isPressed() && Key.V.isDown())
            text.insertText(Jpize.getClipboardString());
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();

        final String string = text.getString(true);
        final float advance = font.getOptions().getAdvanceScaled();

        // Iterate lines
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        final List<String> lines = text.getLines();
        for(int i = 0; i < lines.size(); i++){
            // Add line number
            lineNumbersJoiner.add(String.valueOf(lines.size() - i));
            
            // Draw line background
            final float lineWidth = font.getLineWidth(lines.get(i));
            batch.drawQuad(0.1, 0.15, 0.2, 1,  50, 10 + (lines.size() - 1 - i) * advance,  lineWidth, advance);
            batch.drawQuad(0.3, 0.45, 0.5, 1,  0 , 10 + (lines.size() - 1 - i) * advance,  50, advance);
        }
        // Draw line numbers
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);
        
        // Draw text
        font.drawText(batch, string, 50, 10);
        
        // Draw cursor
        if(text.isCursorRender()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = (text.getCursorY() + 1) * advance - advance * text.getLines().size();
            final float cursorX = font.getLineWidth(currentLine.substring(0, text.getCursorX()));
            batch.drawQuad(1, 1, 1, 1,  50 + cursorX, 10 - cursorY, 2, advance);
        }
        
        batch.end();
    }
    
    @Override
    public void dispose(){
        text.dispose();
        batch.dispose();
        font.dispose();
    }
    
}
