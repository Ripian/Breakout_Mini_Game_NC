package CustomButtons;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.GameThread;
import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

import GameStates.GameState;
import GameStates.GameStateManager;

public class CircleButton extends Buttom{



    protected double radius;




    public CircleButton(GameStateManager gsm,
                        GameState.GameStateName nextGameStateOnClick,
                        GameState ownerState,
                        double percentageOfXToScreen,
                        double percentageOfYToScreen,
                        double percenageOfRadiusToWholeScreenByWidth,
                        Bitmap rawBitmapNormal,
                        Bitmap rawBitmapPressed,
                        String stringToDraw,
                        Paint textPaint)
    {
        super(gsm, nextGameStateOnClick,ownerState,percentageOfXToScreen,percentageOfYToScreen, stringToDraw,textPaint);



        this.radius = MainGameWindow.displayWidth*percenageOfRadiusToWholeScreenByWidth/100.0;
        this.buttonImageNormal=Bitmap.createScaledBitmap(rawBitmapNormal, (int)radius*2, (int)radius*2, false);
        this.buttonImagePressed=Bitmap.createScaledBitmap(rawBitmapPressed, (int)radius*2, (int)radius*2, false);

        /*
        if(textPaint!=null){ //this part is dead , no text drawing is supported
            //init Paint text size
            StringYDrawingOffset=((radius*2.0)*AdditionalDecreasePercentageOfTextSize/100);
            double newTextSize = (radius*2.0) - StringYDrawingOffset;
            textPaint.setTextSize((float) newTextSize);

        }

        */
    }



    @Override
    public void draw(Canvas canvas){

        if(buttonImageNormal!=null) {
            if (isPressed) {
                canvas.drawBitmap(buttonImagePressed, (int) x, (int) y, null);
            } else {
                canvas.drawBitmap(buttonImageNormal, (int) x, (int) y, null);
            }
        }


        //drawText(canvas);

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





    @Override
    protected void drawText(Canvas canvas){//this function is dead for circleButtom
       // canvas.drawText(buttomText,(float)x,(float)(y+radius*2.0-StringYDrawingOffset),textPaint);

    }


    protected boolean isFingerInsideButtom(MotionEvent event){


        double fingerX=event.getX();
        double fingerY=event.getY();

        //true X and Y point on center of buttom not top left corner
        double trueX = x + radius;
        double trueY = y + radius;

        //find distance frm touch to center of buttom
        double distanceXSquared =  (fingerX-trueX)*(fingerX-trueX);
        double distanceYSquared =  (fingerY-trueY)*(fingerY-trueY);
        double distanceSquared = distanceXSquared+distanceYSquared;



        if(radius*radius > distanceSquared){//if this true then finger is on buttom
           return true;

        }

        return false;
    }



    @Override
    public void setAdditionalDecreasePercentageOfTextSize(int newValue){//this function is dead for circleFunction
        this.AdditionalDecreasePercentageOfTextSize = newValue;
        if(textPaint!=null){
            //init Paint text size
            double newTextSize = (radius*2.0) - ((radius*2.0)*AdditionalDecreasePercentageOfTextSize/100);
            textPaint.setTextSize((float) newTextSize);

        }

    }


}
