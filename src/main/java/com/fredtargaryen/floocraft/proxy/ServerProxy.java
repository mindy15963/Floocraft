package com.fredtargaryen.floocraft.proxy;

import com.fredtargaryen.floocraft.network.messages.*;

public class ServerProxy implements IProxy {
    @Override
    public void handleCamera() {}
    @Override
    public void onMessage(MessageApproval ma) {}
    @Override
    public void onMessage(MessageDoGreenFlash mdgf) {}
    @Override
    public void onMessage(MessageFireplaceList mfl) {}
    @Override
    public void onMessage(MessageStartPeek msp) {}
    @Override
    public void registerGUIs() {}
    @Override
    public void registerRenderers(){}
    @Override
    public void registerTickHandlers(){}
    @Override
    public void setUUIDs(MessagePlayerID message){}
}
