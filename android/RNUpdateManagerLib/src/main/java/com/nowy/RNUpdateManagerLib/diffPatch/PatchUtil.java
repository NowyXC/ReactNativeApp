package com.nowy.RNUpdateManagerLib.diffPatch;


import java.util.LinkedList;

public class PatchUtil {

    /**
     *
     * @param originBundleStr 源bundle文件
     * @param patchStr 差分包
     * @return
     */
    public static String merge(String originBundleStr,String patchStr){
        diff_match_patch dmp  = new diff_match_patch();
        LinkedList<diff_match_patch.Patch> patchList = (LinkedList<diff_match_patch.Patch>)
                dmp.patch_fromText(patchStr);
        Object[] resultArr = dmp.patch_apply(patchList,originBundleStr);
        if(isCompleted(resultArr)){
            return (String)resultArr[0];
        }
        return null;
    }


    /**
     * 完全匹配
     * @param results
     * @return
     */
    public static boolean isCompleted(Object[] results){
        boolean[] resultStatusArr = (boolean[])results[1];
        for(boolean s : resultStatusArr){
            if(!s) return false;
        }
        return true;
    }


}
