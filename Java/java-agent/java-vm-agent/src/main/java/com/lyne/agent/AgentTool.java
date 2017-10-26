package com.lyne.agent;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.security.CodeSource;

/**
 * @author nn_liu
 * @Created 2017-10-24-18:00
 */

public class AgentTool {

    static Class<?> vmProviderClass=null;

    public static void loadAgent() {
        try {
            final String virtualMachineClassName = "com.sun.tools.attach.VirtualMachine";
            final String hotspotVMName = "sun.tools.attach.HotSpotVirtualMachine";
            Object VM;
            Method loadAgentMethod;
            Method detachMethod;
            Class<?> vmClass = Tools.loadJDKToolClass(virtualMachineClassName);
            Class<?> hotspotVMClass = Tools.loadJDKToolClass(hotspotVMName);
            String pid = Tools.currentPID();
            if (vmProviderClass != null) {
                Object vmProvider = vmProviderClass.newInstance();
                VM = vmProviderClass.getMethod("attachVirtualMachine", String.class).invoke(vmProvider, pid);
                loadAgentMethod = VM.getClass().getMethod("loadAgent", String.class);
                detachMethod = VM.getClass().getMethod("detach");
            } else {
                Method attacheMethod = vmClass.getMethod("attach", String.class);
                VM = attacheMethod.invoke(null, pid);
                vmProviderClass = vmClass.getMethod("provider").invoke(VM).getClass();
                loadAgentMethod = hotspotVMClass.getMethod("loadAgent", String.class);
                detachMethod = vmClass.getMethod("detach");
            }
            CodeSource src = AgentMain.class.getProtectionDomain().getCodeSource();

            String jarPath = Paths.get(src.getLocation().toURI()).toString();
            loadAgentMethod.invoke(VM, jarPath);
            detachMethod.invoke(VM);
        }catch (Exception e) {
            // do nothing
        }
    }

}
