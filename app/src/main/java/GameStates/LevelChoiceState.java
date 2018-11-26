package GameStates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;
import com.example.stefan.breakout_mini_game_nc.R;

import CustomButtons.CircleButton;

public class LevelChoiceState extends GameState { //unused


    private CircleButton backButtom;


    public LevelChoiceState(GameStateManager gsm, GameState.GameStateName gameStateName){
        super(gsm, gameStateName);



        //init bacground bitmap
        Bitmap rawBackgroundBitmap = BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.meniu_wall_paper);
        this.Background = Bitmap.createScaledBitmap(rawBackgroundBitmap,
                (int)MainGameWindow.displayWidth,
                (int)MainGameWindow.displayheight,
                false);

        //init buttoms
        //init back buttom


        backButtom =new CircleButton(gsm,
                GameStateName.MENIU,
                this,
                10.0,
                10.0,
                10.0,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.arrow_back_off),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.arrow_back_pressed),
                null,
                null);

        //init level choice buttoms



        buttomsOfState.add(backButtom);

    }

    @Override
    public void init(){}
    @Override
    public void update(){}
    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(Background,0,0,null);
        for(int i=0;i<buttomsOfState.size();i++){
            buttomsOfState.get(i).draw(canvas);

        }

    }
    @Override
    public void cleanUp(){}

}
