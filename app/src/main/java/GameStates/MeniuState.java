package GameStates;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;
import com.example.stefan.breakout_mini_game_nc.R;

import CustomButtons.CircleButton;
import CustomButtons.RectangleButtom;

public class MeniuState extends GameState {


    private RectangleButtom startButtom;
    private RectangleButtom exitButtom;


    private int startButtomXPercentage = 30;
    private int startButtomYPercentage = 30;
    private int startButtomWIDTHPercentage = 40;
    private int startButtomHEIGHTPercentage = 10;

    private int exitButtomXPercentage = 35;
    private int exitButtomYPercentage = 55;
    private int exitButtomWIDTHPercentage = 30;
    private int exitButtomHEIGHTPercentage = 10;

 //   private Activity referenceToActivity;


    public MeniuState(GameStateManager gsm, GameState.GameStateName gameStateName){
        super(gsm,gameStateName);

        //init bacground bitmap
        Bitmap rawBackgroundBitmap = BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.meniu_wall_paper);
        this.Background = Bitmap.createScaledBitmap(rawBackgroundBitmap,
                (int)MainGameWindow.displayWidth,
                (int)MainGameWindow.displayheight,
                false);



        //init buttoms
        Paint startAndExitButtomPaintForText = new Paint();
        startAndExitButtomPaintForText.setColor(0xFF965200);


        startButtom = new RectangleButtom(gsm,
                GameStateName.PLAYABLELEVEL,
                this,
                startButtomXPercentage,
                startButtomYPercentage,
                startButtomWIDTHPercentage,
                startButtomHEIGHTPercentage,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_off),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_pressed),
                "Start",
                startAndExitButtomPaintForText
                );

        exitButtom = new RectangleButtom(gsm,
                null, // null = exit app
                this,
                exitButtomXPercentage,
                exitButtomYPercentage,
                exitButtomWIDTHPercentage,
                exitButtomHEIGHTPercentage,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_off),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_pressed),
                "Exit",
                startAndExitButtomPaintForText);



        buttomsOfState.add(exitButtom);
        buttomsOfState.add(startButtom);


    }
    @Override
    public void init(){

       // gsm.useNotInitStateChanger=false;
    }
    @Override
    public void update(){

    }





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
