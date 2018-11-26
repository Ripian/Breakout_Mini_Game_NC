package CustomButtons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

import GameStates.GameState;
import GameStates.GameStateManager;

public class RectangleButtom extends Buttom{

    protected double width;
    protected double height;
    protected int borderLineWidth;
    protected int percentageToTakeForBorderLine=20;

    protected Paint borderPaint;



    public RectangleButtom(
                        GameStateManager gsm,
                        GameState.GameStateName nextGameStateOnClick,
                        GameState ownerState,
                        double percentageOfXToScreen,
                        double percentageOfYToScreen,
                        double percentageofWithToScreen,
                        double percentageOfHeightToScreen,
                        Bitmap rawBitmapNormal,
                        Bitmap rawBitmapPressed,
                        String stringToDraw,
                        Paint textPaint   )
    {
        //init coordinates of box
        super(gsm, nextGameStateOnClick,ownerState,percentageOfXToScreen,percentageOfYToScreen,stringToDraw,textPaint);
        this.width = MainGameWindow.displayWidth*percentageofWithToScreen/100.0;
        this.height = MainGameWindow.displayWidth*percentageOfHeightToScreen/100.0;

        //init border
        this.borderLineWidth = Math.min((int)width,(int)height)*percentageToTakeForBorderLine/100;
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(Color.BLACK); // default color
        this.borderPaint.setStrokeWidth(borderLineWidth);

        //init 2 off/on bitmaps
        if(rawBitmapNormal!=null)
        this.buttonImageNormal=Bitmap.createScaledBitmap(rawBitmapNormal, (int)width, (int)height, false);

        if(rawBitmapPressed!=null)
        this.buttonImagePressed=Bitmap.createScaledBitmap(rawBitmapPressed, (int)width, (int)height, false);

        //init sizeOfText
        if(textPaint!=null){
            //init Paint text size
            StringYDrawingOffset = (height*AdditionalDecreasePercentageOfTextSize/100);
            double newTextSize = height - StringYDrawingOffset;
            textPaint.setTextSize((float) newTextSize);



        }

        if(buttomText!=null) {
            float widthOfString = textPaint.measureText(buttomText,0,buttomText.length());
            StringXDrawingOffset= (this.width-widthOfString)/2;

        }
    }


    @Override
    public void draw(Canvas canvas){

        if(buttonImageNormal!=null) {
            if (isPressed) {
                canvas.drawBitmap(buttonImagePressed, (int) x, (int) y, null);
            } else {
                canvas.drawBitmap(buttonImageNormal, (int) x, (int) y, null);
            }
            //draw black outline box
            canvas.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), borderPaint);

        }


        if(buttomText!=null)drawText(canvas);

    }

    @Override
    protected void drawText(Canvas canvas){
        canvas.drawText(buttomText,(float)(x+StringXDrawingOffset),(float)(y+height-StringYDrawingOffset),textPaint);

    }

    @Override
    protected void manageClick(MotionEvent event){
        if(isFingerInsideButtom(event)){
            gsm.changeGameState(nextGameStateOnClick);

        }

    }




    @Override
    public void touchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            isPressed=false;
            manageClick(event);
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(isFingerInsideButtom(event)){
                isPressed=true;
            }

        }

    }





    protected boolean isFingerInsideButtom(MotionEvent event){
        double fingerX=event.getX();
        double fingerY=event.getY();


        if(fingerX>x &&
           fingerX<(x+width) &&
           fingerY>y &&
           fingerY<(y+height)
                )
        {
            return true;


        }

        return false;

    }



    //setters getters

    @Override
    public void setAdditionalDecreasePercentageOfTextSize(int newValue){
        this.AdditionalDecreasePercentageOfTextSize = newValue;
        if(textPaint!=null){
            //init Paint text size
            double newTextSize = height - (height*AdditionalDecreasePercentageOfTextSize/100);
            textPaint.setTextSize((float) newTextSize);

        }

    }



    public void setBorderColor(int color){
        this.borderPaint.setColor(color);

    }


    public double getWidth(){return width;}

}
