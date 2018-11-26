package GameStates;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import CustomButtons.Buttom;

public abstract class GameState {


    //just in case if I will need to know what state gameStateManager is using right now
    public enum GameStateName {
        MENIU("MeniuGameState"),
        LEVELCHOICE("LevelChoiceState"),
        PLAYABLELEVEL("LevelState"),
        GAMEOVER("GameOverState"),
        INGAMEMENIU("InGameMeniu");

        public final String nameOfState;

        GameStateName(String nameOfState){
            this.nameOfState = nameOfState;

        }
    }

    protected GameStateManager gsm;
    protected GameStateName gameStateName;
    protected ArrayList<Buttom> buttomsOfState;
    protected Bitmap Background;

    public GameState(GameStateManager gsm,GameStateName nameOfGameState){
        this.gsm=gsm;
        this.gameStateName = nameOfGameState;
        buttomsOfState= new ArrayList<Buttom>();

    }



    public abstract void init();
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void cleanUp();








    public void touchingEvent(MotionEvent event){
        for(int i=0;i<buttomsOfState.size();i++){
            buttomsOfState.get(i).touchEvent(event);

        }

    }


    public GameStateName getStateName(){return this.gameStateName;}

}
