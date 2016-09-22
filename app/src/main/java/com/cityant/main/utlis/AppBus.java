package com.cityant.main.utlis;


import com.squareup.otto.Bus;

/**
 * Created by wupeitao on 2016/9/22.
 */

public class AppBus extends Bus {

    private static AppBus bus;

    public static AppBus getInstance() {
        if (bus == null) {
            bus = new AppBus();
        }
        return bus;
    }
}
