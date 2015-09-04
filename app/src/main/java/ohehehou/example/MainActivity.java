package ohehehou.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ohehehou.view.VerticalStepView;

public class MainActivity extends AppCompatActivity {

    private VerticalStepView stepView;
    private VerticalStepView stepView2;
    private VerticalStepView stepView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = (VerticalStepView) findViewById(R.id.step_view1);
        stepView.addStepItem("Step1");
        stepView.addStepItem("Step2");

        stepView2 = (VerticalStepView) findViewById(R.id.step_view2);
        stepView2.addStepItem("A");
        stepView2.addStepItem("测试", "2015-05-01 13:05:99");
        stepView2.addStepItem("测试2", "2015-05-01 13:05:99");
        stepView2.setProgress(2);

        stepView3 = (VerticalStepView) findViewById(R.id.step_view3);
        stepView3.addStepItem("步骤1", "补充说明");
        stepView3.addStepItem("步骤2");
        stepView3.addStepItem("步骤3");
        stepView3.addStepItem("步骤4");
        stepView3.setProgress(3);

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
}
