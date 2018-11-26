package GameStates;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class GameOverState extends GameState {


    public GameOverState(GameStateManager gsm, GameState.GameStateName gameStateName){
        super(gsm,gameStateName);
    }


    @Override
    public void init(){}
    @Override
    public void update(){}
    @Override
    public void draw(Canvas canvas){}
    @Override
    public void cleanUp(){}

}
