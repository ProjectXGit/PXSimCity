package net.projectx.simcity.main;

import com.sun.deploy.net.cookie.CookieUnavailableException;
import netscape.javascript.JSException;

import org.bukkit.plugin.java.JavaPlugin;
import sun.plugin2.liveconnect.BrowserSideObject;
import sun.plugin2.main.server.Plugin;

import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * ~Yannick on 12.11.2019 at 21:42 o´ clock
 */
public class Main extends JavaPlugin implements Plugin {
    @Override
    public void onEnable() {
        Data.instance = this;
        registerCommands();
        registerListener();
    }

    @Override
    public void onDisable() {

    }

    public static void registerCommands() {

    }

    public static void registerListener() {

    }

    @Override
    public void invokeLater(Runnable runnable) {

    }

    @Override
    public void notifyMainThread() {

    }

    @Override
    public String getDocumentBase() {
        return null;
    }

    @Override
    public void showDocument(String s, String s1) {

    }

    @Override
    public void showStatus(String s) {

    }

    @Override
    public String getCookie(URL url) throws CookieUnavailableException {
        return null;
    }

    @Override
    public void setCookie(URL url, String s) throws CookieUnavailableException {

    }

    @Override
    public PasswordAuthentication getAuthentication(String s, String s1, int i, String s2, String s3, URL url, boolean b) {
        return null;
    }

    @Override
    public void hostRemoteCAContext(int i) {

    }

    @Override
    public BrowserSideObject javaScriptGetWindow() {
        return null;
    }

    @Override
    public void javaScriptRetainObject(BrowserSideObject browserSideObject) {

    }

    @Override
    public void javaScriptReleaseObject(BrowserSideObject browserSideObject) {

    }

    @Override
    public Object javaScriptCall(BrowserSideObject browserSideObject, String s, Object[] objects) throws JSException {
        return null;
    }

    @Override
    public Object javaScriptEval(BrowserSideObject browserSideObject, String s) throws JSException {
        return null;
    }

    @Override
    public Object javaScriptGetMember(BrowserSideObject browserSideObject, String s) throws JSException {
        return null;
    }

    @Override
    public void javaScriptSetMember(BrowserSideObject browserSideObject, String s, Object o) throws JSException {

    }

    @Override
    public void javaScriptRemoveMember(BrowserSideObject browserSideObject, String s) throws JSException {

    }

    @Override
    public Object javaScriptGetSlot(BrowserSideObject browserSideObject, int i) throws JSException {
        return null;
    }

    @Override
    public void javaScriptSetSlot(BrowserSideObject browserSideObject, int i, Object o) throws JSException {

    }

    @Override
    public String javaScriptToString(BrowserSideObject browserSideObject) {
        return null;
    }

    @Override
    public int getActiveJSCounter() {
        return 0;
    }

    @Override
    public void incrementActiveJSCounter() {

    }

    @Override
    public void decrementActiveJSCounter() {

    }

    @Override
    public void startupStatus(int i) {

    }

    @Override
    public void waitForSignalWithModalBlocking() {

    }
}
