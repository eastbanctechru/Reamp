package example.reamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.reamp.basic.BasicActivity;
import example.reamp.basic.BasicFragmentActivity;
import example.reamp.lifecycle.LongRunningActivity;
import example.reamp.lifecycle.ShortRunningActivity;
import example.reamp.login.LoginActivity;
import example.reamp.navigation.NavigationActivity;
import example.reamp.share.ParticularActivity;
import example.reamp.view.CustomViewActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, BasicActivity.class));
            }
        });
        findViewById(R.id.basic_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, BasicFragmentActivity.class));
            }
        });
        findViewById(R.id.life_cycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LongRunningActivity.class));
            }
        });
        findViewById(R.id.life_cycle2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, ShortRunningActivity.class));
            }
        });
        findViewById(R.id.navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, NavigationActivity.class));
            }
        });
        findViewById(R.id.presenter_sharing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ParticularActivity.getRootIntent(WelcomeActivity.this));
            }
        });
        findViewById(R.id.custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, CustomViewActivity.class));
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });
    }
}
