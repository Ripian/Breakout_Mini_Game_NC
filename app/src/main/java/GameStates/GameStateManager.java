package GameStates;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;
import com.example.stefan.breakout_mini_game_nc.R;

import java.util.ArrayList;

import LevelObjects.Brick;
import LevelObjects.PowerUp;

public class GameStateManager {


    public ArrayList<GameState> gameStates;//0 meniu       1 levelchoice    2 levelstate    3gameover
    private GameState currentGameState;
    public static boolean useNotInitStateChanger=true;
    public static String choosenLevelDataPath;

    public GameStateManager(){



        this.gameStates = new ArrayList<GameState>();
        gameStates.add(new MeniuState(this, GameState.GameStateName.MENIU));
        gameStates.add(new LevelChoiceState(this, GameState.GameStateName.LEVELCHOICE));
        gameStates.add(new LevelState(this,
                GameState.GameStateName.PLAYABLELEVEL,
                (BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.brick_one_hit)),
                (BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.brick_two_hit)),
                (BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.brick_three_hit))));
        gameStates.add(new GameOverState(this, GameState.GameStateName.GAMEOVER));
        gameStates.add(new InGameMeniu(this, GameState.GameStateName.INGAMEMENIU));



        //made loop to not make stupid mistake of initilization, if later I decide to change something with states
        for(int i=0;i<gameStates.size();i++){
            if(gameStates.get(i).getStateName()==GameState.GameStateName.MENIU){
                currentGameState=gameStates.get(i);// should be MENIU state
            }
        }


        PowerUp.initStatics();



    }



    public void update(){
        currentGameState.update();

    }



    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        currentGameState.draw(canvas);


    }


    public void touchingEvent(MotionEvent event){
        currentGameState.touchingEvent(event);


    }





    //setters getters



    //made this for simulation of default argument value
    public void changeGameState(GameState.GameStateName nameOfNewGameState){
        changeGameState(nameOfNewGameState,true);

    }

    public void changeGameState_dontInitNewState(GameState.GameStateName nameOfNewGameState){ // used for ""in game meniu""



        if(nameOfNewGameState==null){

            Activity activity = (Activity)MainGameWindow.context;
            activity.finish();
            System.exit(0);
        }


        //choose active gameState by name given in function parameter
        for(int i=0;i<gameStates.size();i++){
            if(gameStates.get(i).getStateName()==nameOfNewGameState){
                currentGameState=gameStates.get(i);
            }
        }



        if(GameState.GameStateName.MENIU== nameOfNewGameState || InGameMeniu.isGameOver){
            //init level

            for(int i=0;i<gameStates.size();i++){
                if(gameStates.get(i).getStateName().nameOfState==nameOfNewGameState.PLAYABLELEVEL.nameOfState){
                    gameStates.get(i).init();
                    currentGameState =  gameStates.get(i);
                }
            }
        }
    }

    public void changeGameState(GameState.GameStateName nameOfNewGameState,boolean eraseOldGameState){
       /*
        if(useNotInitStateChanger) {


            changeGameState_dontInitNewState(nameOfNewGameState);//redirect function if special case of no initilizing
            return;
        }
        */

        if(nameOfNewGameState==null){

           Activity activity = (Activity)MainGameWindow.context;
            activity.finish();
            System.exit(0);
        }

        if(eraseOldGameState){
         //   currentGameState.cleanUp();
        }

        //choose active gameState by name given in function parameter
        for(int i=0;i<gameStates.size();i++){
            if(gameStates.get(i).getStateName().nameOfState==nameOfNewGameState.nameOfState){
                currentGameState=gameStates.get(i);
            }
        }



        if(GameState.GameStateName.PLAYABLELEVEL==nameOfNewGameState && (!InGameMeniu.isGameOver))
        {}else{

            currentGameState.init();
        }
        //currentGameState.init();


/*
        if((nameOfNewGameState.nameOfState == GameState.GameStateName.PLAYABLELEVEL.nameOfState ) && InGameMeniu.isGameOver) {

            currentGameState=nameOfNewGameState;
            currentGameState.init();

        }


        if((nameOfNewGameState.nameOfState == GameState.GameStateName.PLAYABLELEVEL.nameOfState ) && !InGameMeniu.isGameOver) {


        }else{
            currentGameState.init();
        }
*/
    }


    public GameState getCurrentState(){return currentGameState;}




}
