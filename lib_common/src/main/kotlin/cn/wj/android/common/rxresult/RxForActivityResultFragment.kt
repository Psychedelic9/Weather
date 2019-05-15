package cn.wj.android.common.rxresult

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * 空 Fragment 处理 startActivityForResult
 */
@Suppress("unused")
class RxForActivityResultFragment : Fragment() {

    private val map = hashMapOf<Int, PublishSubject<RxForActivityResultInfo>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startForResult(requestCode: Int, intent: Intent): Observable<RxForActivityResultInfo> {
        val subject = PublishSubject.create<RxForActivityResultInfo>()
        map[requestCode] = subject
        return subject.doOnSubscribe {
            startActivityForResult(intent, requestCode)
        }
    }

    fun startForResult(requestCode: Int, start: (Fragment) -> Unit): Observable<RxForActivityResultInfo> {
        val subject = PublishSubject.create<RxForActivityResultInfo>()
        map[requestCode] = subject
        return subject.doOnSubscribe {
            start.invoke(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (map.containsKey(requestCode)) {
            val subject = map[requestCode] ?: return
            subject.onNext(RxForActivityResultInfo(requestCode, resultCode, data))
            subject.onComplete()
        }
    }
}