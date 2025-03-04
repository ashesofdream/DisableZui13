package pub.chara.disablealttab.hook

import android.view.KeyEvent
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XposedBridge

object AltTabHook : BaseHook() {
    override fun init() {
        try {
            //disable alt-tab
            //this works for any android version
            findMethod("com.android.server.policy.PhoneWindowManager") {
                name == "interceptKeyBeforeDispatching"
            }.hookBefore { param ->
                run {
                    val arg1: KeyEvent = param.args[1] as KeyEvent;
                    // alt-tab
                    if ((arg1.isAltPressed && arg1.keyCode == 61)) {
                        param.result = 0L;
                    }else if ((arg1.isCtrlPressed && arg1.keyCode == 51)) {
                        param.result = 0L;
                    }
                }
            }
            XposedBridge.log("DisableAltTab: AltTabHook success!")
        } catch (e: Throwable) {
            XposedBridge.log("DisableAltTab: AltTabHook failed!")
            XposedBridge.log(e)
        }
    }
}
