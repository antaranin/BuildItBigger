package udacity.jokeshower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;

public class JokeActivity extends AppCompatActivity
{
    public static String JOKE_EXTRA = "joke_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView jokeTv = ButterKnife.findById(this, R.id.joke_tv);
        jokeTv.setText(getIntent().getStringExtra(JOKE_EXTRA));
    }
}
