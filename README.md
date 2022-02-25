# NezhaApplist


## todo list

``` shell
E/sanbo: java.lang.OutOfMemoryError: Failed to allocate a 16384016 byte allocation with 15837232 free bytes and 15MB until OOM, target footprint 268435456, growth limit 268435456
        at java.util.Arrays.copyOf(Arrays.java:3161)
        at java.io.ByteArrayOutputStream.grow(ByteArrayOutputStream.java:118)
        at java.io.ByteArrayOutputStream.ensureCapacity(ByteArrayOutputStream.java:93)
        at java.io.ByteArrayOutputStream.write(ByteArrayOutputStream.java:153)
        at ff.jnezha.jnt.utils.FileUtils.getBase64FromFile(FileUtils.java:76)
        at ff.jnezha.jnt.cs.GithubHelper.createFile(GithubHelper.java:234)
        at com.nz.sdemo.utils.Github.report(Github.java:31)
        at com.nz.sdemo.PortToServer.work(PortToServer.java:43)
        at me.hhhaiai.nzlist.ui.NzListActivity$6.lambda$onClick$0$NzListActivity$6(NzListActivity.java:256)
        at me.hhhaiai.nzlist.ui.-$$Lambda$NzListActivity$6$hPuxffjlmTGIFcgxvWrLSw6GgjM.run(Unknown Source:6)
```