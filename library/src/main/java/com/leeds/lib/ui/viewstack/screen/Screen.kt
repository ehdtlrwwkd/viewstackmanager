package com.leeds.lib.ui.viewstack.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

/**
 * 화면 인터페이스 객체
 * Fragment를 대체할 수 있도록 가급적 유사한 lifecycle 및 동작을 가지도록 설계
 */

//todo 추가적인 수정 필요..
interface Screen {
    /**
     * 실제 화면이 표시되는 view 객체 반환
     */
    fun getView(): View?

    /**
     * 화면이 연결되어있는 activity 반환
     * lifecycle에 따라 null 이 반환될 수 있음
     */
    fun getActivity(): Activity?


    fun hasArgument(): Boolean
    fun setArgument(bundle: Bundle?)
    fun getArgument(): Bundle?

    /**
     * 뒤로가기 가능여부 반환
     */
    fun onBackPressed(): Boolean

    /**
     * view 표시
     * stack에서 최상단에 표시될때 표출처리
     */
    fun show()

    /**
     * view 숨김
     * stack에서 back으로 이동될때 숨김처리
     */
    fun hide()

    /**
     * 아직 view도 생성되지 않았고 container에 등록도 되지 않은 상황
     * 내부적으로 onCreate()를 호출한다.
     * @param context context 객체
     */
    fun create(context: Context)

    /**
     * view 가 속할 container 객체를 이용하여 view 생성
     * 아직 container에는 등록되지 않았다.
     * 내부적으로 onCreateView()를 호출한다.
     */
    fun createView(container: ViewGroup)

    /**
     * 생성된 view까지 container에 등록된 상황
     * 내부적으로 onStart()를 호출한다.
     */
    fun start()

    /**
     * 호출시점
     *  - 생성시점에 start 이후
     *  - activity onResume 시점
     *  - backstack 에서 front로 옮겨지는 시점
     *
     * 내부적으로 onResume()을 호출한다.
     */
    fun resume()

    /**
     * 호출시점
     *  - stack 에서 제거되는 시점
     *  - activity에서 onPause 시점
     *  - front에서 backstack 으로 옮겨지는 시점
     * 내부적으로 onPause()을 호출한다.
     */
    fun pause()

    /**
     * stack 에서 제거되는 시점에 호출된다.
     * 내부적으로 onStop()을 호출한다.
     */
    fun stop()

    /**
     * stack 에서 제거되는 시점에 호출된다.
     * 내부적으로 onDestroy()을 호출한다.
     * 최종적으로 onDestroy() 이후 view가 container에서 제거된다고 보면 된다.
     */
    fun destroy()

    /**
     * 화면 회전등 Activity.onConfigurationChanged() 가 발생할때 동기화 되어 발생한다.
     */
    fun onConfigurationChanged(newConfig: Configuration)

    /**
     * Fragment.onActivityResult 처럼 직접 전달받는다.
     * stack의 front만 적용된다.
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * Activity.onActivityResult처럼 앞선 stack의 screen에서 돌아오는 시점에 값을 전달받을 때 사용한다.
     */
    fun onScreenResult(requestCode: Int, resultCode: Int, resultData: Bundle?)

    /**
     * onScreenResult로 전달받기를 희망하는 내용이 있는지 여부
     */
    fun hasRequest(): Boolean

    /**
     * request code 설정
     */
    fun setRequestCode(code: Int)

    /**
     * request code 반환
     */
    fun getRequestCode(): Int

    /**
     * onScreenResult로 반환되는 정보가 있는지 여부
     */
    fun hasResult(): Boolean

    /**
     * result code 반환
     */
    fun getResultCode(): Int

    /**
     * result data 반환
     */
    fun getResultData(): Bundle?
}