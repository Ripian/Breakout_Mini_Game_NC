package com.example.stefan.breakout_mini_game_nc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;

import GameStates.GameStateManager;

public class MainGameWindow extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    public static Context context;
    public static GameStateManager gsm;
    public static double displayWidth;
    public static double displayheight;

    public MainGameWindow(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);


        displayheight = Resources.getSystem().getDisplayMetrics().heightPixels;
        displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        gameThread = new GameThread(getHolder(),this);
        this.gsm = new GameStateManager();
        setFocusable(true);
        setClickable(true);


    }



    @Override
    public void surfaceChanged(SurfaceHolder holder,int format, int width, int height){



    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        gameThread.setRunning(true);
        gameThread.start();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        try {
            gameThread.setRunning(false);
            gameThread.join();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }



    public void update(){
        this.gsm.update();


    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        this.gsm.draw(canvas);

    }

    @Override
    public boolean onTouchEvent (MotionEvent event){

        gsm.touchingEvent(event);

        return true;
    }





}
