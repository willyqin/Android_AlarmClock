package com.example.han.myalarmclock;

import android.app.Activity;

/**
 * Created by Han on 2016/6/3.
 */
public class ThemeUtile {
    public enum ThemeType{
        Night,
        Red,
        Pink,
        Purple,
        Indigo,
        Blue,
        Cyan,
        Teal,
        Green,
        Lime,
        Amber,
        Yellow,
        Orange,
        Brown,
        Gray,
        BlueGray,;
    }
    public static ThemeType themeType = ThemeType.Teal;

    public static boolean night = false;
    public static void selectTheme(Activity activity){
        switch (themeType){
            case Night:
                activity.setTheme(R.style.AppThemeNight);
                break;
            case Red:
                activity.setTheme(R.style.AppThemeRed);
                break;
            case Pink:
                activity.setTheme(R.style.AppThemePink);
                break;
            case Purple:
                activity.setTheme(R.style.AppThemePurple);
                break;
            case Indigo:
                activity.setTheme(R.style.AppThemeIndigo);
                break;
            case Blue:
                activity.setTheme(R.style.AppThemeBlue);
                break;
            case Cyan:
                activity.setTheme(R.style.AppThemeCyan);
                break;
            case Teal:
                activity.setTheme(R.style.AppThemeCyan);
                break;
            case Green:
                activity.setTheme(R.style.AppThemeGreen);
                break;
            case Lime:
                activity.setTheme(R.style.AppThemeLime);
                break;
            case Yellow:
                activity.setTheme(R.style.AppThemeYellow);
                break;
            case Amber:
                activity.setTheme(R.style.AppThemeAmber);
                break;
            case Orange:
                activity.setTheme(R.style.AppThemeOrange);
                break;
            case Brown:
                activity.setTheme(R.style.AppThemeBrown);
                break;
            case Gray:
                activity.setTheme(R.style.AppThemeGray);
                break;
            case BlueGray:
                activity.setTheme(R.style.AppThemeBlueGray);
                break;
            default:
                break;
        }
    }
}
