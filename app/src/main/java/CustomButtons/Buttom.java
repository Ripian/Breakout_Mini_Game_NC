package CustomButtons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

import GameStates.GameState;
import GameStates.GameStateManager;

public abstract class Buttom {


    protected GameStateManager gsm;
    protected GameState referenceToOwner;

    protected double x;
    protected double y;
    protected boolean isPressed=false;
    protected boolean isClicked=false;
    protected Bitmap buttonImageNormal;
    protected Bitmap buttonImagePressed;
    protected GameState.GameStateName nextGameStateOnClick;
    protected String buttomText;
    protected int AdditionalDecreasePercentageOfTextSize=25; //example: if button height is 100, letters will have size of 75
    public Paint textPaint;
    protected double StringXDrawingOffset; //relevant only to rectangle buttom, text drawing not supported in circleButtom
    protected double StringYDrawingOffset; //relevant only to rectangle buttom, text drawing not supported in circleButtom


    public Buttom(GameStateManager gsm,
                  GameState.GameStateName nextGameStateOnClick,
                  GameState ownerState,
                  double percentageOfXToScreen,
                  double percentageOfYToScreen,
                  String stringToDraw,
                  Paint textPaint
                  )
    {
        this.gsm=gsm;
        this.nextGameStateOnClick = nextGameStateOnClick;
        this.referenceToOwner = ownerState;
        this.x = MainGameWindow.displayWidth*percentageOfXToScreen/100.0;
        this.y = MainGameWindow.displayheight*percentageOfYToScreen/100.0;
        this.buttomText = stringToDraw;
        this.textPaint = textPaint;


    }


    public abstract void draw(Canvas canvas);
    public abstract void touchEvent(MotionEvent event);
    protected abstract void manageClick(MotionEvent event);
    protected abstract void drawText(Canvas canvas);
    public abstract void setAdditionalDecreasePercentageOfTextSize(int newValue);



    public void setButtomText(String text){this.buttomText = text;}
    public boolean getIsClicked(){return isClicked;}

}
