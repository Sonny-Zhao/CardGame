package com.tianyu.cardgame.activity;
/**
 * All rights Reserved, Designed By Vongvia
 *
 * @Title: 程序的主界面以及处理方法
 * @author: Vongvia 欢迎各位童鞋来交流 ：441256563
 * @date: 2015.11.17
 * @version V1.0
 */

import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import com.tianyu.cardgame.R;
import com.tianyu.cardgame.activity.AboutActivity;
import com.tianyu.cardgame.model.GetIndex;
import com.tianyu.cardgame.service.EffectService;
import com.tianyu.cardgame.service.SoundService;
import com.tianyu.cardgame.view.ExplosionField;


import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.*;

public class MainActivity extends Activity {

    private ExplosionField mExplosionField;
    // 图片控件数组
    static ImageView[] PictureBoxs = new ImageView[12];
    // 图片索引
    static int[] Index = new int[12];

    // 判断点击的次数，最大为2
    private int Count = 0;
    // 存储点击图片索引的数组
    private int[] ClickID = new int[2];

    // 定义错误次数，最大为3
    int wrongnum = 0;
    // 检测是否已经玩过游戏（用来提示用户第一次点击新游戏后才能点击卡牌）
    private int alstart = 0;
    // 游戏总时间
    int alltime = 0;
    private TextView tvShow;// 显示时间
    private TextView TV_num;// 显示错误次数
    private int socore = 0; // 分数
    int setWrongnum = 3;
    int settime = 60;
    int memtime = 3;
    Boolean canTouch = false; // 防止 在记忆时间内 被点击
    Message msg;

    // 用于保存 设置信息
    SharedPreferences preferences;
    SharedPreferences.Editor sp_edit;
    // 分配图片资源
    public static int[] ImageSource =
            {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,
                    R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11,
                    R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16,
                    R.drawable.image17, R.drawable.image18, R.drawable.image19, R.drawable.image20, R.drawable.image21,
                    R.drawable.image22, R.drawable.image23, R.drawable.image24, R.drawable.image25, R.drawable.image26,
                    R.drawable.image27, R.drawable.image28, R.drawable.image29, R.drawable.image30, R.drawable.image31,
                    R.drawable.image32, R.drawable.image33, R.drawable.image34, R.drawable.image35, R.drawable.image36,
                    R.drawable.image36};

    void init() {

        // 给ImageView数字绑定ID
        PictureBoxs[0] = (ImageView) findViewById(R.id.ImageView00);
        PictureBoxs[1] = (ImageView) findViewById(R.id.ImageView01);
        PictureBoxs[2] = (ImageView) findViewById(R.id.ImageView02);
        PictureBoxs[3] = (ImageView) findViewById(R.id.ImageView03);
        PictureBoxs[4] = (ImageView) findViewById(R.id.ImageView04);
        PictureBoxs[5] = (ImageView) findViewById(R.id.ImageView05);
        PictureBoxs[6] = (ImageView) findViewById(R.id.ImageView06);
        PictureBoxs[7] = (ImageView) findViewById(R.id.ImageView07);
        PictureBoxs[8] = (ImageView) findViewById(R.id.ImageView08);
        PictureBoxs[9] = (ImageView) findViewById(R.id.ImageView09);
        PictureBoxs[10] = (ImageView) findViewById(R.id.ImageView10);
        PictureBoxs[11] = (ImageView) findViewById(R.id.ImageView11);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_exit = (Button) findViewById(R.id.Button_Exit);
        Button btn_about = (Button) findViewById(R.id.Button_About);
        Button btn_newgame = (Button) findViewById(R.id.Button_NewGame);

        /*
         * Inflater inflater=new Inflater();
         *
         * final LinearLayout rootView=(LinearLayout)inflater.inflate(buf,
         * offset, byteCount) ScaleAnimation sAnimation=new ScaleAnimation(0,
         * 1,0,1); sAnimation.setDuration(5000);
         *
         * LayoutAnimationController la=new
         * LayoutAnimationController(sAnimation, 0.5f);
         */
        preferences = getSharedPreferences("CardsGame", MODE_APPEND);
        sp_edit = preferences.edit();

        setWrongnum = preferences.getInt("Wrongnum", 3);
        settime = preferences.getInt("gametime", 100);
        memtime = preferences.getInt("memtime", 3);

        tvShow = (TextView) findViewById(R.id.tv_Time);
        TV_num = (TextView) findViewById(R.id.IV_num);

        mExplosionField = ExplosionField.attach2Window(this);// 开启特效的语句

        init();

        // 点击“新游戏”
        btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alltime = 0; // 时间清0
                wrongnum = 0; // 错误次数清0
                socore = 0; // 分数清0
                canTouch = false;
                TV_num.setText(" " + wrongnum);
                Index = GetIndex.Getnum();// 获得随机数

                if (alstart != 0) // 如果已经开始过游戏（说明有可能有牌消除了），就将已经消除的牌重新显示出来
                {
                    reset(findViewById(R.id.IV_Group));
                    Ahandler.removeCallbacks(myRunnable);// 注销计时
                }
                // 给图片控件分配资源
                for (int i = 0; i < 12; i++) {
                    PictureBoxs[i].setImageResource(ImageSource[Index[i]]);
                }
                // 开始计时
                Ahandler.post(myRunnable);

                // 开始播放背景音乐
                Intent intent = new Intent(MainActivity.this, SoundService.class);
                intent.putExtra("playing", true);
                startService(intent);

                // 标记已经开始过游戏
                alstart++;

            }
        });

        // 点击退出按钮
        btn_exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Ahandler.removeCallbacks(myRunnable);// 停止计时
                stopmusic();
                finish();

            }
        });

        // 点击关于
        btn_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }

    /** 监听对话框里面的button点击事件 */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                {
                    Ahandler.removeCallbacks(myRunnable);// 停止计时
                    stopmusic();
                    finish();
                    break;
                }
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    public void settingB(View source) {
        final LinearLayout set = (LinearLayout) getLayoutInflater().inflate(R.layout.settings, null);
        EditText etw = (EditText) set.findViewById(R.id.ev_wrongnum);
        EditText ett = (EditText) set.findViewById(R.id.ev_time);
        EditText etm = (EditText) set.findViewById(R.id.ev_mem);
        etw.setText("" + setWrongnum);
        ett.setText("" + settime);
        etm.setText("" + memtime);
        new AlertDialog.Builder(this)
                // 设置对话框的图标
                .setIcon(R.drawable.set_icon)
                // 设置对话框的标题
                .setTitle("游戏设置")
                // 设置对话框显示的View对象
                .setView(set)
                // 为对话框设置一个“确定”按钮
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText etw = (EditText) set.findViewById(R.id.ev_wrongnum);
                        EditText ett = (EditText) set.findViewById(R.id.ev_time);
                        EditText etm = (EditText) set.findViewById(R.id.ev_mem);

                        setWrongnum = Integer.parseInt(etw.getText().toString());
                        settime = Integer.parseInt(ett.getText().toString());
                        memtime = Integer.parseInt(etm.getText().toString());
                        // 在这里写入 数据

                        sp_edit.putInt("Wrongnum", setWrongnum);
                        sp_edit.putInt("gametime", settime);
                        sp_edit.putInt("memtime", memtime);
                        sp_edit.commit();
                        Toast.makeText(getApplicationContext(), "设置成功~", Toast.LENGTH_LONG).show();

                    }
                })
                // 为对话框设置一个“取消”按钮
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取消登录，不做任何事情。
                    }
                })
                // 创建、并显示对话框
                .create().show();

    }

    // 停止音乐
    void stopmusic() {
        Intent intent = new Intent(MainActivity.this, EffectService.class);
        intent.putExtra("what", "quit");
        startService(intent);

        Intent nintent = new Intent(MainActivity.this, SoundService.class);
        nintent.putExtra("quit", true);
        startService(nintent);
    }

    // 重新开始，让所有ImageView设置为可以显示
    private void reset(View root) {
        mExplosionField.clear();

        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }

    // 计时器
    private Handler Ahandler = new Handler();

    private Runnable myRunnable = new Runnable() {
        public void run() {
            Ahandler.postDelayed(this, 1000);
            tvShow.setText("" + Integer.toString(alltime++));
            if (alltime == memtime) // 3秒后 所有牌翻面
            {
                CardTurn();
                canTouch = true;
            } else if (alltime == settime) {
                // 游戏时间到
                canTouch = false;
                Intent nintent = new Intent(MainActivity.this, SoundService.class);
                nintent.putExtra("quit", true);
                startService(nintent);

                Intent intent = new Intent(MainActivity.this, EffectService.class);
                intent.putExtra("what", "lose");
                startService(intent);

                Toast.makeText(getApplicationContext(), "游戏时间到~~您输啦", Toast.LENGTH_LONG).show();
                tvShow.setText("" + alltime);
                alltime = 0;
                Ahandler.removeCallbacks(myRunnable);// 停止计时
            }

        }
    };

    /**
     * 牌翻转
     */
    public static void CardTurn() {
        for (int i = 0; i < 12; i++) {
            PictureBoxs[i].setImageResource(R.drawable.card);
        }
    }

    // 点击卡牌后的处理
    public void CardTouch(View v) {
        if (canTouch) {
            Intent intent = new Intent(MainActivity.this, EffectService.class);
            intent.putExtra("what", "selected");
            startService(intent);
            if (alstart == 0 || (wrongnum == (setWrongnum + 1) && alstart != 0)) {
                // 游戏刚开始牌没分配或游戏结束，牌区不能点击
                Toast.makeText(this, "先点击“新游戏”哦~", Toast.LENGTH_SHORT).show();

            } else {
                Count++;// 点击次数+1

                if (Count > 2) // 假设玩家迅速点击了3次，则第三次不计入
                {
                    Count = 2;
                } else {
                    // 根据点击牌的ID进行判断 曾经分配的图片是否相同，用ClickId保存。并把牌翻过来
                    switch (v.getId()) {
                        case R.id.ImageView00:
                            ClickID[Count - 1] = 0;
                            PictureBoxs[0].setImageResource(ImageSource[Index[0]]);

                            break;
                        case R.id.ImageView01:
                            ClickID[Count - 1] = 1;
                            PictureBoxs[1].setImageResource(ImageSource[Index[1]]);

                            break;
                        case R.id.ImageView02:
                            ClickID[Count - 1] = 2;
                            PictureBoxs[2].setImageResource(ImageSource[Index[2]]);

                            break;
                        case R.id.ImageView03:
                            ClickID[Count - 1] = 3;
                            PictureBoxs[3].setImageResource(ImageSource[Index[3]]);

                            break;
                        case R.id.ImageView04:
                            ClickID[Count - 1] = 4;
                            PictureBoxs[4].setImageResource(ImageSource[Index[4]]);

                            break;
                        case R.id.ImageView05:
                            ClickID[Count - 1] = 5;
                            PictureBoxs[5].setImageResource(ImageSource[Index[5]]);

                            break;
                        case R.id.ImageView06:
                            ClickID[Count - 1] = 6;
                            PictureBoxs[6].setImageResource(ImageSource[Index[6]]);

                            break;
                        case R.id.ImageView07:
                            ClickID[Count - 1] = 7;
                            PictureBoxs[7].setImageResource(ImageSource[Index[7]]);

                            break;
                        case R.id.ImageView08:
                            ClickID[Count - 1] = 8;
                            PictureBoxs[8].setImageResource(ImageSource[Index[8]]);

                            break;
                        case R.id.ImageView09:
                            ClickID[Count - 1] = 9;
                            PictureBoxs[9].setImageResource(ImageSource[Index[9]]);

                            break;
                        case R.id.ImageView10:
                            ClickID[Count - 1] = 10;
                            PictureBoxs[10].setImageResource(ImageSource[Index[10]]);

                            break;
                        case R.id.ImageView11:
                            ClickID[Count - 1] = 11;
                            PictureBoxs[11].setImageResource(ImageSource[Index[11]]);
                            break;
                        default:
                            // 只给这12个ImageView绑定了点击事件，一般来说此处不可能触法
                            break;
                    }
                }

                if (Count == 2)// 点击了两次进行判断
                {

                    // 加入Handler是因为需要延迟0.5秒，显示出第二张牌翻面的效果，否则将不显示第二张牌翻过来而直接进行判断
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        public void run() {

                            if (ClickID[0] != ClickID[1] && Index[ClickID[0]] == Index[ClickID[1]])// 成功消除
                            {
                                // 消除特效
                                mExplosionField.explode(PictureBoxs[ClickID[0]]);
                                mExplosionField.explode(PictureBoxs[ClickID[1]]);
                                // v.setOnClickListener(null);

                                // 分数加1
                                socore++;

                                // 触法音效
                                Intent eintent = new Intent(MainActivity.this, EffectService.class);
                                eintent.putExtra("what", "effect");
                                startService(eintent);

                                // 总分数到6，也就是消除了6组，游戏胜利
                                if (socore == 6) {

                                    // 关闭游戏背景音乐
                                    Intent nintent = new Intent(MainActivity.this, SoundService.class);
                                    nintent.putExtra("quit", true);
                                    startService(nintent);

                                    // 触法胜利音效
                                    Intent intent = new Intent(MainActivity.this, EffectService.class);
                                    intent.putExtra("what", "win");
                                    startService(intent);
                                    // 提示游戏胜利
                                    Toast.makeText(getApplicationContext(), "恭喜你赢得胜利", Toast.LENGTH_LONG).show();

                                    // 固定游戏时间
                                    tvShow.setText("" + alltime);

                                    Ahandler.removeCallbacks(myRunnable);// 停止计时
                                }
                            } else {
                                // 错误，两张牌再翻过去
                                PictureBoxs[ClickID[0]].setImageResource(R.drawable.card);
                                PictureBoxs[ClickID[1]].setImageResource(R.drawable.card);
                                // 错误计数+1
                                wrongnum++;
                                if (wrongnum == (setWrongnum + 1))
                                    TV_num.setText("" + (wrongnum - 1));
                                else
                                    TV_num.setText("" + wrongnum);
                                if (wrongnum == (setWrongnum + 1)) {
                                    // 错误次数达到上限，失败

                                    Intent nintent = new Intent(MainActivity.this, SoundService.class);
                                    nintent.putExtra("quit", true);
                                    startService(nintent);

                                    Intent intent = new Intent(MainActivity.this, EffectService.class);
                                    intent.putExtra("what", "lose");
                                    startService(intent);

                                    Toast.makeText(getApplicationContext(), "胜败乃兵家常事~大侠请重新来过", Toast.LENGTH_LONG)
                                            .show();
                                    tvShow.setText("" + alltime);
                                    alltime = 0;
                                    Ahandler.removeCallbacks(myRunnable);// 停止计时
                                    canTouch = false;
                                }
                            }
                            Count = 0;
                        }

                    }, 500);
                }
            }
        }
    }

}
