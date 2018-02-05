package com.mm.gallerywatcher.util.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final static int DEFAULT_FIRST_ROW_SPACING_MULTIPLIER = 1;
    public final static int DEFAULT_TOP_SPACING_MULTIPLIER = 1;

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private int topSpaceMultiplier;
    private int lastBottomSpace;
    private int additionalFirstRowSpacing;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.topSpaceMultiplier = DEFAULT_FIRST_ROW_SPACING_MULTIPLIER;
    }

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int topSpaceMultiplier) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.topSpaceMultiplier = topSpaceMultiplier;
    }

    public void setLastBottomSpace(int lastBottomSpace) {
        this.lastBottomSpace = lastBottomSpace;
    }

    public void setAdditionalFirstRowSpacing(int additionalFirstRowSpacing) {
        this.additionalFirstRowSpacing = additionalFirstRowSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column
        final int itemCount = state.getItemCount();

        //признак заполненности последней строки таблицы (чило элементов кратно числу столбцов)
        boolean isLastStringFull = (itemCount % spanCount) == 0;

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                int top = spacing * topSpaceMultiplier;
                if (additionalFirstRowSpacing > 0) {
                    top = top + additionalFirstRowSpacing;
                }
                outRect.top = top;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }

        //для последнего ряда в таблице элеметов добавляется дополнительное поле снизу (чтобы не слипалось с нижним баром)
        if (lastBottomSpace > 0 && itemCount > 0
                //случай, когда нижняя строка заполнена целиком
                && ((isLastStringFull && (position >= itemCount - spanCount))
                //случай, когда нижняя строка не полна
                || (!isLastStringFull && (position >= itemCount - itemCount % spanCount)))) {
            outRect.bottom = lastBottomSpace;
        }
    }
}