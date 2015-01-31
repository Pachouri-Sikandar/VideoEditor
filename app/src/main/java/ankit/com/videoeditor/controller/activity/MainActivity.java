package ankit.com.videoeditor.controller.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ankit.com.videoeditor.R;
import ankit.com.videoeditor.util.CommonUtil;
import carousel3D.AppUtils;
import carousel3D.CarouselDataItem;
import carousel3D.CarouselView;
import carousel3D.CarouselViewAdapter;
import carousel3D.Singleton;


public class MainActivity extends Activity {

    Singleton singletonObject = Singleton.getInstance();
    CarouselViewAdapter  carouselViewAdapter = null;
    private final int m_nFirstItem = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //no keyboard unless requested by user

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // compute screen size and scaling
        Singleton.getInstance().InitGUIFrame(this);

        int padding = singletonObject.Scale(10);
        // create the interface : full screen container
        RelativeLayout panel = new RelativeLayout(this);
        panel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        panel.setPadding(padding, padding, padding, padding);
        panel.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.WHITE, Color.GRAY}));
        setContentView(panel);

        // copy images from assets to sdcard
        AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma1.png", "plasma1.png", false);
        AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma2.png", "plasma2.png", false);
        AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma3.png", "plasma3.png", false);
        AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma4.png", "plasma4.png", false);

        //Create carousel view documents
        ArrayList<CarouselDataItem> Docus = new ArrayList<CarouselDataItem>();
        for (int i = 0; i < 1000; i++) {
            CarouselDataItem docu;
            if (i % 4 == 0)
                docu = new CarouselDataItem("/mnt/sdcard/plasma1.png", 0, "1st Image " + i);
            else if (i % 4 == 1)
                docu = new CarouselDataItem("/mnt/sdcard/plasma2.png", 0, "2nd Image " + i);
            else if (i % 4 == 2)
                docu = new CarouselDataItem("/mnt/sdcard/plasma3.png", 0, "3rd Image " + i);
            else
                docu = new CarouselDataItem("/mnt/sdcard/plasma4.png", 0, "4th Image " + i);
            Docus.add(docu);
        }

        // create the carousel
        CarouselView coverFlow = new CarouselView(this);

        // create adapter and specify device independent items size (scaling)
        // for more details see: http://www.pocketmagic.net/2013/04/how-to-scale-an-android-ui-on-multiple-screens/

        carouselViewAdapter = new CarouselViewAdapter(this, Docus, singletonObject.Scale(400), singletonObject.Scale(300));
        coverFlow.setAdapter(carouselViewAdapter);
        coverFlow.setSpacing(-1 * singletonObject.Scale(150));
        coverFlow.setSelection(Integer.MAX_VALUE / 2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                //this item selection works when user scrolls and stops on an item..when its icon comes in front
                CarouselDataItem docu = (CarouselDataItem) carouselViewAdapter.getItem(i);
                if (docu != null)
                {
//                    CommonUtil.showToast(getApplicationContext(), "Clicked on: "+docu.getDocText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        AppUtils.AddView(panel, coverFlow, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, new int[][]{new int[]{RelativeLayout.CENTER_IN_PARENT}}, -1, -1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
