package me.hhhaiai.nzlist.memory;

import android.text.TextUtils;

import me.hhhaiai.nzlist.interfaces.IProcesBase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Copyright © 2021 analsys Inc. All rights reserved.
 * @Description: 内存持有页面回调选项
 * @Version: 1.0
 * @Create: 2021/04/110 16:47:51
 * @author: sanbo
 */
public class ProcessHolder {

    private ConcurrentHashMap<String, IProcesBase> mMemoryMap =
            new ConcurrentHashMap<String, IProcesBase>();

    public void addPorceser(IProcesBase proceser) {
        if (proceser != null && !TextUtils.isEmpty(proceser.getName())) {
            mMemoryMap.put(proceser.getName(), proceser);
        }
    }

    public synchronized String[] getPorceserNames() {

        CopyOnWriteArrayList<String> res = new CopyOnWriteArrayList<String>();

        for (Map.Entry<String, IProcesBase> entry : mMemoryMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey())) {
                res.add(entry.getKey());
            }
        }
        if (res.size() > 0) {
            return res.toArray(new String[res.size()]);
        }
        return new String[] {};
    }

    public synchronized IProcesBase getPorceserByName(String name) {
        if (!TextUtils.isEmpty(name) && mMemoryMap.containsKey(name)) {
            return mMemoryMap.get(name);
        }
        return null;
    }

    /********************* get instance begin **************************/
    public static ProcessHolder getInstance() {
        return HLODER.INSTANCE;
    }

    private static class HLODER {
        private static final ProcessHolder INSTANCE = new ProcessHolder();
    }

    private ProcessHolder() {}
    /********************* get instance end **************************/

}
