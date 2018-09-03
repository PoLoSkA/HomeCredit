package ru.polosatuk.homecredit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.polosatuk.homecredit.fragments.CostsAddFragment;
import ru.polosatuk.homecredit.fragments.IncomeAddFragment;


public class EnterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EnterActivity";
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    Button btnNextPage;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_data);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        btnNextPage = (Button) findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ChoseDate.class);
        startActivity(intent);

    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CostsAddFragment());
        adapter.addFragment(new IncomeAddFragment());
        viewPager.setAdapter(adapter);
    }
}
