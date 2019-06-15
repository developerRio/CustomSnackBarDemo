package com.originalstocks.lottieanimdemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private View container;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container_layout);
    }

    public void showSnack(View view) {
        // That's how you'll show the SnackBar
        showSnackBar(container, "Daily Reward Claimed", "This is something New !!!");

    }

    private void showSnackBar(View container, String header, String content) {

        final Snackbar snackbar = Snackbar.make(container, header, Snackbar.LENGTH_LONG);
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hiding default text
        TextView textView = layout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = View.inflate(this, R.layout.my_snackbar, null);
        // Configure the view
        /*ImageView imageView = snackView.findViewById(R.id.image);
        imageView.setImageBitmap(image);*/
        TextView headerTextView = snackView.findViewById(R.id.snack_header_text);
        TextView contentTextView = snackView.findViewById(R.id.snack_content_text);
        headerTextView.setText(header);
        contentTextView.setText(content);

        //If the view is not covering the whole SnackBar layout, add this line
        layout.setPadding(0, 0, 0, 0);
        View viewContainer = snackbar.getView();
        // Getting rid of old school black snackBar color
        viewContainer.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        // If in case your parent layout is not coordinator
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewContainer.getLayoutParams();

        // but if you're using coordinator layout then change this above line with below or just comment this below line
        //  CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)viewContainer.getLayoutParams();
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;  // roughly around 200
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        params.setMargins(50, 50, 50, 50);

        viewContainer.setLayoutParams(params);
        // Add the view to the SnackBar's layout


        layout.addView(snackView, 0);

        snackbar.show();

    }

    public void showAnimation(View view) {

        initiatePopupWindow(view);

    }

    private void initiatePopupWindow(View v) {
        try {

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.progress_loader,
                    (ViewGroup) v.findViewById(R.id.progress_container));
            popupWindow = new PopupWindow(popupView, 600, 600, true);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            dimBehind(popupWindow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }


}
