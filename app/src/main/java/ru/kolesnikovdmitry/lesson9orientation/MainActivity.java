package ru.kolesnikovdmitry.lesson9orientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.SubMenuBuilder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    Чтобы запретить активности или целому приложению менять ориентацию, следует прописать в манифесте
    android:screenOrientation="portrait"
    android:screenOrientation="landscape" (на выбор)
     */

    /*
    чтобы активность не пересоздавалась при повороте экрана, опять в манифесте прописываем
    android:configChanges="keyboardHidden|orientation|screenSize"
     */



    public static int ORIENTATION_SCREEN    = 0; // 0 - portrait, 1- landscape;
    static final int MENU_MAIN_GROUP_ID     = 101;
    static final int MENU_MAIN_ITEM_PORT_ID = 102;
    static final int MENU_MAIN_ITEM_LAND_ID = 103;
    static  boolean menuItemSelectedLand = false;
    static  boolean menuItemSelectedPort = true;
    TextView mTextViewOrientationActMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewOrientationActMain = findViewById(R.id.textViewOrientationActMain);
        mTextViewOrientationActMain.setText(getScreenOrientation());

        if(getScreenOrientationBool()) {
            ORIENTATION_SCREEN = 1;
        }
        else {
            ORIENTATION_SCREEN = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuMainInf = getMenuInflater();
        menuMainInf.inflate(R.menu.menu_main, menu);

        SubMenu subMenuMain = menu.addSubMenu("Ориентация");

        if(getScreenOrientationBool()) {
            menuItemSelectedLand = true;
            menuItemSelectedPort = false;
        }
        else {
            menuItemSelectedPort = true;
            menuItemSelectedLand = false;
        }

        subMenuMain.add(MENU_MAIN_GROUP_ID, MENU_MAIN_ITEM_LAND_ID, Menu.NONE, "Альбомный").setChecked(menuItemSelectedLand);;
        subMenuMain.add(MENU_MAIN_GROUP_ID, MENU_MAIN_ITEM_PORT_ID, Menu.NONE, "Портретный").setChecked(menuItemSelectedPort);;

        subMenuMain.setGroupCheckable(MENU_MAIN_GROUP_ID, true, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_MAIN_ITEM_LAND_ID:
                item.setChecked(!item.isCheckable());
                ORIENTATION_SCREEN   = 1;
                menuItemSelectedPort = false;
                menuItemSelectedLand = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                break;
            case MENU_MAIN_ITEM_PORT_ID:
                item.setChecked(!item.isCheckable());
                ORIENTATION_SCREEN   = 0;
                menuItemSelectedLand = false;
                menuItemSelectedPort = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                break;
            case R.id.menuItemCheckOrientActMain:
                Toast.makeText(getApplicationContext(), getScreenOrientation(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    String getScreenOrientation() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return "Альбомная";
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return "Портретная";
        }
        return "Квадратная";
    }

    boolean getScreenOrientationBool() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }

    Boolean isLandOrient(Activity activity) {
        int width  = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        return width > height;
    }
}
