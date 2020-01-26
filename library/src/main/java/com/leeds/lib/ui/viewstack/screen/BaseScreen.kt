package com.leeds.lib.ui.viewstack.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseScreen : Screen {

    private var view: View? = null

    override fun getView(): View? {
        return view
    }

    //-- lifecycle 동작
    // start 상태일때 두번 호출 되지 않도록, resume 상태일때도 마찬가지
    // state에 대한 구분 단계 추가 필요로 보임

    //생성
    override fun create(context: Context) {
        onCreate(context)
        onActivityCreated(null)
    }

    //생성 시점에 호출
    open fun onCreate(context: Context) {}

    //saved instance 연결 시점에 호출..
    open fun onActivityCreated(savedInstanceState: Bundle?) {}

    //화면에 표시할 view 를 생성
    override fun createView(container: ViewGroup) {
        val inflater = LayoutInflater.from(container.context)
        val createdView = createView(inflater, container, null) ?: return
        view = createdView
        onCreateView(container, createdView)
    }

    /**
     * state 복구 필요시 override 하여 사용
     */
//    protected void restoreInstanceState(Bundle savedInstanceState) {
//    }

    /**
     * layout으로 사용할 View 생성 onCreateView()에서 실행
     */
    protected abstract fun createView(
        inflater: LayoutInflater?,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View?

    //createView의 동작이후 호출
    open fun onCreateView(container: ViewGroup, view: View) {}


    override fun start() {
       onStart()
    }

    open fun onStart() {}

    override fun resume() {
       onResume()
    }

    open fun onResume() {}

    override fun pause() {
       onPause()
    }

    open fun onPause() {}

    override fun stop() {
       onStop()
    }

    open fun onStop() {}

    override fun destroy() {
       onDestroy()
    }

    open fun onDestroy() {}
    //-- lifecycle 동작

    //-- view 상태
    fun isAdded() : Boolean {
        return view?.parent != null
    }

    //-- view 상태

    override fun getActivity(): Activity? {
       return if(view?.context is Activity) {
           view?.context as Activity
       } else {
           null
       }
    }

    //-- view visibility
    override fun show() {
       view?.let {
            it.visibility = View.VISIBLE
           onShow()
       }
    }

    open fun onShow() {}

    override fun hide() {
        view?.let {
            it.visibility = View.GONE
            onHide()
        }
    }

    open fun onHide() {}
    //-- view visibility

    override fun onBackPressed(): Boolean {
       return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun hasArgument(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setArgument(bundle: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getArgument(): Bundle? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScreenResult(requestCode: Int, resultCode: Int, resultData: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasRequest(): Boolean {
        return false
    }

    override fun setRequestCode(code: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRequestCode(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasResult(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResultCode(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResultData(): Bundle? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Screen) {
            return false
        }

        if(this === other) {
            return true
        }

        if(view == other.getView()) {
            return true
        }

        if(javaClass.name == other.javaClass.name) {
            return true
        }
        return false
    }
}