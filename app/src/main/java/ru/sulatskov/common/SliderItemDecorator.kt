package ru.sulatskov.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Integer.max

class SliderItemDecorator(val context: Context?) : RecyclerView.ItemDecoration() {
    private val colorActive = Color.parseColor("#008577")
    private val colorInactive = Color.parseColor("#DADADA")

    fun dpToPx(dp: Int): Int {
        val metrics: DisplayMetrics? = context?.resources?.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), metrics).toInt()
    }
    private val mIndicatorHeight = dpToPx(32)
    private val mIndicatorStrokeWidth = dpToPx(4)
    private val mIndicatorItemLength = dpToPx(4)
    private val mIndicatorItemPadding = dpToPx(10)
    private val mInterpolator: AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint: Paint = Paint()

    init{
        mPaint.strokeWidth = mIndicatorStrokeWidth.toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter?.itemCount ?: 0

        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        val indicatorPosY = parent.height - mIndicatorHeight / 2f
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild?.left
        val width = activeChild?.width
        val right = activeChild?.right

        val progress: Float? = width?.let {
            mInterpolator.getInterpolation((left?.times(-1) ?: 0) / it.toFloat())
        }
        progress?.let { drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, it) }
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = colorInactive
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float
    ) {
        mPaint.color = colorActive
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            val partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress
            c.drawCircle(
                highlightStart + partialLength,
                indicatorPosY,
                mIndicatorItemLength / 2f,
                mPaint
            )
        }
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }
}