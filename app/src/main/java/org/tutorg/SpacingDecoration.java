package org.tutorg;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * This creates spacing for our RecyclerView
 *
 * @author Sharaf Zaman
 */
public class SpacingDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacingDecoration(Context context, int spaceInDp) {
        this.space = dpToPx(context, spaceInDp);
    }

    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = space;
        outRect.bottom = space;
    }
}
