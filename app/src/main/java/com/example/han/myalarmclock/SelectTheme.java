package com.example.han.myalarmclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Han on 2016/6/2.
 */
public class SelectTheme extends AppCompatActivity {
    private Button night,red,pink,purple,indigo,blue,cyan,teal,green,lime,yellow,amber,orange,brown,gray,blueGray;
    private Toolbar toolbar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtile.selectTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_theme_activity);

        preferences = getSharedPreferences("myClock",MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = (Toolbar) findViewById(R.id.theme_toolbar);
        toolbar.setTitle("选取主题");
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectTheme.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        night = (Button) findViewById(R.id.theme_night);
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Night) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Night;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkNight));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorBgNight));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkNight));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Night.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        red = (Button) findViewById(R.id.theme_red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Red) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Red;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorRed));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Red.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        pink = (Button) findViewById(R.id.theme_pink);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Pink) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Pink;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPink));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPink));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Pink.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        purple = (Button) findViewById(R.id.theme_purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Purple) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Purple;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPurple));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Purple.ordinal());
                    editor.commit();

                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        indigo = (Button) findViewById(R.id.theme_indigo);
        indigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Indigo) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Indigo;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorIndigo));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorIndigo));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Indigo.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        blue = (Button) findViewById(R.id.theme_blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Blue) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Blue;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlue));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Blue.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        cyan = (Button) findViewById(R.id.theme_cyan);
        cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Cyan) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Cyan;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorCyan));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorCyan));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Cyan.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        teal = (Button) findViewById(R.id.theme_teal);
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Teal) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Teal;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorTeal));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorTeal));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Teal.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(),"已选择该主题",Toast.LENGTH_SHORT).show();
            }
        });

        green = (Button) findViewById(R.id.theme_green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Green) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Green;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Green.ordinal());
                    editor.commit();

                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        lime = (Button) findViewById(R.id.theme_lime);
        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Lime) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Lime;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorLime));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorLime));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Lime.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        yellow = (Button) findViewById(R.id.theme_yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Yellow) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Yellow;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorYellow));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Yellow.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        amber = (Button) findViewById(R.id.theme_amber);
        amber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Amber) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Amber;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorAmber));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorAmber));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Amber.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        orange = (Button) findViewById(R.id.theme_orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Orange) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Orange;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Orange.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        brown = (Button) findViewById(R.id.theme_brown);
        brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Brown) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Brown;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorBrown));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorBrown));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Brown.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        gray = (Button) findViewById(R.id.theme_gray);
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.Gray) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.Gray;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorGray));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    editor.putInt("Theme", ThemeUtile.ThemeType.Gray.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });

        blueGray = (Button) findViewById(R.id.theme_bluegray);
        blueGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtile.themeType != ThemeUtile.ThemeType.BlueGray) {
                    ThemeUtile.themeType = ThemeUtile.ThemeType.BlueGray;
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlueGray));
                    findViewById(R.id.theme_activity).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlueGray));
                    editor.putInt("Theme", ThemeUtile.ThemeType.BlueGray.ordinal());
                    editor.commit();
                }
                Toast.makeText(getApplicationContext(), "已选择该主题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectTheme.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
