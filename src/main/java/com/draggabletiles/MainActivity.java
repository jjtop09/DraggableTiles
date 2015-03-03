package com.draggabletiles;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

class Tile extends TextView {

    public int mRow, mCol;
    public String mValue;

    public Tile(Context context) {
        super(context);

        this.setBackgroundResource(R.drawable.rounded_corner);
        this.setLayoutParams(new ActionBar.LayoutParams(50, 50));
        this.setGravity(Gravity.CENTER);
    }

    public void setValue(String val) {
        mValue = val;
        setText(mValue);
        invalidate();
        requestLayout();
    }
}

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int NUM_OF_TILES = 4;

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layout_main);
        mainLayout.setOnDragListener(new TileDragListener());
        ViewGroup tileLayout = (ViewGroup)findViewById(R.id.tile_area);

        for (int i=0; i<NUM_OF_TILES; i++) {
            Tile t = new Tile(this);
            t.setValue(String.valueOf(i));
            tileLayout.addView(t);

            t.setOnTouchListener(new TileTouchListener());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final class TileTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class TileDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    TextView dragged = (TextView) event.getLocalState();
                    ViewGroup owner = (ViewGroup) dragged.getParent();
                    owner.removeView(dragged);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(dragged);
                    dragged.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
