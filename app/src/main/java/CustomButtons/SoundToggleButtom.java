package CustomButtons;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.MotionEvent;

import GameStates.GameState;
import GameStates.GameStateManager;

public class SoundToggleButtom extends CircleButton {

    public SoundToggleButtom(GameStateManager gsm,
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


        super(gsm,
               nextGameStateOnClick,
                ownerState,
        percentageOfXToScreen,
        percentageOfYToScreen,
        percenageOfRadiusToWholeScreenByWidth,
        rawBitmapNormal,
        rawBitmapPressed,
        stringToDraw,
        textPaint);









    }



    @Override
    protected void manageClick(MotionEvent event){
        if(isFingerInsideButtom(event)){

            isPressed=!isPressed;

        }

    }

    @Override
    public void touchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){

            manageClick(event);
        }

    }







}
