package com.leeds.lib.ui.viewstack.base

/**
 * 화면 스택 정보
 *
 * 스택에 저장될때 화면객체 및 추가정보를 함께 저장하는 클래스
 */
internal class ViewStackInfo<T>(
    /**
     * 화면
     */
    val view: T
) {
    /**
     * 태그정보
     */
    var tag: String = ""

    //todo require result 같은거 필요할까?
    override fun equals(other: Any?): Boolean {
        if (other !is ViewStackInfo<*>) {
            return false
        }

        if(this === other) {
            return true
        }

        if (view == other.view) {
            return true
        }

        if (tag == other.tag) {
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        var result = view?.hashCode() ?: 0
        result = 31 * result + tag.hashCode()
        return result
    }
}