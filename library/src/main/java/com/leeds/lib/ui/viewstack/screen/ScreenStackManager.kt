package com.leeds.lib.ui.viewstack.screen

import android.content.res.Configuration
import android.widget.FrameLayout

//todo legacy 코드 사용 방법에 대한 개선 필요
class ScreenStackManager {
    companion object {
        const val FLAG_NONE = 0
        const val FLAG_CLEAR_TOP = 1
    }

    private val screenStack: ScreenStack = ScreenStack()


    //현재 동작이 가능한지 여부
    //screen 제어에 필요한 파라매터가 정상인지 여부 판별
    private fun isUnavailable(container: FrameLayout?, screen: Screen?): Boolean {
        return container == null || screen == null
    }

    //screen 추가
    //screenStack에 파라매터로 전달받은 screen을 추가
    private fun putStack(screen: Screen) {
        screenStack.put(screen)
    }

    private fun popStack() {
        screenStack.pop()
    }

    private fun clearStack(container: FrameLayout) {
        var screen: Screen?
        while (!screenStack.isEmpty()) {
            screenStack.pop()?.let {
                removeView(container, it)
            }
        }
    }


    private fun getContainerLayoutParams(): FrameLayout.LayoutParams? {
        return FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun addView(container: FrameLayout, screen: Screen) {
        if (isUnavailable(container, screen)) {
            return
        }
        screen.createView(container)
        container.addView(screen.getView(), getContainerLayoutParams())
        screen.start()
        screen.resume()
    }

    private fun removeView(container: FrameLayout, screen: Screen) {
        if (isUnavailable(container, screen)) {
            return
        }
        screen.pause()
        screen.stop()
        screen.destroy()
        container.removeView(screen.getView())
    }

    private fun hasScreen(screen: Screen): Boolean {
        return screenStack.contain(screen)
    }

    fun getFront(): Screen? {
        return screenStack.peek()
    }

    fun replace(container: FrameLayout?, screen: Screen?) {
        if (container == null || screen == null) {
            return
        }
        //TransitionManager.beginDelayedTransition(container, new Explode());
        clearStack(container)
        screen.create(container.context)
        addView(container, screen)
        putStack(screen)
    }

    fun add(container: FrameLayout?, screen: Screen?) {
        add(container, screen, FLAG_NONE)
    }

    fun add(container: FrameLayout?, screen: Screen?, flag: Int) {
        if (container == null || screen == null) {
            return
        }
        //TransitionManager.beginDelayedTransition(container, new Explode());
        screen.create(container.context)
        when (flag) {
            FLAG_CLEAR_TOP -> if (hasScreen(screen)) {
                popBackScreen(container, screen)
            } else {
                internalAdd(container, screen)
            }
            FLAG_NONE -> internalAdd(container, screen)
            else -> internalAdd(container, screen)
        }
    }

    private fun popBackScreen(container: FrameLayout, screen: Screen) {
        val index: Int = screenStack.getTopViewIndex(screen)
        if (index < 0) {
            return
        }
        val currentIndex: Int = screenStack.getTopViewIndex()
        var popScreen: Screen?
        for (i in currentIndex downTo index + 1) {
            popScreen = screenStack.peek()
            removeView(container, popScreen!!)
            popStack()
        }
        popScreen = screenStack.peek()
        if (popScreen == null) {
            return
        }
        if (screen.hasArgument()) {
            popScreen.setArgument(screen.getArgument())
        }
    }

    private fun internalAdd(container: FrameLayout, screen: Screen) {
        addView(container, screen)
        putStack(screen)
    }

    fun addForResult(requestCode: Int, container: FrameLayout?, screen: Screen?) {
        if (container == null || screen == null) {
            return
        }

        screen.create(container.context)
        screen.setRequestCode(requestCode)
        addView(container, screen)
        putStack(screen)
    }

    fun back(container: FrameLayout?): Boolean {
        if (container == null) {
            return false
        }
        //TransitionManager.beginDelayedTransition(container, new Explode());
        val last: Screen = screenStack.peek() ?: return false
        removeView(container, last)
        popStack()
        val front: Screen = screenStack.peek() ?: return false
        setResultIfNeed(front, last)
        front.show()
        return true
    }

    private fun setResultIfNeed(front: Screen, last: Screen) {
        if (!front.hasRequest()) {
            return
        }
        front.onScreenResult(front.getRequestCode(), last.getResultCode(), last.getResultData())
    }

    fun back(container: FrameLayout?, screen: Screen?): Boolean {
        if (container == null || screen == null) {
            return false
        }
        //TransitionManager.beginDelayedTransition(container, new Explode());
        val index: Int = screenStack.getTopViewIndex(screen)
        if (index < 0) {
            return false
        }
        val last: Screen = screenStack.peek() ?: return false
        val currentIndex: Int = screenStack.getTopViewIndex()
        for (i in currentIndex downTo index + 1) {
            screenStack.peek()?.let {
                removeView(container, it)
                popStack()
            }
        }
        val front: Screen = screenStack.peek() ?: return false
        setResultIfNeed(front, last)
        front.show()
        return true
    }

    fun canGoBack(): Boolean {
        return screenStack.isEmpty()
    }

    fun onConfigurationChanged(newConfig: Configuration?) {
        if (newConfig == null) {
            return
        }

        screenStack.getViews().forEach {
            it.onConfigurationChanged(newConfig)
        }
    }

    fun start() {
        val screen = getFront() ?: return
        screen.start()
    }

    fun resume() {
        val screen = getFront() ?: return
        screen.resume()
    }

    fun pause() {
        val screen = getFront() ?: return
        screen.pause()
    }

    fun stop() {
        val screen = getFront() ?: return
        screen.stop()
    }

    fun destory(container: FrameLayout?) {
        clearStack(container!!)
        screenStack.clear()
    }
}