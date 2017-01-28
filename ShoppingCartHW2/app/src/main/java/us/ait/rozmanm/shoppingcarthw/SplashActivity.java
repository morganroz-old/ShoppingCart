package us.ait.rozmanm.shoppingcarthw;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Animation splashAnim = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.splash_anim);

        final ImageView ivSplash = (ImageView) findViewById(R.id.ivSplash);
        ivSplash.startAnimation(splashAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}
