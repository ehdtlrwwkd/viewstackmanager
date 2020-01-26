package com.leeds.lib.ui.viewstack.screen

import com.leeds.lib.ui.viewstack.base.ViewStack
import com.leeds.lib.ui.viewstack.base.ViewStackInfo
import java.util.*

/**
 * Screen 스택
 *
 * Screen 인터페이스 스택 구현체
 */
class ScreenStack : ViewStack<Screen> {
    private val stack: Vector<ViewStackInfo<Screen>> = Vector()

    override fun peek(): Screen? {
        val front = stack.lastOrNull() ?: return null
        return front.view
    }

    override fun put(view: Screen) {
        stack.add(ViewStackInfo(view))
    }

    override fun put(view: Screen, tag: String) {
        stack.add(ViewStackInfo(view).apply { this.tag = tag })
    }

    override fun pop(): Screen? {
        val front = stack.lastOrNull() ?: return null
        stack.remove(front)
        return front.view
    }

    override fun getTopViewIndex(view: Screen): Int {
        return stack.indexOfLast { it.view == view }
    }

    override fun getTopViewIndex(tag: String): Int {
        return stack.indexOfLast { it.tag == tag }
    }

    override fun getTopViewIndex(): Int {
        return stack.size - 1
    }

    override fun contain(view: Screen): Boolean {
        return stack.any { it.view == view }
    }

    override fun contain(tag: String): Boolean {
        return stack.any { it.tag == tag }
    }

    override fun getViews(): List<Screen> {
        return stack.reversed().map { it.view }
    }

    override fun isEmpty(): Boolean {
        return stack.isEmpty()
    }

    override fun size(): Int {
        return stack.size
    }

    fun clear() {
        while (!isEmpty()) {
            pop()?.destroy()
        }
        stack.clear()
    }
}