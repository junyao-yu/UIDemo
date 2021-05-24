package com.example.ui.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.example.ui.R
import kotlinx.android.synthetic.main.activity_gallery.*

/**
 * Created by yujunyao@xinrenlei.net on 5/24/21.
 *
 * RecyclerView实现画廊
 * 1、自定义IteDecoration
 * 2、通过滑动的时候缩放View以及通过Decoration来改变item之间的距离
 */
class GalleryActivity: AppCompatActivity() {

    private var mPageMargin = 0
    private var mConsumX = 0
    private var mLeftPageVisibleWidth = 0
    private lateinit var mDecoration: GalleryDecoration
    private var mPosition = 0
    private var mAnimFactor = 0.2f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        var list = arrayListOf<String>()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        var mPagerSnapHelper = PagerSnapHelper()
        galleryRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mDecoration = GalleryDecoration()
        galleryRecyclerView.addItemDecoration(mDecoration)
        mPagerSnapHelper.attachToRecyclerView(galleryRecyclerView)
        galleryRecyclerView.adapter = GalleryAdapter(list)

        mPageMargin = SizeUtils.dp2px(0f)
        mLeftPageVisibleWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(270f)) / 2

        mDecoration.mPageMargin = mPageMargin
        mDecoration.mLeftPageVisibleWidth = mLeftPageVisibleWidth

        mConsumX = mLeftPageVisibleWidth + mPageMargin * 2

        galleryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mConsumX += dx

                galleryRecyclerView.post {
                    var shouldConsumeX = mDecoration.mItemConsumeX

                    var offset = mConsumX.toFloat() / shouldConsumeX.toFloat()

                    var percent = offset - offset.toInt()

                    mPosition = offset.toInt()

                    LogUtils.e("shouldConsumeX--->${shouldConsumeX};offset---->${offset};percent--->${percent};")

                    setBottomToTopAnim(galleryRecyclerView, mPosition, percent)
                }
            }

        })
    }

    private fun setBottomToTopAnim(recyclerView: RecyclerView, position: Int, percent: Float) {
        // 中间页
        val mCurView = recyclerView.layoutManager!!.findViewByPosition(position)
        // 右边页
        val mRightView = recyclerView.layoutManager!!.findViewByPosition(position + 1)
        // 左边页
        val mLeftView = recyclerView.layoutManager!!.findViewByPosition(position - 1)
        // 右右边页
        val mRRView = recyclerView.layoutManager!!.findViewByPosition(position + 2)
        if (mLeftView != null) {
            mLeftView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mLeftView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
        if (mCurView != null) {
            mCurView.scaleX = 1 - percent * mAnimFactor
            mCurView.scaleY = 1 - percent * mAnimFactor
        }
        if (mRightView != null) {
            mRightView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mRightView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
        //如果当前最多可见三个View的，不需要又又边页
//        if (mRRView != null) {
//            mRRView.scaleX = 1 - percent * mAnimFactor
//            mRRView.scaleY = 1 - percent * mAnimFactor
//        }
    }


}