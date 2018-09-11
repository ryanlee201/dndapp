package com.example.dndapp;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import junit.framework.Test;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

public class TestSwipeController extends ItemTouchHelper.SimpleCallback{
    private TestSwipeControllerListener controller;


    public TestSwipeController(int dragDirs, int swipeDirs, TestSwipeControllerListener controller) {
        super(dragDirs, swipeDirs);
        this.controller = controller;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }


    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((CharacterViewAdapter.ViewHolder) viewHolder).viewForeground;

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CharacterViewAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CharacterViewAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CharacterViewAdapter.ViewHolder) viewHolder).viewForeground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        controller.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface TestSwipeControllerListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

}

