package com.zhou.appmanager.service;

import android.content.Context;

import com.zhou.appmanager.controller.AppChecker;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by zhou on 14-5-30.
 */
public class ServiceManger {

    Context context;

    public static final String AppCheckerService = AppChecker.class.getName();

    private Map<String, IService> serviceMap;

    public ServiceManger(Context context) {
        this.context = context;
        this.serviceMap = new WeakHashMap(4);
        serviceMap.put(AppCheckerService, null);
    }

    public IService getService(String serviceName) {
        if (!serviceMap.keySet().contains(serviceName)) {
            throw new RuntimeException("service not found!");
        }
        IService service = serviceMap.get(serviceName);
        if (service == null) {
            Class serviceClass = null;
            try {
                serviceClass = context.getClassLoader().loadClass(serviceName);
                if (serviceClass == null || !IService.class.isAssignableFrom(serviceClass)) {
                    throw new InstantiationException("Trying to instantiate a class " + serviceName);
                }
                Constructor constructor = serviceClass.getDeclaredConstructor(new Class[]{Context.class});
                constructor.setAccessible(true);
                service = (IService) constructor.newInstance(this.context);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            serviceMap.put(serviceName, service);
        }
        return service;
    }
}
