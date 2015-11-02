# VerticalStepsView
![image](https://github.com/oHeHeHou/VerticalStepsView/raw/master/app/src/main/res/drawable/sample.png)
# Usage
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:step="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:paddingTop="50dp"
    android:paddingLeft="30dp"
    tools:context=".MainActivity">

    <ohehehou.view.VerticalStepView
        android:id="@+id/step_view1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <ohehehou.view.VerticalStepView
        android:id="@+id/step_view2"
        android:layout_marginTop="20dp"
        android:layout_below="@id/step_view1"
        android:layout_width="wrap_content"
        android:layout_height="200dp"
        step:subLabelTextColor="@android:color/holo_red_dark"
        step:labelTextColor="@android:color/holo_blue_dark"
        step:progressColor="@color/darker_blue"
        step:normalColor="@color/black" />

    <ohehehou.view.VerticalStepView
        android:id="@+id/step_view3"
        android:layout_marginRight="20dp"
        android:layout_alignParentRight="true"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        step:lineThickness="8dp"
        step:labelTextSize="16sp"
        step:subLabelTextSize="10sp"
        step:circleRadius="14dp" />


</RelativeLayout>
```
```java
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
  ```
