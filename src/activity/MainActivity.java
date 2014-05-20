package activity;

import com.magisto.uicompomentviewer.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUi();
    }
    
    private void initUi() {
        initDisplayInfo();
        initSpacing();
        initTextSize();
        initIconSize();
    }
    
    private void initIconSize() {
        // width
        find(R.id.icon_size_width_bar, SeekBar.class).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onStopTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                int heightPx = find(R.id.icon_placeholder, TextView.class).getHeight();
                find(R.id.icon_size_value, TextView.class).setText(String.format(getString(R.string.icon_size_label)
                        , convertPixelsToDp(progress)
                        , convertPixelsToDp(heightPx)
                        , progress
                        , heightPx));
                find(R.id.icon_placeholder, TextView.class).setWidth(progress);
            }
        });
        // height
        find(R.id.icon_size_height_bar, SeekBar.class).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onStopTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                int widthPx = find(R.id.icon_placeholder, TextView.class).getWidth();
                find(R.id.icon_size_value, TextView.class).setText(String.format(getString(R.string.icon_size_label)
                        , convertPixelsToDp(widthPx)
                        , convertPixelsToDp(progress)
                        , widthPx
                        , progress));
                find(R.id.icon_placeholder, TextView.class).setHeight(progress);
                find(R.id.scroll_view, ScrollView.class).post(new Runnable() {
                    
                    @Override
                    public void run() {
                        find(R.id.scroll_view, ScrollView.class).fullScroll(View.FOCUS_DOWN);                        
                    }
                });
            }
        });

        final int max = getMaxProgress(0);
        find(R.id.icon_size_width_bar, SeekBar.class).setMax(max);
        find(R.id.icon_size_height_bar, SeekBar.class).setMax(max);
        
        final int defaultSizePx = getResources().getDimensionPixelSize(R.dimen.icon_default_size);
        find(R.id.icon_size_width_bar, SeekBar.class).setProgress(defaultSizePx);
        find(R.id.icon_size_height_bar, SeekBar.class).post(new Runnable() {
            
            @Override
            public void run() {
                find(R.id.icon_size_height_bar, SeekBar.class).setProgress(defaultSizePx);                
            }
        });
    }

    private void initDisplayInfo() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        find(R.id.display_info_screen_size, TextView.class).setText(String.format(getString(R.string.display_info_screen_size)
                , metrics.widthPixels
                , metrics.heightPixels));
        
        find(R.id.display_info_density, TextView.class).setText(String.format(getString(R.string.display_info_density)
                , (int) metrics.density, metrics.densityDpi, getDensityLabel(metrics.densityDpi)));
    }

    private void initTextSize() {
        find(R.id.text_size_bar, SeekBar.class).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onStopTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                find(R.id.text_size_value, TextView.class).setText(String.format(getString(R.string.text_size_label)
                        , progress
                        , convertSpToPixels(progress)));
                find(R.id.text_size_test_text, TextView.class).setTextSize(progress);
            }
        });
        find(R.id.text_size_bar, SeekBar.class).setProgress(14);
    }

    private void initSpacing() {
        int elementWidthPx = getResources().getDimensionPixelSize(R.dimen.spacing_element_width);
        find(R.id.spacing_bar, SeekBar.class).setMax(getMaxProgress(elementWidthPx));
        find(R.id.spacing_bar, SeekBar.class).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            @Override
            public void onStopTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar view) {
                // do nothing
            }
            
            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                find(R.id.spacing_value, TextView.class).setText(String.format(getString(R.string.spacing_label)
                        , convertPixelsToDp(progress)
                        , progress));
                find(R.id.spacing_moving_element, TextView.class).setWidth(progress);
            }
        });
        find(R.id.spacing_bar, SeekBar.class).setProgress(getResources().getDimensionPixelSize(R.dimen.padding));
        find(R.id.spacing_element_width_value, TextView.class).setText(String.format(getString(R.string.spacing_element_width)
                , convertPixelsToDp(elementWidthPx)
                , elementWidthPx));
    }

    private <T extends View>T find(int id, Class<T> className){
        return className.cast(findViewById(id));
    }
    
    private int convertPixelsToDp(int px){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) (px / (metrics.densityDpi / 160f));
    }
    
    private int convertSpToPixels(int sp) {
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity);
    }
    
    private int getMaxProgress(int extraSpace) {
        return getResources().getDisplayMetrics().widthPixels - 2 * extraSpace;
    }
    
    private Object getDensityLabel(int dpi) {
        String value = null;
        switch (dpi) {
            case DisplayMetrics.DENSITY_LOW:
                value = "low";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                value = "medium";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                value = "high";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                value = "xhigh";
                break;
            case DisplayMetrics.DENSITY_TV:
                value = "tv";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                value = "xxhigh";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                value = "xxxhigh";
                break;
            default:
                value = "medium";
                break;
        }
        return value;
    }

}
