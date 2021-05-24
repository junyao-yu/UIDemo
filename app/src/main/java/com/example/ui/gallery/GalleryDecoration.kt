package com.example.ui.gallery

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * Created by yujunyao@xinrenlei.net on 5/24/21.
 */
class GalleryDecoration: RecyclerView.ItemDecoration() {

    var mItemConsumeX = 0

    var mPageMargin = 0
    var mLeftPageVisibleWidth = 0


    private var mInitPos = 0
    private fun onItemSizeMeasured(size: Int) {
        if (mInitPos < 0) {
            return
        }
        if (mInitPos == 0) {
            galleryRecyclerView?.scrollToPosition(0)
        } else {
            galleryRecyclerView?.smoothScrollBy(mInitPos * size, 0)
        }
        mInitPos = -1
    }

    private var galleryRecyclerView: RecyclerView? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)


        galleryRecyclerView = parent
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount!!


        parent.post {
            var itemNewWidth = parent.width - 4 * mPageMargin - 2 * mLeftPageVisibleWidth
            mItemConsumeX = itemNewWidth + 2 * mPageMargin

            onItemSizeMeasured(mItemConsumeX)

            var leftMargin = if (position == 0) mLeftPageVisibleWidth + 2 * mPageMargin else  mPageMargin
            var rightMargin = if (position == itemCount - 1) mLeftPageVisibleWidth + 2 * mPageMargin else  mPageMargin

            setLayoutParams(view, leftMargin, 0, rightMargin, 0, itemNewWidth, parent.height)
        }
    }

    private fun setLayoutParams(
        itemView: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        itemWidth: Int,
        itemHeight: Int
    ) {
//        DLog.d(
//            TAG, "GalleryItemDecoration setLayoutParams -->" + "left=" + left + ";top=" + top
//                    + ";right=" + right + ";bottom=" + bottom + ";itemWidth=" + itemWidth + ";itemHeight=" + itemHeight
//        )
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        var mMarginChange = false
        var mWidthChange = false
        var mHeightChange = false
        if (lp.leftMargin !== left || lp.topMargin !== top || lp.rightMargin !== right || lp.bottomMargin !== bottom) {
            lp.setMargins(left, top, right, bottom)
            mMarginChange = true
        }
        if (lp.width !== itemWidth) {
            lp.width = itemWidth
            mWidthChange = true
        }
        if (lp.height !== itemHeight) {
            lp.height = itemHeight
            mHeightChange = true
        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            itemView.layoutParams = lp
        }
    }

}